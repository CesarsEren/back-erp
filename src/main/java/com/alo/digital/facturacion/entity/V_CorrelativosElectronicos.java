package com.alo.digital.facturacion.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "V_correlativos_electronicos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V_CorrelativosElectronicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Clave primaria autogenerada

    private String empresa; // char(3)
    private String empresad; // char(50)
    private String terminal; // char(15)
    private String terminald; // char(15)
    private String agencia; // char(3)
    private String agenciad; // char(30)
    private String documento; // char(2)
    private String documentod; // char(30)
    private String serie; // char(3)

    private BigDecimal correlativo; // numeric(10, 0)
    private BigDecimal del; // numeric(10, 0)
    private BigDecimal al; // numeric(10, 0)

    private String dobleserie; // char(1)
}