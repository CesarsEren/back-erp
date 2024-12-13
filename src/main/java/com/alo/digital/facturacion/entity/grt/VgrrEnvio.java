package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "v_grr_envio")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enviosid;

    private String identificador;
    private String codigomotivo;
    private String descripcionmotivo;
    private String sustentodiferenciapeso;
    private BigDecimal pesobrutoitemsseleccionados;
    private String unidadmedidaitemsseleccionados;
    private BigDecimal pesobruto;
    private String unidadmedida;
    private Integer numerobultos;
    private Integer vehiculoprincipalid;

    // Getters and Setters
}
