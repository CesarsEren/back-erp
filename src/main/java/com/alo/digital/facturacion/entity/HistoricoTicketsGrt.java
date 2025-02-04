package com.alo.digital.facturacion.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_tickets_grt")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistoricoTicketsGrt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grt_nro", nullable = false)
    private Integer grtNro;

    @Column(name = "nro_ticket", nullable = false, length = 100)
    private String nroTicket;

    @Column(name = "usuario", nullable = false, length = 100)
    private String usuario;

    @Column(name = "fecha_modificacion", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @Column(name = "respuesta_sunat", columnDefinition = "VARCHAR(MAX)")
    private String respuestaSunat;

}
