package com.treemaswebapi.treemaswebapi.controller.NotifController.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest {
    private String isApprove;
    private String isApprove1;
    private String isApprove2;
    private String noteApp;
    private String noteApp1;
    private String noteApp2;
}
