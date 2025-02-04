package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transportista {
    String apellidosNombresDenominacionRazonSocial;
    String codigoEntidadAutorizadora;
    String codigoTipoIdentificacion;
    String descripcionTipoIdentificacion;
    String numeroAutorizacion;
    String numeroDocumentoIdentificacion;
    String numeroRegistroMTC;
}
