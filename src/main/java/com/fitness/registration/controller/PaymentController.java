package com.fitness.registration.controller;

import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.service.PaymentService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/verify")
    public ResponseEntity<BaseResponse> verifyPayment(@RequestParam("accountId")Long accountId,
                                                      @RequestParam("subId")int subId) throws MessagingException {
        BaseResponse response = paymentService.verifyPayment(accountId, subId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/verify/otp")
    public ResponseEntity<BaseResponse> verifyOTP(@RequestParam("otpEmail")String otpEmail,
                                                  @RequestParam("otpCode")String otpCode){
        BaseResponse response = paymentService.verifyOTP(otpEmail, otpCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/resend-otp")
    public ResponseEntity<BaseResponse> resendOTP(@RequestParam("accountEmail")String accountEmail,
                                                  @RequestParam("accountName")String accountName) throws MessagingException {
        int result = paymentService.sendPaymentOtp(accountEmail,accountName);
        BaseResponse response = new BaseResponse();
        if (result == PaymentService.SEND_OTP_SUCCESS){
            response.setCode("000");
            response.setMessage("Success resend OTP");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (result == PaymentService.FAILED_SEND_OTP) {
            response.setCode("013");
            response.setMessage("Failed send OTP");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            response.setCode("099");
            response.setMessage("Something wrong");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
