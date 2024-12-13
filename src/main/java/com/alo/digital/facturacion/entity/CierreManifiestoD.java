package com.alo.digital.facturacion.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cierremanifiestod")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CierreManifiestoD {

    @Column(name = "ID", nullable = false)
    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Iddetalle", nullable = false)
    private Integer idDetalle;

    @Column(name = "nroencomienda", nullable = false)
    private Integer nroEncomienda;

}