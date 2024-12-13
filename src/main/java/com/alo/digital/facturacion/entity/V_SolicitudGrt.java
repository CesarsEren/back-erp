package com.alo.digital.facturacion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "V_solicitudgrt")
public class V_SolicitudGrt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura el autoincremento
    Long id;
    @Column(name = "idmanifiesto",unique = true )
    Integer idManifiesto;
    @Column(name = "generado")
    Integer generado; // 0 sin generar , 1 por generar , 2 generado
    @Column(name = "fecha_solicitud")
    LocalDateTime fecha_solicitud;

}