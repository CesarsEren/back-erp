package com.alo.digital.facturacion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "B_Programacionsalida")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class B_ProgramacionSalida {

    @Id
    @Column(name = "nro", nullable = false)
    private Integer nro;  // Assuming 'Nro' is unique and acts as a primary key

    @Column(name = "destino")
    private Integer destino;

    @Column(name = "destinod")
    private String destinoD;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "serviciod")
    private String servicioD;

    @Column(name = "agencia1")
    private String agencia1;

    @Column(name = "agencia1d")
    private String agencia1D;

    @Column(name = "hora1")
    private String hora1;

    @Column(name = "agencia2")
    private String agencia2;

    @Column(name = "agencia2d")
    private String agencia2D;

    @Column(name = "hora2")
    private String hora2;

    @Column(name = "agencia3")
    private String agencia3;

    @Column(name = "agencia3d")
    private String agencia3D;

    @Column(name = "hora3")
    private String hora3;

    @Column(name = "precio1")
    private Integer precio1;

    @Column(name = "precio2")
    private Integer precio2;

    @Column(name = "bus")
    private String bus;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "empresad")
    private String empresaD;

    @Column(name = "serie")
    private String serie;

    @Column(name = "correlativo")
    private String correlativo;

    @Column(name = "nroautorizacion")
    private String nroAutorizacion;

    @Column(name = "fechaimpresion")
    private LocalDateTime fechaImpresion;

    @Column(name = "desde")
    private Integer desde;

    @Column(name = "hasta")
    private Integer hasta;

    @Column(name = "pasajero")
    private Integer pasajero;

    @Column(name = "asientos")
    private Integer asientos;

    @Column(name = "pasajerovacio")
    private Integer pasajeroVacio;

    @Column(name = "comentario", length = 255)
    private String comentario;

    @Column(name = "observacion", length = 255)
    private String observacion;

    @Column(name = "terramoza")
    private String terramoza;

    @Column(name = "terramozad")
    private String terramozaD;

    @Column(name = "terramozadni")
    private String terramozaDni;

    @Column(name = "nrohojae")
    private Integer nroHojaE;

    @Column(name = "nrohojam1")
    private Integer nroHojaM1;

    @Column(name = "nrohojam2")
    private Integer nroHojaM2;

    @Column(name = "factura")
    private String factura;

    @Column(name = "piloto")
    private String piloto;

    @Column(name = "pilotod")
    private String pilotoD;

    @Column(name = "pilotob")
    private String pilotoB;

    @Column(name = "pilotopos")
    private String pilotoPos;

    @Column(name = "copiloto")
    private String coPiloto;

    @Column(name = "copilotod")
    private String coPilotoD;

    @Column(name = "copilotob")
    private String coPilotoB;

    @Column(name = "copilotopos")
    private String coPilotoPos;

    @Column(name = "copiloto2")
    private String coPiloto2;

    @Column(name = "copilotod2")
    private String coPilotoD2;

    @Column(name = "copilotob2")
    private String coPilotoB2;

    @Column(name = "estadochofer")
    private String estadoChofer;

    @Column(name = "estadocopiloto")
    private String estadoCopiloto;

    @Column(name = "hora4")
    private String hora4;

    @Column(name = "agencia4")
    private String agencia4;

    @Column(name = "agencia4d")
    private String agencia4D;

    @Column(name = "planillachofer")
    private String planillaChofer;

    @Column(name = "agencia5")
    private String agencia5;

    @Column(name = "agencia5d")
    private String agencia5D;

    @Column(name = "hora5")
    private String hora5;

    @Column(name = "agencia6")
    private String agencia6;

    @Column(name = "agencia6d")
    private String agencia6D;

    @Column(name = "hora6")
    private String hora6;

    @Column(name = "agencia7")
    private String agencia7;

    @Column(name = "agencia7d")
    private String agencia7D;

    @Column(name = "hora7")
    private String hora7;

    @Column(name = "agencia8")
    private String agencia8;

    @Column(name = "agencia8d")
    private String agencia8D;

    @Column(name = "hora8")
    private String hora8;

    @Column(name = "hrutanumero")
    private String hRutaNumero;

    @Column(name = "hcrutanumero")
    private String hcRutaNumero;

    @Column(name = "grupoitinerario")
    private Integer grupoItinerario;

    @Column(name = "otro")
    private String otro;

    @Column(name = "lboletost")
    private Integer lBoletosT;

    @Column(name = "lboletosc")
    private Integer lBoletosC;

    @Column(name = "lboletosp")
    private Integer lBoletosP;

    @Column(name = "lboletose")
    private Integer lBoletosE;

    @Column(name = "lencot")
    private Integer lEncoT;

    @Column(name = "lencoc")
    private Integer lEncoC;

    @Column(name = "lencop")
    private Integer lEncoP;

    @Column(name = "lencoe")
    private Integer lEncoE;

    @Column(name = "lsubtotal")
    private Integer lSubTotal;

    @Column(name = "lvales")
    private Integer lVales;

    @Column(name = "ltotales")
    private Integer lTotales;

    @Column(name = "lusuario")
    private String lUsuario;

    @Column(name = "lpiloto")
    private String lPiloto;

    @Column(name = "lpilotod")
    private String lPilotoD;

    @Column(name = "lboletero")
    private String lBoletero;

    @Column(name = "lboleterod")
    private String lBoleteroD;

    @Column(name = "lhcr")
    private Integer lHCR;

    @Column(name = "lpeajes")
    private Integer lPeajes;

    @Column(name = "ldetraccion")
    private Integer lDetraccion;

    @Column(name = "lrampa")
    private Integer lRampa;

    @Column(name = "lbonos")
    private Integer lBonos;

    @Column(name = "ltipoviaje")
    private String lTipoViaje;

    @Column(name = "lfechaliquidacion")
    private LocalDateTime lFechaLiquidacion;

    @Column(name = "lpax")
    private Integer lPax;

    @Column(name = "flgliquidado")
    private String flgLiquidado;

    @Column(name = "lhoraf")
    private String lHoraF;

    @Column(name = "lagencia")
    private String lAgencia;

    @Column(name = "lagenciad")
    private String lAgenciaD;

    @Column(name = "referencia", length = 255)
    private String referencia;

    @Column(name = "tdp")
    private Boolean tdp;

    @Column(name = "pasajes")
    private Boolean pasajes;

    @Column(name = "carga")
    private Boolean carga;

    @Column(name = "viajeespecial")
    private Boolean viajeEspecial;

    @Column(name = "tercero")
    private Boolean tercero;
}
