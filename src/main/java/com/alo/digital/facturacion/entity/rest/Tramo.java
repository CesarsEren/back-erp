package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tramo {

    String numeroCorrelativo;
    String modalidad;
    String fechaEntregaBienInicioTraslado;
    String tipoEvento;
    String oTransportista;
    Conductor[] lConductorSecundario;
    Conductor oConductorPrincipal;
}
