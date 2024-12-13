package com.alo.digital.facturacion.entity.grt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "v_grr_tramo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrTramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tramoid;

    private Integer enviosid;
    private String numerocorrelativo;
    private String modalidad;
    private LocalDateTime fechaentregabieniniciotraslado;
    private String tipoevento;

    // Getters and Setters
}
