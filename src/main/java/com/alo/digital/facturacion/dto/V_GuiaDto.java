package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class V_GuiaDto {

    Integer id;
    String guia;
    String cpeEnlazado;
    String destinario;
    LocalDate fechaemision;
    String origen;
    String destino;

}
