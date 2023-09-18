package com.fitness.registration.service;

import com.fitness.registration.dto.*;
import com.fitness.registration.model.*;
import com.fitness.registration.repository.*;
import com.fitness.registration.response.BaseResponse;
import com.fitness.registration.response.ListProgramResponse;
import com.fitness.registration.response.ProgramResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionDetailRepository subscriptionDetailRepository;
    @Autowired
    private SubscriptionMemberRepository subscriptionMemberRepository;
    @Autowired
    private SubscriptionScheduleRepository subscriptionScheduleRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ListProgramDTO convertListProgramDTO(SubscriptionModel subscriptionModel){
        ListProgramDTO listProgramDTO = new ListProgramDTO();
        listProgramDTO.setSubId(subscriptionModel.getSubId());
        listProgramDTO.setSubMenu(subscriptionModel.getSubMenu());
        return listProgramDTO;
    }

    public ProgramDetailDTO convertProgramDetail(SubscriptionDetailModel subscriptionDetailModel){
        ProgramDetailDTO programDetailDTO = new ProgramDetailDTO();
        programDetailDTO.setSubsDetailDesc(subscriptionDetailModel.getSubsDetailDesc());
        programDetailDTO.setSubsDetailDuration(subscriptionDetailModel.getSubsDetailDuration());
        return programDetailDTO;
    }

    public ProgramScheduleDTO convertProgramScheduleDTO(SubscriptionScheduleModel subscriptionScheduleModel){
        ProgramScheduleDTO programScheduleDTO = new ProgramScheduleDTO();
        programScheduleDTO.setSubsSchedule(subscriptionScheduleModel.getSubsSchedule());
        return programScheduleDTO;
    }

    public SubscriptionStatusDTO convertSubscriptionStatus(SubscriptionMemberModel subscriptionMemberModel){
        SubscriptionStatusDTO subscriptionStatusDTO = new SubscriptionStatusDTO();
        subscriptionStatusDTO.setSubsMemberStatus(subscriptionMemberModel.getSubsMemberStatus());
        subscriptionStatusDTO.setSubsMemberSession(subscriptionMemberModel.getSubsMemberSession());
        return subscriptionStatusDTO;
    }

    public ProgramDTO convertProgramDTO(SubscriptionModel subscriptionModel,Long accountId){
        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setSubId(subscriptionModel.getSubId());
        programDTO.setSubMenu(subscriptionModel.getSubMenu());
        programDTO.setSubPrice(subscriptionModel.getSubPrice());
        programDTO.setSubDuration(subscriptionModel.getSubDuration());

        List<ProgramDetailDTO> subDetailDTO = subscriptionDetailRepository.findBySubscriptionId(subscriptionModel.getSubId())
                .stream().map(this::convertProgramDetail).collect(Collectors.toList());
        programDTO.setDetailDTO(subDetailDTO);

        List<ProgramScheduleDTO> subScheduleDTO = subscriptionScheduleRepository.findBySubscriptionId(subscriptionModel.getSubId())
                .stream().map(this::convertProgramScheduleDTO).collect(Collectors.toList());
        programDTO.setScheduleDTO(subScheduleDTO);

        Optional<SubscriptionMemberModel> subMember = subscriptionMemberRepository.findBySubscriptionIdAndSubsMemberAccountId(subscriptionModel.getSubId(), accountId);
        if (subMember.isEmpty()){
            SubscriptionStatusDTO subscriptionStatusDTO = new SubscriptionStatusDTO();
            subscriptionStatusDTO.setSubsMemberStatus("N");
            subscriptionStatusDTO.setSubsMemberSession(0);
            programDTO.setSubscriptionStatusDTO(subscriptionStatusDTO);
        }else {
            programDTO.setSubscriptionStatusDTO(convertSubscriptionStatus(subMember.get()));
        }

        return programDTO;

    }

    //list program
    public ListProgramResponse listPrograms(){
        List<ListProgramDTO> programs = subscriptionRepository.findAll().stream().map(this::convertListProgramDTO).collect(Collectors.toList());
        ListProgramResponse response = new ListProgramResponse();
        response.setCode("000");
        response.setMessage("Success");
        response.setData(programs);
        return response;
    }

    //detail program
    public ProgramResponse getProgramById(int subId,Long accountId){
        ProgramResponse response = new ProgramResponse();
        Optional<SubscriptionModel> subscriptionOpt = subscriptionRepository.findBySubId(subId);
        Optional<AccountModel> accountOpt = accountRepository.findByAccountId(accountId);

        if (subscriptionOpt.isEmpty() || accountOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            response.setData(new ProgramDTO());
            return response;
        }
        ProgramDTO programDTO = convertProgramDTO(subscriptionOpt.get(), accountId);

        response.setCode("000");
        response.setMessage("Success");
        response.setData(programDTO);
        return response;

    }

    //create new subscription
    public BaseResponse createSubscription(int subId,Long accountId){
        BaseResponse response = new BaseResponse();
        Optional<SubscriptionMemberModel> subMemberOpt = subscriptionMemberRepository.findBySubscriptionIdAndSubsMemberAccountId(subId, accountId);
        if (subMemberOpt.isPresent() && Objects.equals(subMemberOpt.get().getSubsMemberStatus(), "Y")){
            response.setCode("001");
            response.setMessage("Data already exists");
            return response;
        }

        if (subMemberOpt.isPresent() && Objects.equals(subMemberOpt.get().getSubsMemberStatus(), "N")){
            response.setCode("004");
            response.setMessage("Follow the payment instruction");
            return response;
        }

        Optional<AccountModel> accountOpt = accountRepository.findByAccountId(accountId);

        Optional<SubscriptionModel> subOpt = subscriptionRepository.findBySubId(subId);
        if (subOpt.isEmpty() || accountOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        SubscriptionMemberModel subscriptionMemberModel = new SubscriptionMemberModel();
        subscriptionMemberModel.setSubscriptionId(subId);
        subscriptionMemberModel.setSubsMemberStatus("N");
        subscriptionMemberModel.setSubsMemberSession(subOpt.get().getSubDuration());
        subscriptionMemberModel.setSubsMemberAccountId(accountId);
        subscriptionMemberModel.setCreatedAt(LocalDateTime.now());
        subscriptionMemberModel.setUpdatedAt(LocalDateTime.now());
        subscriptionMemberRepository.save(subscriptionMemberModel);

        response.setCode("000");
        response.setMessage("Success");
        return response;

    }

    public BaseResponse deleteSubscription(int subMemberId){
        BaseResponse response = new BaseResponse();
        Optional<SubscriptionMemberModel> submemberOpt = subscriptionMemberRepository.findBySubMemberId(subMemberId);
        if (submemberOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        subscriptionMemberRepository.delete(submemberOpt.get());

        response.setCode("000");
        response.setMessage("Success");
        return response;
    }

    //add more session
    public BaseResponse addSession(int subMemberId){
        BaseResponse response = new BaseResponse();
        Optional<SubscriptionMemberModel> subMemberOpt = subscriptionMemberRepository.findBySubMemberId(subMemberId);
        if (subMemberOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        int subscriptionId = subMemberOpt.get().getSubscriptionId();
        Optional<SubscriptionModel> subscriptionOpt = subscriptionRepository.findBySubId(subscriptionId);
        if (subscriptionOpt.isEmpty()){
            response.setCode("002");
            response.setMessage("Data not found");
            return response;
        }

        int subDuration = subscriptionOpt.get().getSubDuration();

        SubscriptionMemberModel subscriptionMemberModel = new SubscriptionMemberModel();
        subscriptionMemberModel.setSubsMemberSession(subDuration);
        subscriptionMemberRepository.save(subscriptionMemberModel);

        response.setCode("000");
        response.setMessage("Success");
        return response;
    }

}
