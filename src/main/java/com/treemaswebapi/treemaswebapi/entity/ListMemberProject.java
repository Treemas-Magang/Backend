// package com.treemaswebapi.treemaswebapi.entity;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;

// import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;

// @Entity
// @Table(name = "listmember_project", schema="public")
// public class ListMemberProject {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "idlistmember")
//     private String idListMember;

//     @ManyToOne(targetEntity=ProjectMaster.class)
//     @JoinColumn( name = "kode_project", referencedColumnName = "kode_project")
//     private ProjectMaster kodeProject;

//     @ManyToOne
//     @JoinColumn(name = "nik", referencedColumnName = "nik")
//     private UserEntity userNik;

//     @ManyToOne
//     @JoinColumn(name = "nama_karyawan", referencedColumnName = "nik", insertable = false, updatable = false)
//     private UserEntity userName;

//     public String getIdListMember() {
//         return idListMember;
//     }

//     public void setIdListMember(String idListMember) {
//         this.idListMember = idListMember;
//     }

//     public ProjectMaster getProject() {
//         return kodeProject;
//     }

//     public void setProject(ProjectMaster kodeProject) {
//         this.kodeProject= kodeProject;
//     }

//     public UserEntity getUserNik() {
//         return userNik;
//     }

//     public void setUserNik(UserEntity userNik) {
//         this.userNik = userNik;
//     }

//     public UserEntity getUserName() {
//         return userName;
//     }

//     public void setUserName(UserEntity userName) {
//         this.userName = userName;
//     }
// }
