package com.treemaswebapi.treemaswebapi.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.hibernate.annotations.Type;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.treemaswebapi.treemaswebapi.repository.AbsenImageRepo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "absen_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbsenImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "absen_image_id_absen_image_seq" )
    @SequenceGenerator(name = "absen_image_id_absen_image_seq", sequenceName = "absen_image_id_absen_image_seq", allocationSize = 1)
    @Column(name = "id_absen_image")
    private int idAbsenImg;

    @ManyToOne
    @JoinColumn(name = "id_absen", referencedColumnName = "id_absen")
    private AbsenMaster idAbsen;

    @ManyToOne
    @JoinColumn(name = "nik", referencedColumnName = "nik")
    private UserEntity nik;

    @Lob
    @Column(name = "image")
    private byte[] imageData;

    @Column(name = "image_timestamp")
    private LocalDateTime imageTimestamp;
}