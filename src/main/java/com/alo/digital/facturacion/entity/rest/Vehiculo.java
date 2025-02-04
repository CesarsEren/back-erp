package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehiculo {

    String numeroPlaca;
    String tarjetaUnicaCertificado;
    String numeroAutorizacion;
    String codigoEntidadAutorizadora;

}
