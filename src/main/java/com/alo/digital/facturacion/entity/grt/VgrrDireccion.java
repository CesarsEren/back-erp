package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "v_grr_direccion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrDireccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer direccionid;

    private String ubigeo;
    private String descripcion;
    private String numerorucasociado;
    private String codigoestablecimiento;
    private BigDecimal puntogeorreferencialongitud;
    private BigDecimal puntogeorreferencialatitud;

    // Getters and Setters
}
