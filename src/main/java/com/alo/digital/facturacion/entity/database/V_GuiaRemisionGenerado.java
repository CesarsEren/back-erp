package com.alo.digital.facturacion.entity.database;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "v_guiaremisiongenerado")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class V_GuiaRemisionGenerado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String nroguia;
    //String numeroticket;
    String resultadopresentacion;
    String codigorespuesta;
    String descripcionrespuesta;
    byte[] constanciarecepcion;
    String descripcionexcepcion;
    String descripcionmensaje;
    byte[] documentofirmado;
    String codigoexcepcion;
    byte[] representacionimpresa;
    LocalDate fecharecepcion;
    String valorresumen;
    String codigobarras;
    String estadoproceso;
    String firmadigital;
    String codigomensaje;
    String observaciones;

    //    boolean ultimarespuesta;
    V_GuiaRemisionGenerado(Integer id, String nroguia, byte[] representacionimpresa) {
        this.id = id;
        this.nroguia = nroguia;
        this.representacionimpresa = representacionimpresa;
    }

}
