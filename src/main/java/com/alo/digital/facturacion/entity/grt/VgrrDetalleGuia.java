package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "v_grr_detalleguia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrDetalleGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detalleguiaid;

    private Integer documentoid;
    private Integer numeroorden;
    private BigDecimal cantidad;
    private String productodescripcion;
    private String codigounidadmedida;

    // Getters and Setters
}
