package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuiaRemisionTransportista {

    String codigoTipoDocumento;
    String numeroSerie;
    String numeroCorrelativo;
    String fechaEmision;
    String horaEmision;
    String observaciones;
    String oGuiaBaja;
    List<DocumentoReferencia> lDocumentoReferencia;
    Remitente oRemitente;
    Destinatario oDestinatario;
    String oProveedor;
    String oComprador;
    String oTerceroPagaServicio;
    Envio oEnvio;
    Emisor oEmisor;
    List<GrtDetalle> lDetalleGuia;
    Transportista oTransportista;
    String parametrosAdicionalesReporte;

}
