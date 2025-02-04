package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Emisor {

    String codigoTipoDocumentoIdentidad;
    String numeroDocumentoIdentidad;
    String apellidosNombresDenominacionRazonSocial;
    String nombreComercial;
    String codigoPais;
    String codigoUbicacionGeografica;
    String codigoEstablecimientoAnexo;
    String departamento;
    String provincia;
    String distrito;
    String urbanizacion;
    String direccion;
    String direccionEstablecimientoAnexo;
}
