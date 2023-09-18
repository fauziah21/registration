package com.fitness.registration.controller;

import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.service.RegistrationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("")
    public String tes(){
        return "Test success";
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> createAccount(@RequestParam("accountName")String accountName,
                                                      @RequestParam("accountEmail")String accountEmail,
                                                      @RequestParam("accountPassword")String accountPassword,
                                                      @RequestParam("accountPhone")String accountPhone,
                                                      @RequestParam("accountCreditCard")String accountCreditCard,
                                                      @RequestParam("accountCardCvv")String accountCardCvv,
                                                      @RequestParam("accountCardExp")String accountCardExp,
                                                      @RequestParam("accountCardName")String accountCardName) throws MessagingException {
        BaseResponse account = registrationService.createAccount(accountName, accountEmail, accountPassword, accountPhone, accountCreditCard,
                accountCardCvv, accountCardExp, accountCardName);

        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @GetMapping("/resend-otp")
    public ResponseEntity<BaseResponse> resendOTP(@RequestParam("accountEmail")String accountEmail,
                                                  @RequestParam("accountName")String accountName) throws MessagingException {
        int result = registrationService.sendOTP(accountEmail,accountName);
        BaseResponse response = new BaseResponse();
        if (result == registrationService.SEND_OTP_SUCCESS){
            response.setCode("000");
            response.setMessage("Success resend OTP");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (result == registrationService.FAILED_MAX_RETRY) {
            response.setCode("012");
            response.setMessage("Failed max retry");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (result == registrationService.FAILED_SEND_OTP) {
            response.setCode("013");
            response.setMessage("Failed send OTP");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            response.setCode("099");
            response.setMessage("Something wrong");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<BaseResponse> verifyOtp(@RequestParam("otpEmail")String otpEmail,
                                                  @RequestParam("otpCode")String otpCode){
        BaseResponse response = registrationService.verifyOTP(otpEmail, otpCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/membership-status")
    public ResponseEntity<BaseResponse> membershipStatus(@RequestParam("accountEmail")String accountEmail,
                                                         @RequestParam("accountName")String accountName) throws MessagingException {
        BaseResponse response = registrationService.membershipStatus(accountEmail, accountName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
