package com.treemaswebapi.treemaswebapi.controller.NotifController.response;

import java.util.List;
import java.util.Optional;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppUploadEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenPulangAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppUploadEntity;
import com.treemaswebapi.treemaswebapi.entity.GeneralEntity.GeneralParamApprovalEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseAppEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ApprovalResponse {
    //List
    private List<AbsenAppEntity> lemburApprovals;
    private List<AbsenAppEntity> liburApprovals;
    private List<CutiAppEntity> sakitApprovals;
    private List<CutiAppEntity> cutiApprovals;
    private List<CutiAppUploadEntity> cutiApprovalWebs;
    private List<AbsenAppUploadEntity> absenWebApprovals;
    private List<AbsenPulangAppEntity> absenPulangApprovals;
    private List<GeneralParamApprovalEntity> generalParamApprovals;
    private List<ReimburseAppEntity> reimburseApprovals;
    // Optional
    private Optional<AbsenAppUploadEntity> absenWebApproval;
    private Optional<AbsenPulangAppEntity> absenPulangApproval;
    private Optional<GeneralParamApprovalEntity> generalParamApproval;
    private Optional<ReimburseAppEntity> reimburseApproval;
    private Optional<AbsenAppEntity> lemburApproval;
    private Optional<AbsenAppEntity> liburApproval;
    private Optional<CutiAppEntity> sakitApproval;
    private Optional<CutiAppEntity> cutiApproval;
    private Optional<CutiAppUploadEntity> cutiApprovalWeb;
    private Long dataCounter;
    
}
