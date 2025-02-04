package com.alo.digital.facturacion.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Cierremanifiesto")
public class CierreManifiesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    @Column(name = "Nroprog")
    Integer nroProg;
    @Column(name = "Agencia")
    String agencia;
    @Column(name = "Agenciad")
    String agenciaD;
    @Column(name = "Tipomanifiesto")
    String tipoManifiesto;
    @Column(name = "Tipomanifiestod")
    String tipoManifiestoD;
    @Column(name = "Terminal")
    String terminal;
    @Column(name = "grts_generados")
    Boolean grtsGenerados;

}