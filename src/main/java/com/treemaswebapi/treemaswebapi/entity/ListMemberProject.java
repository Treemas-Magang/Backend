package com.treemaswebapi.treemaswebapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table
public class ListMemberProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_listmember")
    private Long idListMember;

    @ManyToOne
    @JoinColumn(name = "kode_project", referencedColumnName = "kode_project")
    private ProjectMaster project;

    @ManyToOne
    @JoinColumn(name = "nik", referencedColumnName = "nik")
    private UserEntity userNik;

    @ManyToOne
    @JoinColumn(name = "nama_karyawan", referencedColumnName = "nik", insertable = false, updatable = false)
    private UserEntity userName;

    public Long getIdListMember() {
        return idListMember;
    }

    public void setIdListMember(Long idListMember) {
        this.idListMember = idListMember;
    }

    public ProjectMaster getProject() {
        return project;
    }

    public void setProject(ProjectMaster project) {
        this.project = project;
    }

    public UserEntity getUserNik() {
        return userNik;
    }

    public void setUserNik(UserEntity userNik) {
        this.userNik = userNik;
    }

    public UserEntity getUserName() {
        return userName;
    }

    public void setUserName(UserEntity userName) {
        this.userName = userName;
    }
}
