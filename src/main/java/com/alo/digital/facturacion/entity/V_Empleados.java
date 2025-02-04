package com.alo.digital.facturacion.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "V_Empleados")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V_Empleados {

    @Id
    private String codigo; // char(4)

    private String detalle; // char(30)
    private String tipo; // char(1)
    private String brevete; // char(15)
    private LocalDateTime brevetef; // datetime
    private BigDecimal saldoinicial; // numeric(12, 2)
    private BigDecimal recorrido; // numeric(12, 2)
    private BigDecimal transbordo; // numeric(12, 2)
    private BigDecimal descanso; // numeric(12, 2)
    private String empresa; // char(3)
    private String empresad; // char(50)
    private String direccion; // char(30)
    private String telefono; // char(20)
    private String dni; // char(8)
    private LocalDateTime fechanaci; // datetime
    private String edad; // char(2)
    private String certificado; // char(15)
    private LocalDateTime certificadof; // datetime
    private String contrato; // char(15)
    private LocalDateTime contratof; // datetime
    private BigDecimal sueldo; // numeric(10, 2)
    private BigDecimal sueldoh; // numeric(10, 2)
    private BigDecimal sueldopdt; // numeric(10, 2)
    private BigDecimal asigfami; // numeric(10, 2)
    private BigDecimal bono; // numeric(10, 2)
    private String seguro; // char(2)
    private String segurod; // char(30)
    private BigDecimal vida; // numeric(10, 2)
    private BigDecimal invita; // numeric(10, 2)
    private String estudios; // char(30)
    private String grado; // char(30)
    private String carga; // char(30)
    private String estado; // char(30)
    private String documento; // char(30)
    private String antecedentes; // char(30)
    private String certificados; // char(30)
    private String otros; // char(30)
    private String experiencia; // char(100)
    private String entrevistadora; // char(30)
    private String calificacion; // char(30)
    private String comentario; // char(60)
    private Integer grupo; // int
    private Integer descansero; // int
    private LocalDateTime fechai; // datetime
    private String fechacflag; // char(1)
    private LocalDateTime fechac; // datetime

    private String nombres;
    private String apellidos;
}
