package com.fitness.registration.utils;

import com.fitness.registration.model.AccountModel;
import com.fitness.registration.model.OtpModel;
import com.fitness.registration.repository.AccountRepository;
import com.fitness.registration.repository.OtpRepository;
import freemarker.core.ParseException;
import freemarker.template.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SendOTPUtils {

    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration config;
    @Autowired
    private AccountRepository accountRepository;

    public static final int SUCCESS = 0;
    public static final int FAILED_MAX_RETRY = 1;
    public static final int DATA_NOT_FOUND = 3;

    public int sendMembershipStatusEmail(String accountEmail,String accountName) throws MessagingException {
        Optional<AccountModel> accountOpt = accountRepository.findByAccountEmail(accountEmail);
        if (accountOpt.isEmpty()){
            return DATA_NOT_FOUND;
        }

        jakarta.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        try {
            Template template = config.getTemplate("status-member.html");
            Map<String, String> model = new HashMap<>();
            model.put("name",accountName);
            model.put("status", accountOpt.get().getAccountStatus());

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setFrom("no-reply@bubufitness.com");
            helper.setTo(accountEmail);
            helper.setSubject("Membership Status");
            helper.setText(html, true);
            emailSender.send(mimeMessage);

        } catch (TemplateNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (MalformedTemplateNameException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

        return SUCCESS;
    }

    public int sendPaymentOtp(String otpEmail, String accountName) throws MessagingException {
        String otpCode = generateOTP();
        long otpExpired = 1; //in sec

        jakarta.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        try{
            Template template = config.getTemplate("payment-code.html");
            Map<String, String> model = new HashMap<>();
            model.put("name",accountName);
            model.put("otpcode", otpCode);

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setFrom("no-reply@bubufitness.com");
            helper.setTo(otpEmail);
            helper.setSubject("Payment OTP Code");
            helper.setText(html, true);
            emailSender.send(mimeMessage);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (TemplateNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (MalformedTemplateNameException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OtpModel otpModel = new OtpModel();
        otpModel.setOtpEmail(otpEmail);
        otpModel.setOtpCode(otpCode);
        otpModel.setCreatedAt(LocalDateTime.now());
        otpModel.setUpdatedAt(LocalDateTime.now());
        otpModel.setOtpExpiredAt(LocalDateTime.now().plusMinutes(1));
        otpRepository.save(otpModel);

        System.out.println("kode OTP adalah = " + otpExpired);

        return SUCCESS;
    }

    public int sendOTP(String otpEmail, String accountName) throws MessagingException {
        String otpCode = generateOTP();
        long otpExpired = 1; //in sec

        List<OtpModel> countUserOtp = otpRepository.countUserOtp(otpEmail);
        if (!countUserOtp.isEmpty() && countUserOtp.size() >= 3){
            return FAILED_MAX_RETRY;
        }

        jakarta.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        try {
            Template template = config.getTemplate("verify.html");
            Map<String, String> model = new HashMap<>();
            model.put("name", accountName);
            model.put("otpcode", otpCode);

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setFrom("no-reply@bubufitness.com");
            helper.setTo(otpEmail);
            helper.setSubject("Email Verification");
            helper.setText(html, true);
            emailSender.send(mimeMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

        LocalDateTime now = LocalDateTime.now();

        OtpModel otpModel = new OtpModel();
        otpModel.setOtpEmail(otpEmail);
        otpModel.setOtpCode(otpCode);
        otpModel.setCreatedAt(now);
        otpModel.setUpdatedAt(now);
        otpModel.setOtpExpiredAt(now.plusMinutes(otpExpired));
        otpRepository.save(otpModel);

        System.out.println("kode OTP adalah = " + otpCode);

        return SUCCESS;
    }

    public String generateOTP(){
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
