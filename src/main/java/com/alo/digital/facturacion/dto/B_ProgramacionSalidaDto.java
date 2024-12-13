package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class B_ProgramacionSalidaDto {

    String nro;
    String destinod;

    String agenciaOrigen;
    String direccionAgenciaOrigen;

    String nroBus;
    String placaBus;
    String marcaBus;

    List<ConductorDto> conductores;

}
