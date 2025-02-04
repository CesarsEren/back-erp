package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrtGeneradoDto {
    Integer nro;
    String serieNumeroEncomienda;
    String serieNumeroGuia;
    String link_pdf;
    String link_xml;
    boolean generado;

    public GrtGeneradoDto(Integer nro, String serieNumeroEncomienda, String serieNumeroGuia, boolean generado) {
        this.nro = nro;
        this.serieNumeroEncomienda = serieNumeroEncomienda;
        this.serieNumeroGuia = serieNumeroGuia;
        this.generado = generado;
    }
}
