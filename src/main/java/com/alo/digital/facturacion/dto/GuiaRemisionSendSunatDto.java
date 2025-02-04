package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GuiaRemisionSendSunatDto {

    Integer nroGuia;
    Integer nroEncomienda;
    String serieNumeroGuia;
    String respuestaGeneracion;
    String respuestaSunat;
    String ticket;
    Integer sunatState;
    Boolean xmlGenerado;
    String sunatStateD;

}