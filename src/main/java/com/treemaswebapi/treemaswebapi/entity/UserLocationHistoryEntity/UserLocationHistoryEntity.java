package com.treemaswebapi.treemaswebapi.entity.UserLocationHistoryEntity;

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
@Table(name = "user_location_history", schema = "public")
public class UserLocationHistoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_history_seqno")
    private Long locHistorySeqno;

    @Column(name = "userid", length = 40)
    private String userid;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "accuracy")
    private Long accuracy;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;
}