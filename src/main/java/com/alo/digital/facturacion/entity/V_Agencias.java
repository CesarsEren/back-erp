package com.alo.digital.facturacion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "V_Agencias")
public class V_Agencias {

    @Id
    @Column(name = "codigo", length = 1)
    private String codigo;

    @Column(name = "detalle", length = 1)
    private String detalle;

    @Column(name = "ciudad", length = 1)
    private String ciudad;

    @Column(name = "ciudadD", length = 1)
    private String ciudadD;

    @Column(name = "direccion", length = 1)
    private String direccion;

    @Column(name = "telefono", length = 1)
    private String telefono;

    @Column(name = "destino", length = 1)
    private String destino;

    @Column(name = "color", length = 1)
    private String color;

    @Column(name = "intermedio", length = 1)
    private String intermedio;

    @Column(name = "sistema", length = 1)
    private String sistema;

    @Column(name = "codliq", length = 1)
    private String codLiq;

    @Column(name = "giroradial", length = 1)
    private String giroRadial;

    @Column(name = "agenciavje", length = 1)
    private String agenciaVje;

    @Column(name = "manejacodsecreto", length = 1)
    private String manejaCodSecreto;

    @Column(name = "codigoweb", length = 1)
    private String codigoWeb;

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "responsable", length = 1)
    private String responsable;

    @Column(name = "responsabled", length = 1)
    private String responsableD;

    @Column(name = "puntoventa", length = 1)
    private String puntoVenta;

    @Column(name = "giros")
    private BigDecimal giros;

    @Column(name = "cargueros")
    private BigDecimal cargueros;

    @Column(name = "encomiendas")
    private BigDecimal encomiendas;

    @Column(name = "embarquemovil")
    private Boolean embarqueMovil;

    @Column(name = "urlfoto")
    private String urlFoto;

    @Column(name = "pasajes")
    private Boolean pasajes;

    @Column(name = "carga")
    private Boolean carga;

    @Column(name = "tdp")
    private Boolean tdp;

    @Column(name = "viajeespecial")
    private Boolean viajeEspecial;

    @Column(name = "desactivo")
    private Boolean desactivo;

    @Column(name = "transferencias")
    private Boolean transferencias;

    @Column(name = "tercero")
    private Boolean tercero;

    // Getters y setters...
}
