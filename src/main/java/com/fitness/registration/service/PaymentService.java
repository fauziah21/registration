package com.fitness.registration.service;

import com.fitness.registration.model.*;
import com.fitness.registration.repository.*;
import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.utils.SendOTPUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SubscriptionMemberRepository subscriptionMemberRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SendOTPUtils sendOTPUtils;
    @Autowired
    private OtpRepository otpRepository;

    public static int SEND_OTP_SUCCESS = 0;
    public static int FAILED_SEND_OTP = 2;

    public int sendPaymentOtp(String otpEmail, String accountName) throws MessagingException {
        int result = sendOTPUtils.sendPaymentOtp(otpEmail, accountName);
        if (result == SendOTPUtils.SUCCESS) {
            return SEND_OTP_SUCCESS;
        }else {
            return FAILED_SEND_OTP;
        }
    }

    /*
    * 1. cek apakah user exists
    * 2. cek apakah prog exists
    * 3. kirim email*/
    public BaseResponse verifyPayment(Long accountId, int subId) throws MessagingException {
        BaseResponse response = new BaseResponse();
        Optional<SubscriptionMemberModel> subMemberAccountOpt = subscriptionMemberRepository.findBySubscriptionIdAndSubsMemberAccountId(subId,accountId);
        if (subMemberAccountOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        int subscriptionId = subMemberAccountOpt.get().getSubscriptionId();
        Optional<SubscriptionModel> subscriptionOpt = subscriptionRepository.findBySubId(subscriptionId);
        if (subscriptionOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }
        int subPrice = subscriptionOpt.get().getSubPrice();

        Optional<AccountModel> accountOpt = accountRepository.findByAccountId(accountId);
        String accountName = accountOpt.get().getAccountName();
        String accountEmail = accountOpt.get().getAccountEmail();

        int resultEmail = sendPaymentOtp(accountEmail, accountName);

        if (resultEmail != SendOTPUtils.SUCCESS){
            response.setCode("099");
            response.setMessage("Something wrong");
            return response;
        }

        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setAccountId(accountId);
        paymentModel.setPaymentTotal(subPrice);
        paymentModel.setCreatedAt(LocalDateTime.now());
        paymentModel.setUpdatedAt(LocalDateTime.now());
        paymentModel.setPaymentStatus("PROCESSING");
        paymentModel.setPaymentSubscriptionId(subscriptionId);
        paymentRepository.save(paymentModel);

        response.setCode("000");
        response.setMessage("Email Sent");
        return response;

    }

    /*
    * 1. otp tidak cocok (tapi masih blm expired) = gagal
    * 2. otp expired = gagal
    * 3. otp sesuai && belum expired == sukses, status langganan = Y, payment status = lunas*/
    public BaseResponse verifyOTP(String otpEmail,String otpCode){
        BaseResponse response = new BaseResponse();
        Optional<OtpModel> otpOpt = otpRepository.verifyOtp(otpEmail, otpCode);

        Optional<AccountModel> accountOtp = accountRepository.findByAccountEmail(otpEmail);
        if (accountOtp.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        List<OtpModel> getOtpByEmailOpt = otpRepository.findByOtpEmail(otpEmail);
        if (!getOtpByEmailOpt.isEmpty()){
            otpRepository.deleteAll(getOtpByEmailOpt);
        }

        Long accountId = accountOtp.get().getAccountId();
        Optional<PaymentModel> paymentOpt = paymentRepository.findByAccountId(accountId);
        if (paymentOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        PaymentModel paymentCurrent = paymentOpt.get();

        if (otpOpt.isEmpty()){
            paymentCurrent.setPaymentStatus("Failed");
            paymentCurrent.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(paymentCurrent);

            response.setCode("003");
            response.setMessage("data not valid");
            return response;
        }

        //otp tidak cocok status pembayaran = gagal
        if (!Objects.equals(otpOpt.get().getOtpCode(), otpCode)){

            paymentCurrent.setPaymentStatus("Failed");
            paymentCurrent.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(paymentCurrent);

            otpRepository.delete(otpOpt.get());

            response.setCode("003");
            response.setMessage("OTP not valid");
            return response;
        }

        Optional<OtpModel> otpExpiredOpt = otpRepository.getOtpExpired(otpEmail, otpCode);
        if (otpExpiredOpt.isPresent()){

            paymentCurrent.setPaymentStatus("Failed");
            paymentCurrent.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(paymentCurrent);

            otpRepository.delete(otpOpt.get());

            response.setCode("005");
            response.setMessage("Data expired");
            return response;
        }

        //delete otp
        otpRepository.delete(otpOpt.get());

        //update status lunas
        paymentCurrent.setPaymentStatus("Paid");
        paymentCurrent.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(paymentCurrent);

        otpRepository.delete(otpOpt.get());

        Optional<SubscriptionMemberModel> subMemberOpt = subscriptionMemberRepository.findBySubsMemberAccountId(accountOtp.get().getAccountId());
        if (subMemberOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("subMember not found");
            return response;
        }

        SubscriptionMemberModel subMemberCurrent = subMemberOpt.get();
        subMemberCurrent.setSubsMemberStatus("Y");
        subMemberCurrent.setUpdatedAt(LocalDateTime.now());
        subscriptionMemberRepository.save(subMemberCurrent);

        response.setCode("000");
        response.setMessage("Data Valid");
        return response;

    }

}
