package com.alo.digital.facturacion.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "V_Destinos")
public class V_Destinos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nro; // numeric(7, 0)
    private String origen; // char(3)
    private String origend; // char(30)
    private String destino; // char(3)
    private String destinod; // char(30)
    private String agencia; // char(3)
    private String agenciad; // char(30)
    private BigDecimal kilometraje; // numeric(12, 2)
    private String codliq; // char(2)
    private String codigoweb; // char(4)

    @Column(nullable = false, columnDefinition = "varchar(200) default ''")
    private String direccionorigen; // varchar(200)

    @Column(nullable = false, columnDefinition = "varchar(200) default ''")
    private String direcciondestino; // varchar(200)

    private String liquidar; // char(1)
    private String tipoviaje; // char(10)
    private String agenciapasaje; // char(3)
    private String agenciadpasaje; // char(30)
    private Integer tiempoviajeapi; // int

    @Column(columnDefinition = "numeric(12, 2) default 0")
    private BigDecimal dotacion; // numeric(12, 2)

    private BigDecimal porc; // numeric(10, 2)

    @Column(nullable = false, columnDefinition = "int default 1")
    private Integer activo; // int

    private Boolean tdp; // bit
    private Integer pasajes; // int
    private Integer encomiendas; // int
    private Boolean viajesespeciales; // bit
    private BigDecimal sobre; // numeric(12, 2)
    private BigDecimal limite; // numeric(12, 2)
    private BigDecimal costoLimite; // numeric(12, 2)
    private BigDecimal kilosBus; // numeric(12, 2)
    private BigDecimal kilosFur; // numeric(12, 2)
    private BigDecimal montoFur; // numeric(12, 2)
    private BigDecimal domiciliado; // numeric(12, 2)
}

