package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "v_grr_remitente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrRemitente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer remitenteid;

    private String codigotipoidentificacion;
    private String descripciontipoidentificacion;
    private String numerodocumentoidentificacion;
    private String apellidosnombresdenominacionrazonsocial;
    private String numeroautorizacion;
    private String codigoentidadautorizadora;
    private String numeroregistromtc;

    // Getters and Setters
}