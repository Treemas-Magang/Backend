package com.treemaswebapi.treemaswebapi.entity.ReimburseParamEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_reimburse_param", schema = "public")
public class ReimburseParamEntity implements Serializable {

    @Id
    @Column(name = "reimburse_id", length = 10, nullable = false)
    private String reimburseId;

    @Column(name = "nama", length = 50)
    private String nama;

    @Column(name = "nominal")
    private BigDecimal nominal;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "is_leader", length = 1)
    private String isLeader;
}
