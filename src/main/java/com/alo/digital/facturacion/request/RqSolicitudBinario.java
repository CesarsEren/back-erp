package com.alo.digital.facturacion.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RqSolicitudBinario {

    String nroGuia;
    String nombreDocumento;
}
