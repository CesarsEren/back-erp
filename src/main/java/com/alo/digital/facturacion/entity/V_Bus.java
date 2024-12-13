package com.alo.digital.facturacion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "V_Bus")
public class V_Bus {

    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "placa")
    private String placa;

    @Column(name = "propietario")
    private String propietario;

    @Column(name = "terramozo")
    private String terramozo;

    @Column(name = "terramozod")
    private String terramozoD;

    @Column(name = "terramozodni")
    private String terramozoDni;

    @Column(name = "marca")
    private String marca;

    @Column(name = "asientos")
    private String asientos;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "serviciod")
    private String servicioD;

    @Column(name = "certificadoh")
    private String certificadoH;

    @Column(name = "chofer")
    private String chofer;

    @Column(name = "choferd")
    private String choferD;

    @Column(name = "choferb")
    private String choferB;

    @Column(name = "copiloto")
    private String copiloto;

    @Column(name = "copilotod")
    private String copilotoD;

    @Column(name = "copilotob")
    private String copilotoB;

    @Column(name = "copiloto2")
    private String copiloto2;

    @Column(name = "copilotod2")
    private String copilotoD2;

    @Column(name = "copilotob2")
    private String copilotoB2;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "empresad")
    private String empresaD;

    @Column(name = "boletoruta")
    private BigDecimal boletoRuta;

    @Column(name = "debaja")
    private String deBaja;

    @Column(name = "pantalla")
    private String pantalla;

    @Column(name = "configuracionvehicular")
    private String configuracionVehicular;

    @Column(name = "constancia")
    private String constancia;

    @Column(name = "horadescanso")
    private String horaDescanso;

    @Column(name = "vigenciah")
    private LocalDateTime vigenciaH;

    @Column(name = "certgen")
    private String certGen;

    @Column(name = "certcon")
    private String certCon;

    @Column(name = "vigenciac")
    private LocalDateTime vigenciaC;

    @Column(name = "certope")
    private String certOpe;

    @Column(name = "vigenciao")
    private LocalDateTime vigenciaO;

    @Column(name = "asientosfisicos")
    private String asientosFisicos;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "ano")
    private String ano;

    @Column(name = "ejes")
    private String ejes;

    @Column(name = "rueda")
    private String rueda;

    @Column(name = "motor")
    private String motor;

    @Column(name = "asientostotal")
    private String asientosTotal;

    @Column(name = "serie")
    private String serie;

    @Column(name = "botiquin")
    private String botiquin;

    @Column(name = "triangulo")
    private String triangulo;

    @Column(name = "llanta")
    private String llanta;

    @Column(name = "extinguidor")
    private String extinguidor;

    @Column(name = "fechaextinguidor")
    private LocalDateTime fechaExtinguidor;

    @Column(name = "asientosoat")
    private String asientoSoat;

    @Column(name = "gravame")
    private String gravame;

    @Column(name = "gravamed")
    private String gravameD;

    @Column(name = "polizasoat")
    private String polizaSoat;

    @Column(name = "vigenciasoat")
    private LocalDateTime vigenciaSoat;

    @Column(name = "polizamaterialsoat")
    private String polizaMaterialSoat;

    @Column(name = "vigenciasoatmate")
    private LocalDateTime vigenciaSoatMate;

    @Column(name = "montoasegurado")
    private BigDecimal montoAsegurado;

    @Column(name = "pesobruto")
    private BigDecimal pesoBruto;

    @Column(name = "pesoseco")
    private BigDecimal pesoSeco;

    @Column(name = "pesoutil")
    private BigDecimal pesoUtil;

    @Column(name = "asientotripu")
    private String asientoTripu;

    @Column(name = "hrutanumero")
    private String hRutaNumero;

    @Column(name = "hcrutanumero")
    private String hcRutaNumero;

    @Column(name = "movilasignado")
    private String movilAsignado;

    @Column(name = "tracking")
    private BigDecimal tracking;

    @Column(name = "situacion")
    private String situacion;

    @Column(name = "situaciond")
    private String situacionD;

    @Column(name = "ofertaruta")
    private BigDecimal ofertaRuta;

    @Column(name = "destino")
    private String destino;

    @Column(name = "destinod")
    private String destinoD;

    @Column(name = "certifinscripcion")
    private String certifInscripcion;

    @Column(name = "soatvenc")
    private LocalDateTime soatVenc;

    @Column(name = "soatbroker")
    private String soatBroker;

    @Column(name = "soatbrokerd")
    private String soatBrokerD;

    @Column(name = "soataseguradora")
    private String soatAseguradora;

    @Column(name = "soataseguradorad")
    private String soatAseguradoraD;

    @Column(name = "modalidad")
    private String modalidad;

    @Column(name = "modalidadd")
    private String modalidadD;

    @Column(name = "modalidadvenc")
    private LocalDateTime modalidadVenc;

    @Column(name = "gravamen")
    private String gravamen;

    @Column(name = "gravamend")
    private String gravamenD;

    @Column(name = "gravamenvenc")
    private LocalDateTime gravamenVenc;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "seguropoliza")
    private String seguroPoliza;

    @Column(name = "segurobroker")
    private String seguroBroker;

    @Column(name = "segurobrokerd")
    private String seguroBrokerD;

    @Column(name = "segurovenc")
    private LocalDateTime seguroVenc;

    @Column(name = "seguroaseguradora")
    private String seguroAseguradora;

    @Column(name = "seguroaseguradorad")
    private String seguroAseguradoraD;

    @Column(name = "constanciafecha")
    private LocalDateTime constanciaFecha;

    @Column(name = "citv")
    private String citv;

    @Column(name = "fecvencitv")
    private LocalDateTime fecVenCITV;

    // Getters and Setters
}
