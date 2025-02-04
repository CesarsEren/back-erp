package com.alo.digital.facturacion.entity.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Envio {
    String identificador;
    String codigoMotivo;
    String descripcionMotivo;
    String sustentoDiferenciaPeso;
    String pesoBrutoItemsSeleccionados;
    String unidadMedidaItemsSeleccionados;
    Double pesoBruto;
    String unidadMedida;
    Integer numeroBultos;
    Tramo[] lTramo;
    String[] lContenedor; //contenedor class
    String[] lIndicadores; // Indicadores class
    Vehiculo oVehiculoPrincipal;
    String[] lVehiculoSecundario; // array vehiculos
    String codigoPuertoAeropuerto;
    String tipoLocacion;
    String nombrePuertoAeropuerto;
    Direccion oDireccionPuntoPartida;
    Direccion oDireccionPuntoLlegada;
    String oEmpresaSubcontrata;

}
