package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Destinatario {

    String codigoTipoIdentificacion;
    String descripcionTipoIdentificacion;
    String numeroDocumentoIdentificacion;
    String apellidosNombresDenominacionRazonSocial;
    String numeroAutorizacion;
    String codigoEntidadAutorizadora;
    String numeroRegistroMTC;

}
