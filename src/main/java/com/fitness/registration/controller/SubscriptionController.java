package com.fitness.registration.controller;

import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.response.ListProgramResponse;
import com.fitness.registration.response.ProgramResponse;
import com.fitness.registration.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("")
    public ResponseEntity<ListProgramResponse> listProgram(){
        ListProgramResponse listProgramResponse = subscriptionService.listPrograms();
        return ResponseEntity.status(HttpStatus.OK).body(listProgramResponse);
    }

    @GetMapping("/detail")
    public ResponseEntity<ProgramResponse> getProgramById(@RequestParam("subId")int subId,
                                                          @RequestParam("accountId")Long accountId){
        ProgramResponse result = subscriptionService.getProgramById(subId, accountId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createSubscription(@RequestParam("subId")int subId,
                                                           @RequestParam("accountId")Long accountId){
        BaseResponse subscription = subscriptionService.createSubscription(subId, accountId);
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteSubscription(@RequestParam("subMemberId")int subMemberId){
        BaseResponse response = subscriptionService.deleteSubscription(subMemberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/add")
    public ResponseEntity<BaseResponse> addSession(@RequestParam("subMemberId")int subMemberId){
        BaseResponse response = subscriptionService.addSession(subMemberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
