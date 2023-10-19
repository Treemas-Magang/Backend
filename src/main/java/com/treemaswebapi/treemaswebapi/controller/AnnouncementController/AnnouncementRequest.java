package com.treemaswebapi.treemaswebapi.controller.AnnouncementController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementRequest {
    private String titleAnn;
    private String bodyAnn;
    private String createdBy;
    // Include any other fields that you want to send in the request
}
