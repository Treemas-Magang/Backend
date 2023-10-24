package com.treemaswebapi.treemaswebapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "listmember_project", schema="public")
public class ListMemberProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlistmember")
    private String idListMember;

    @ManyToOne
    @JoinColumn(name = "kode_project", referencedColumnName = "kode_project")
    private ProjectMaster kodeProject;

    @Column(name = "nik")
    private String nik;
}
