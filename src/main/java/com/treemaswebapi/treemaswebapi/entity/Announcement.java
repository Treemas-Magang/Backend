package com.treemaswebapi.treemaswebapi.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcementmaster")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ann")
    private int idAnn;

    @Column(name = "title_ann", length = 100)
    private String titleAnn;

    @Column(name = "date_ann")
    @Temporal(TemporalType.DATE)
    private Date dateAnn;

    @Column(name = "body_ann", columnDefinition = "text")
    private String bodyAnn;

    @Column(name = "createdby", length = 255)
    private String createdBy;
}
