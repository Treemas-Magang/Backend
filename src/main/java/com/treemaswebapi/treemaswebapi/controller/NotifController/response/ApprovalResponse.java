package com.treemaswebapi.treemaswebapi.controller.NotifController.response;

import java.util.List;

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
    private List<AbsenAppEntity> lemburApproval;
    private List<AbsenAppEntity> liburApproval;
    private List<CutiAppEntity> sakitApproval;
    private List<CutiAppEntity> cutiApproval;
    private List<CutiAppUploadEntity> cutiApprovalWeb;
    private List<AbsenAppUploadEntity> absenWebApproval;
    private List<AbsenPulangAppEntity> absenPulangApproval;
    private List<GeneralParamApprovalEntity> generalParamApproval;
    private List<ReimburseAppEntity> reimburseApproval;
    private Long dataCounter;
    
}
