package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class V_GuiaRemisionGeneradoDto {

    Integer id;
    String nroguia;
    String numeroticket;
    String resultadopresentacion;
    String codigorespuesta;
    String descripcionrespuesta;
    String constanciarecepcion;
    String descripcionexcepcion;
    String descripcionmensaje;
    String documentofirmado;
    String codigoexcepcion;
    String representacionimpresa;
    LocalDate fecharecepcion;
    String valorresumen;
    String codigobarras;
    String estadoproceso;
    String firmadigital;
    String codigomensaje;
    String observaciones;

}
