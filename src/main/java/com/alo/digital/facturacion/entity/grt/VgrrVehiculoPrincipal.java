package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "v_grr_vehiculoprincipal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrVehiculoPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehiculoprincipalid;

    private String numeroplaca;
    private String tarjetaunicacertificado;
    private String numeroautorizacion;
    private String codigoentidadautorizadora;

    // Getters and Setters
}
