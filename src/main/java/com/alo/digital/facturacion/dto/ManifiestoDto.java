package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ManifiestoDto {

    Integer id;
    String agencia;
    Integer nro;
    String destino;
    LocalDateTime fecha;
    String servicio;
    String servicioD;
    String bus;
    Long cantidadb;
    Integer generado;

    public ManifiestoDto(Integer id, String agencia, Integer nro, String destino, LocalDateTime fecha, String servicio, String servicioD, String bus, Long cantidadb, Integer generado) {
        this.id = id;
        this.agencia = agencia;
        this.nro = nro;
        this.destino = destino;
        this.fecha = fecha;
        this.servicio = servicio;
        this.servicioD = servicioD;
        this.bus = bus;
        this.cantidadb = cantidadb;
        this.generado = generado;
    }
}
