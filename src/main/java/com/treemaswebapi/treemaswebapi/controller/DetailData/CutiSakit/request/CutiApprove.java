package com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CutiApprove {
    private String noteApp;
    private String isApproved;
    private String usrApp;
}
