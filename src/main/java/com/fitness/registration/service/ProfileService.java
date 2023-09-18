package com.fitness.registration.service;

import com.fitness.registration.model.AccountModel;
import com.fitness.registration.repository.AccountRepository;
import com.fitness.registration.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private AccountRepository accountRepository;

    //fitur change password
    public BaseResponse changePassword(String accountEmail, String accountPassword){
        BaseResponse response = new BaseResponse();
        Optional<AccountModel> accountOtp = accountRepository.findByAccountEmail(accountEmail);
        if (accountOtp.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        String passwordHash = "";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        passwordHash = passwordEncoder.encode(accountPassword);

        AccountModel accountCurrent = accountOtp.get();
        accountCurrent.setAccountPassword(passwordHash);
        accountRepository.save(accountCurrent);

        response.setCode("000");
        response.setMessage("Success");
        return response;

    }

    //fitur update profile
    public BaseResponse updateProfile(String accountEmail,String accountName,String accountCreditCard,
                                      String accountCardCvv,String accountCardExp,String accountCardName){
        BaseResponse response = new BaseResponse();
        Optional<AccountModel> accountOpt = accountRepository.findByAccountEmail(accountEmail);
        System.out.println("EMAIL ADALAH = " +accountEmail);
        if (accountOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ccHash = "";
        String cvvHash = "";
        String cardNameHash = "";

        YearMonth expDate = YearMonth.parse(accountCardExp);
        LocalDate cardExp = expDate.atDay(1);

        ccHash = passwordEncoder.encode(accountCreditCard);
        cvvHash = passwordEncoder.encode(accountCardCvv);
        cardNameHash = passwordEncoder.encode(accountCardName);

        AccountModel accountCurrent = accountOpt.get();
        accountCurrent.setAccountName(accountName);
        accountCurrent.setAccountCreditCard(ccHash);
        accountCurrent.setAccountCardCvv(cvvHash);
        accountCurrent.setAccountCardExp(cardExp);
        accountCurrent.setAccountCardName(cardNameHash);
        accountCurrent.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(accountCurrent);

        response.setCode("000");
        response.setMessage("Success");
        return response;
    }
}
