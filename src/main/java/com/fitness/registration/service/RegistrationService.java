package com.fitness.registration.service;

import com.fitness.registration.model.*;
import com.fitness.registration.repository.AccountRepository;
import com.fitness.registration.repository.OtpRepository;

import com.fitness.registration.repository.RolesRepository;
import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.utils.SendOTPUtils;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SendOTPUtils sendOTPUtils;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private
    OtpRepository otpRepository;

    public static int SEND_OTP_SUCCESS = 0;
    public static int FAILED_MAX_RETRY = 1;
    public static int FAILED_SEND_OTP = 2;


    @Transactional
    public BaseResponse createAccount(String accountName, String accountEmail, String accountPassword, String accountPhone,
                                      String accountCreditCard, String accountCardCvv, String accountCardExp,
                                      String accountCardName) throws MessagingException {
        BaseResponse response = new BaseResponse();

        if (accountName.isEmpty()||accountEmail.isEmpty()||accountPassword.isEmpty()||accountPhone.isEmpty()||
                accountCreditCard.isEmpty()||accountCardCvv.isEmpty()||accountCardExp.isEmpty()
                ||accountCardName.isEmpty() ){
            response.setCode("009");
            response.setMessage("Please fill all the fields");
            return response;
        }

        //validasi apakah akun exists
        Optional<AccountModel> accountOpt = accountRepository.findByAccountEmail(accountEmail);
        if (accountOpt.isPresent()){
            response.setCode("001");
            response.setMessage("Data already exists");
            return response;
        }

        //validasi password
        if (!accountPassword.matches("(?=[A-Za-z0-9@#$%^&+!=]+$)^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,}).*$")){
            response.setCode("004");
            response.setMessage("Data should contains 8 characters, 1 numeric, min 1 uppercase and 1 lowercase");
            return response;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordHash = "";
        String creditCardHash = "";
        String cvvHash = "";
        String accountCardNameHash = "";
        try{
            passwordHash = passwordEncoder.encode(accountPassword);
            creditCardHash = passwordEncoder.encode(accountCreditCard);
            cvvHash = passwordEncoder.encode(accountCardCvv);
            accountCardNameHash = passwordEncoder.encode(accountCardName);
        }catch (Exception e){
            e.printStackTrace();
        }

        //send otp
        sendOTP(accountEmail, accountName);

        YearMonth expDate = YearMonth.parse(accountCardExp);
        LocalDate cardExp = expDate.atDay(1);

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();
        roles.add(role);

        AccountModel accountModel = new AccountModel();
        accountModel.setAccountName(accountName);
        accountModel.setAccountEmail(accountEmail);
        accountModel.setAccountPassword(passwordHash);
        accountModel.setAccountPhone(accountPhone);
        accountModel.setAccountCreditCard(creditCardHash);
        accountModel.setAccountCardCvv(cvvHash);
        accountModel.setAccountCardExp(cardExp);
        accountModel.setAccountCardName(accountCardNameHash);
        accountModel.setAccountStatus("BELUM TERVALIDASI");
        accountModel.setCreatedAt(LocalDateTime.now());
        accountModel.setUpdatedAt(LocalDateTime.now());
        accountModel.setRoles(roles);
        accountRepository.save(accountModel);

        response.setCode("000");
        response.setMessage("Success");
        return response;
    }

    public int sendOTP(String otpEmail, String accountName) throws MessagingException {
        int result = sendOTPUtils.sendOTP(otpEmail, accountName);
        if (result == SendOTPUtils.SUCCESS){
            return SEND_OTP_SUCCESS;
        } else if (result == SendOTPUtils.FAILED_MAX_RETRY) {
            return FAILED_MAX_RETRY;
        }else {
            return FAILED_SEND_OTP;
        }
    }

    public BaseResponse verifyOTP(String otpEmail,String otpCode){
        BaseResponse response = new BaseResponse();
        Optional<OtpModel> otpOpt = otpRepository.verifyOtp(otpEmail, otpCode);
        if (otpOpt.isEmpty()){
            response.setCode("003");
            response.setMessage("Data not valid");
            return response;
        }

        //delete otp
        otpRepository.delete(otpOpt.get());

        //update status tervalidasi
        Optional<AccountModel> accountOtp = accountRepository.findByAccountEmail(otpEmail);
        if (accountOtp.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        AccountModel accountCurrent = accountOtp.get();
        accountCurrent.setAccountStatus("TERDAFTAR");
        accountRepository.save(accountCurrent);

        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setRoleId(2);
        userRoleModel.setUserId(accountOtp.get().getAccountId());

        response.setCode("000");
        response.setMessage("Data Valid");
        return response;

    }

    //cek status member
    public BaseResponse membershipStatus(String accountEmail,String accountName) throws MessagingException {
        BaseResponse response = new BaseResponse();
        Optional<AccountModel> accountOtp = accountRepository.findByAccountEmail(accountEmail);
        if (accountOtp.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        int resultEmail = sendOTPUtils.sendMembershipStatusEmail(accountEmail, accountName);
        if (resultEmail != SendOTPUtils.SUCCESS){
            response.setCode("099");
            response.setMessage("Something wrong");
            return response;
        }

        response.setCode("000");
        response.setMessage("Success");
        return response;

    }



//    public BaseResponse login(String accountEmail,String accountPassword){
//        BaseResponse response = new BaseResponse();
//
//        Optional<AccountModel> accountOpt = accountRepository.findByAccountEmail(accountEmail);
//        if (accountOpt.isEmpty()){
//            response.setCode("002");
//            response.setMessage("Data not found");
//            return response;
//        }
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        boolean isPassMatch = passwordEncoder.matches(accountPassword, accountOpt.get().getAccountPassword());
//    }
}
