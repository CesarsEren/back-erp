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
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "ciudadD")
    private String ciudadD;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "destino")
    private String destino;

    @Column(name = "color")
    private String color;

    @Column(name = "intermedio")
    private String intermedio;

    @Column(name = "sistema")
    private String sistema;

    @Column(name = "codliq")
    private String codLiq;

    @Column(name = "giroradial")
    private String giroRadial;

    @Column(name = "agenciavje")
    private String agenciaVje;

    @Column(name = "manejacodsecreto")
    private String manejaCodSecreto;

    @Column(name = "codigoweb")
    private String codigoWeb;

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "responsabled")
    private String responsableD;

    @Column(name = "puntoventa")
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

    @Column(name = "ubigeo")
    private String ubigeo;

    // Getters y setters...
}
