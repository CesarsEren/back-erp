package com.alo.digital.facturacion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "V_guiatransportista")
public class V_GuiaTransportista {

    Integer id;
    String serie;
    String numero;
    Integer nroB_Encomienda;
    String nroEncomienda;
    String serieEncomienda;
    LocalDate fechaemision;
    LocalTime horaemision;


}
