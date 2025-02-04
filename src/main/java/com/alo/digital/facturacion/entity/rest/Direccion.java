package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Direccion {

    String ubigeo;
    String descripcion;
    String numeroRucAsociado;
    String codigoEstablecimiento; //null
    Double puntoGeorreferenciaLongitud;
    Double puntoGeorreferenciaLatitud;

}
