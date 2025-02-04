package com.alo.digital.facturacion.entity.rest;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class DocumentoReferencia {

    String codigoTipoDocumento;
    String numero;
}
