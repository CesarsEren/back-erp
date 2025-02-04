package com.alo.digital.facturacion.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import java.time.LocalDateTime;

import lombok.Data;

@Entity
@Table(name = "b_encomiendas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class B_Encomiendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro")
    private BigDecimal nro;

    @Column(name = "salida")
    private BigDecimal salida;

    @Column(name = "destino")
    private BigDecimal destino;

    @Column(name = "destinod")
    private String destinoD;

    @Column(name = "fechaviaje")
    private LocalDateTime fechaViaje;

    @Column(name = "horaviaje")
    private String horaViaje;

    @Column(name = "horaviajeini")
    private String horaViajeIni;

    @Column(name = "fechaemision")
    private LocalDateTime fechaEmision;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "empresad")
    private String empresaD;

    @Column(name = "documento")
    private String documento;

    @Column(name = "documentod")
    private String documentoD;

    @Column(name = "serie")
    private String serie;

    @Column(name = "numero")
    private String numero;

    @Column(name = "destino1")
    private String destino1;

    @Column(name = "destino1d")
    private String destino1D;

    @Column(name = "otraoficina")
    private String otraOficina;

    @Column(name = "transaccion")
    private String transaccion;

    @Column(name = "pago")
    private String pago;

    @Column(name = "valija")
    private String valija;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "dnigiro")
    private String dniGiro;

    @Column(name = "razon")
    private String razon;

    @Column(name = "remitente")
    private String remitente;

    @Column(name = "remitented")
    private String remitenteD;

    @Column(name = "consignado")
    private String consignado;

    @Column(name = "consignadod")
    private String consignadoD;

    @Column(name = "cantidad1")
    private String cantidad1;

    @Column(name = "descripcion1")
    private String descripcion1;

    @Column(name = "cantidad2")
    private String cantidad2;

    @Column(name = "descripcion2")
    private String descripcion2;

    @Column(name = "cantidad3")
    private String cantidad3;

    @Column(name = "descripcion3")
    private String descripcion3;

    @Column(name = "montogiro")
    private BigDecimal montoGiro;

    @Column(name = "kilos")
    private BigDecimal kilos;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "totalreal")
    private BigDecimal totalReal;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "agencia")
    private String agencia;

    @Column(name = "agenciad")
    private String agenciaD;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "anulado")
    private String anulado;

    @Column(name = "sistema")
    private String sistema;

    @Column(name = "bus")
    private String bus;

    @Column(name = "pagado")
    private String pagado;

    @Column(name = "fechacancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(name = "usuario1")
    private String usuario1;

    @Column(name = "terminal1")
    private String terminal1;

    @Column(name = "agencia1")
    private String agencia1;

    @Column(name = "agenciad1")
    private String agenciaD1;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombreapellido")
    private String nombreApellido;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "horareg")
    private String horaReg;

    @Column(name = "desechado")
    private String desechado;

    @Column(name = "otro")
    private String otro;

    @Column(name = "estado")
    private String estado;

    @Column(name = "domicilio")
    private BigDecimal domicilio;

    @Column(name = "b_identity")
    private Integer bIdentity;

    @Column(name = "salidar")
    private BigDecimal salidaR;

    @Column(name = "agenciar")
    private String agenciaR;

    @Column(name = "agenciadr")
    private String agenciaDR;

    @Column(name = "destinor")
    private BigDecimal destinoR;

    @Column(name = "destinodr")
    private String destinoDR;

    @Column(name = "codigosecreto")
    private String codigoSecreto;

    @Column(name = "nrodoc", length = 255, columnDefinition = "varchar DEFAULT ''")
    private String nroDoc;

    @Column(name = "cc_mitsui")
    private Integer ccMitsui;

    @Column(name = "tipoprecio")
    private String tipoPrecio;

    @Column(name = "codproducto")
    private String codProducto;

    @Column(name = "guiasclientes")
    private String guiasClientes;

    @Column(name = "cantcajas")
    private Integer cantCajas;

    @Column(name = "encoestado")
    private String encoEstado;

    @Column(name = "um1")
    private String UM1;

    @Column(name = "um2")
    private String UM2;

    @Column(name = "um3 ")
    private String UM3;

    @Column(name = "um")
    private String UM;

    @Column(name = "ruc_consignado")
    private String ruc_consignado;

    @Column(name = "dni_consignado")
    private String dni_consignado;

    // Continúa de manera similar con el resto de columnas...
}
