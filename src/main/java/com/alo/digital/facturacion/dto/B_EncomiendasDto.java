package com.alo.digital.facturacion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class B_EncomiendasDto {

    String nro;
    String serienumero;
    String destinod;
    String consignado;
    String remitente;
    String nrobultos;
    String pesototal;
    String costototal;

}
