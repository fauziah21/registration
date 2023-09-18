package com.fitness.registration.controller;

import com.fitness.registration.jwt.JwtUtils;
import com.fitness.registration.jwt.TokenRefreshException;
import com.fitness.registration.model.RefreshToken;
import com.fitness.registration.repository.RefreshTokenRepository;
import com.fitness.registration.request.LoginRequest;
import com.fitness.registration.request.TokenRefreshRequest;
import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.response.LoginResponse;
import com.fitness.registration.response.TokenRefreshResponse;
import com.fitness.registration.service.AuthService;
import com.fitness.registration.service.RefreshTokenService;
import com.fitness.registration.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse login = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(login);

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getAccountEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<BaseResponse> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getAccountId();
        refreshTokenService.deleteByUserId(userId);

        BaseResponse response = new BaseResponse();
        response.setCode("000");
        response.setMessage("Log out successful!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
