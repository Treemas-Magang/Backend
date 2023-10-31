package com.treemaswebapi.treemaswebapi.entity.SysUserEntity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_user_member", schema = "public")
public class SysUserMember {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nik_leader")
    private String nikLeader;

    @Column(name = "nik_user")
    private String nikUser;

    @Column(name = "usrupd")
    private String usrUpd;

    @Column(name = "dtmupd")
    private Instant dtmUpd;
}
