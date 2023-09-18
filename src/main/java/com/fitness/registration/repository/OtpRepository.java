package com.fitness.registration.repository;

import com.fitness.registration.model.OtpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpModel,Integer> {

    @Query(value = "select * from regs_otp ro \n" +
            "where ro.otp_email = :otpEmail \n" +
            "and ro.created_at > current_timestamp() - interval 1 minute ", nativeQuery = true)
    List<OtpModel> countUserOtp(@Param("otpEmail")String otpEmail);

    @Query(value = "select * from regs_otp ro \n" +
            "where ro.otp_email = :otpEmail and ro.otp_code = :otpCode\n" +
            "and current_timestamp() < ro.otp_expired_at", nativeQuery = true)
    Optional<OtpModel> verifyOtp(@Param("otpEmail")String otpEmail, @Param("otpCode")String otpCode);

    @Query(value = "select * from regs_otp ro \n" +
            "where ro.otp_email = :otpEmail and ro.otp_code = :otpCode\n" +
            "and current_timestamp() > ro.otp_expired_at", nativeQuery = true)
    Optional<OtpModel> getOtpExpired(@Param("otpEmail")String otpEmail, @Param("otpCode")String otpCode);

    List<OtpModel> findByOtpEmail(String otpEmail);
}
