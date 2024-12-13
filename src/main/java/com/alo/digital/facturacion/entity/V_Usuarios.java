package com.alo.digital.facturacion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "V_Usuarios")
public class V_Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String codusuario;
    @Column
    String usuario;
    @Column
    String password;
    @Column
    String nomusuario;
    @Column
    String nivel;
    @Column
    String idagencia;
    @Column
    int activo;

}