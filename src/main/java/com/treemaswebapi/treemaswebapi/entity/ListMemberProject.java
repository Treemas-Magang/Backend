// package com.treemaswebapi.treemaswebapi.entity;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// @Entity
// @Table(name = "listmember_project", schema="public")
// public class ListMemberProject {
//     @Id
//     @Column(name = "idlistmember")
//     private String idListMember;

//     @ManyToOne(targetEntity=ProjectMaster.class)
//     @JoinColumn( name = "kode_project", referencedColumnName = "kode_project")
//     private ProjectMaster kodeProject;

//     @ManyToOne
//     @JoinColumn(name = "nik", referencedColumnName = "nik")
//     private UserEntity nik;

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

//     public UserEntity getNik() {
//         return nik;
//     }

//     public void setNik(UserEntity nik) {
//         this.nik = nik;
//     }

//     public UserEntity getUserName() {
//         return userName;
//     }

//     public void setUserName(UserEntity userName) {
//         this.userName = userName;
//     }
// }
