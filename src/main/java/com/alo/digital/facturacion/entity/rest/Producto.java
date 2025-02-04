package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producto {

    String codigo;
    String descripcion;
    String codigoUnidadMedida;
    String codigoSunat;
}
