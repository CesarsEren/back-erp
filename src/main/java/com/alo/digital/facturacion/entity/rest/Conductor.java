package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Conductor {
    String codigoTipoIdentificacion;
    String numeroDocumentoIdentificacion;
    String nombres;
    String apellidos;
    String numeroLicenciaConducir;
}
