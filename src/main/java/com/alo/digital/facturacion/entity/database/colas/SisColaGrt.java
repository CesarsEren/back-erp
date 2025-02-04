package com.alo.digital.facturacion.entity.database.colas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sis_cola_grt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SisColaGrt {

    @Id
    @Column(name = "nro_guia", nullable = false)
    private int nroGuia;

    @Column(name = "nro_ticket", nullable = false, length = 255)
    private String nroTicket;

    @Column(name = "estado", nullable = false)
    private int estado;

    @Column(name = "intento", nullable = false)
    private int intento;

}
