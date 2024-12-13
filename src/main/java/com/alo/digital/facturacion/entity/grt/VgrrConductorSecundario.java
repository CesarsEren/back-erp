package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "v_grr_conductorsecundario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrConductorSecundario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer conductorsecundarioid;

    private Integer tramoid;
    private String codigotipoidentificacion;
    private String numerodocumentoidentificacion;
    private String nombres;
    private String apellidos;
    private String numerolicenciaconducir;

    // Getters and Setters
}
