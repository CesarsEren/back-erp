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

    @Column(name = "destinod", length = 1)
    private String destinoD;

    @Column(name = "fechaviaje")
    private LocalDateTime fechaViaje;

    @Column(name = "horaviaje", length = 1)
    private String horaViaje;

    @Column(name = "horaviajeini", length = 1)
    private String horaViajeIni;

    @Column(name = "fechaemision")
    private LocalDateTime fechaEmision;

    @Column(name = "empresa", length = 1)
    private String empresa;

    @Column(name = "empresad", length = 1)
    private String empresaD;

    @Column(name = "documento", length = 1)
    private String documento;

    @Column(name = "documentod", length = 1)
    private String documentoD;

    @Column(name = "serie", length = 1)
    private String serie;

    @Column(name = "numero", length = 1)
    private String numero;

    @Column(name = "destino1", length = 1)
    private String destino1;

    @Column(name = "destino1d", length = 1)
    private String destino1D;

    @Column(name = "otraoficina", length = 1)
    private String otraOficina;

    @Column(name = "transaccion", length = 1)
    private String transaccion;

    @Column(name = "pago", length = 1)
    private String pago;

    @Column(name = "valija", length = 1)
    private String valija;

    @Column(name = "ruc", length = 1)
    private String ruc;

    @Column(name = "dnigiro", length = 1)
    private String dniGiro;

    @Column(name = "razon", length = 1)
    private String razon;

    @Column(name = "remitente", length = 1)
    private String remitente;

    @Column(name = "remitentd", length = 1)
    private String remitenteD;

    @Column(name = "consignado", length = 1)
    private String consignado;

    @Column(name = "consignadod", length = 1)
    private String consignadoD;

    @Column(name = "cantidad1", length = 1)
    private String cantidad1;

    @Column(name = "descripcion1", length = 1)
    private String descripcion1;

    @Column(name = "cantidad2", length = 1)
    private String cantidad2;

    @Column(name = "descripcion2", length = 1)
    private String descripcion2;

    @Column(name = "cantidad3", length = 1)
    private String cantidad3;

    @Column(name = "descripcion3", length = 1)
    private String descripcion3;

    @Column(name = "montogiro")
    private BigDecimal montoGiro;

    @Column(name = "kilos")
    private BigDecimal kilos;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "usuario", length = 1)
    private String usuario;

    @Column(name = "terminal", length = 1)
    private String terminal;

    @Column(name = "agencia", length = 1)
    private String agencia;

    @Column(name = "agenciad", length = 1)
    private String agenciaD;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "anulado", length = 1)
    private String anulado;

    @Column(name = "sistema", length = 1)
    private String sistema;

    @Column(name = "bus", length = 1)
    private String bus;

    @Column(name = "pagado", length = 1)
    private String pagado;

    @Column(name = "fechacancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(name = "usuario1", length = 1)
    private String usuario1;

    @Column(name = "terminal1", length = 1)
    private String terminal1;

    @Column(name = "agencia1", length = 1)
    private String agencia1;

    @Column(name = "agenciad1", length = 1)
    private String agenciaD1;

    @Column(name = "dni", length = 1)
    private String dni;

    @Column(name = "nombreapellido", length = 1)
    private String nombreApellido;

    @Column(name = "observacion", length = 1)
    private String observacion;

    @Column(name = "horareg", length = 1)
    private String horaReg;

    @Column(name = "desechado", length = 1)
    private String desechado;

    @Column(name = "otro", length = 1)
    private String otro;

    @Column(name = "estado", length = 1)
    private String estado;

    @Column(name = "domicilio")
    private BigDecimal domicilio;

    @Column(name = "b_identity")
    private Integer bIdentity;

    @Column(name = "salidar")
    private BigDecimal salidaR;

    @Column(name = "agenciar", length = 1)
    private String agenciaR;

    @Column(name = "agenciadr", length = 1)
    private String agenciaDR;

    @Column(name = "destinor")
    private BigDecimal destinoR;

    @Column(name = "destinodr", length = 1)
    private String destinoDR;

    @Column(name = "codigosecreto", length = 1)
    private String codigoSecreto;

    @Column(name = "nrodoc", length = 255, columnDefinition = "varchar DEFAULT ''")
    private String nroDoc;

    @Column(name = "cc_mitsui")
    private Integer ccMitsui;

    @Column(name = "tipoprecio", length = 1)
    private String tipoPrecio;

    @Column(name = "codproducto", length = 1)
    private String codProducto;

    @Column(name = "guiasclientes")
    private String guiasClientes;

    @Column(name = "cantcajas")
    private Integer cantCajas;

    @Column(name = "encoestado")
    private String encoEstado;

    // Continúa de manera similar con el resto de columnas...
}
