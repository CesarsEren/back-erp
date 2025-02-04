package com.alo.digital.facturacion.entity.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseGeneraXML {

    private String numeroTicket;
    private String resultadoPresentacion;
    private String codigoRespuesta;
    private String descripcionRespuesta;
    private String constanciaRecepcion;
    private String descripcionExcepcion;
    private String descripcionMensaje;
    private String documentoFirmado;
    private String codigoExcepcion;
    private String representacionImpresa;
    private String fechaRecepcion;
    private String valorResumen;
    private String codigoBarras;
    private String estadoProceso;
    private String firmaDigital;
    private String codigoMensaje;
    private List<String> observaciones;

}
