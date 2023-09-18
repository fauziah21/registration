package com.fitness.registration.controller;

import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PatchMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(@RequestParam("accountEmail")String accountEmail,
                                                       @RequestParam("accountPassword")String accountPassword){
        BaseResponse response = profileService.changePassword(accountEmail, accountPassword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<BaseResponse> updateProfile(@RequestParam("accountEmail")String accountEmail,
                                                      @RequestParam("accountName")String accountName,
                                                      @RequestParam("accountCreditCard")String accountCreditCard,
                                                      @RequestParam("accountCardCvv")String accountCardCvv,
                                                      @RequestParam("accountCardExp")String accountCardExp,
                                                      @RequestParam("accountCardName")String accountCardName){
        BaseResponse response = profileService.updateProfile(accountEmail, accountName, accountCreditCard, accountCardCvv, accountCardExp, accountCardName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
