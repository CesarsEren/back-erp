package com.alo.digital.facturacion.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "v_empresas")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class V_Empresas {

    @Id
    @Column(name = "codigo", nullable = false, length = 1)
    private String codigo;

    @Column(name = "ruc", length = 1)
    private String ruc;

    @Column(name = "razon", length = 1)
    private String razon;

    @Column(name = "direccion", length = 1)
    private String direccion;

    @Column(name = "telefono", length = 1)
    private String telefono;

    @Column(name = "ticketautoriza", length = 1)
    private String ticketAutoriza;

    @Column(name = "correo", length = 1)
    private String correo;

    @Column(name = "mtc_usuario", length = 1)
    private String mtcUsuario;

    @Column(name = "mtc_password", length = 1)
    private String mtcPassword;

    @Column(name = "mtc_partida", length = 1)
    private String mtcPartida;

    @Column(name = "liquidar", length = 1)
    private String liquidar;

    @Column(name = "ubigeo", length = 1)
    private String ubigeo;

    @Column(name = "codigopais")
    private String codigoPais;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "distrito")
    private String distrito;

    @Column(name = "urbanización")
    private String urbanizacion;

    @Column(name = "codigoestablecimientoanexo", length = 1)
    private String codigoEstablecimientoAnexo;
}
