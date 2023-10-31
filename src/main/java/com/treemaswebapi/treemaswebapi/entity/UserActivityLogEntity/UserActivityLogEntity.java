package com.treemaswebapi.treemaswebapi.entity.UserActivityLogEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_activity_log", schema = "public")
public class UserActivityLogEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_seqno")
    private Long activitySeqno;

    @Column(name = "userid", length = 15)
    private String userid;

    @Column(name = "branch_id", length = 20)
    private String branchId;

    @Column(name = "activity")
    private String activity;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;
}