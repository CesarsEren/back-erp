package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "v_grr_emisor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrEmisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emisorid;

    private String codigotipodocumentoidentidad;
    private String numerodocumentoidentidad;
    private String apellidosnombresdenominacionrazonsocial;
    private String nombrecomercial;
    private String codigopais;
    private String codigoubicaciongeografica;
    private String codigoestablecimientoanexo;
    private String direccionEstablecimientoAnexo;
    private String departamento;
    private String provincia;
    private String distrito;
    private String urbanizacion;
    private String direccion;

    // Getters and Setters
}
