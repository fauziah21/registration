package com.fitness.registration.service;

import com.fitness.registration.jwt.JwtUtils;
import com.fitness.registration.model.AccountModel;
import com.fitness.registration.model.RefreshToken;
import com.fitness.registration.repository.AccountRepository;
import com.fitness.registration.repository.RefreshTokenRepository;
import com.fitness.registration.request.LoginRequest;
import com.fitness.registration.response.JwtResponse;
import com.fitness.registration.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public LoginResponse login(LoginRequest loginRequest){
        LoginResponse response = new LoginResponse();

        Optional<AccountModel> accountEmailOpt = accountRepository.findByAccountEmail(loginRequest.getAccountEmail());
        if (accountEmailOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Email not found");
            response.setData(new JwtResponse());
            return response;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isMatch = bCryptPasswordEncoder.matches(loginRequest.getPassword(), accountEmailOpt.get().getAccountPassword());
        if (!isMatch){
            response.setCode("003");
            response.setMessage("Data invalid");
            response.setData(new JwtResponse());
            return response;
        }
        System.out.println("password matched" + isMatch);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getAccountEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getAccountEmail());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setRefreshToken(refreshToken.getToken());
        jwtResponse.setId(accountEmailOpt.get().getAccountId());
        jwtResponse.setUsername(accountEmailOpt.get().getAccountName());
        jwtResponse.setEmail(loginRequest.getAccountEmail());
        jwtResponse.setRoles(roles);



        response.setCode("000");
        response.setMessage("Success");
        response.setData(jwtResponse);
        return response;


    }
}
