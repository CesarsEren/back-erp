package com.alo.digital.facturacion.entity.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Entity
//@Table(name = "v_guiaremisionrespuesta")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class V_GuiaRemisionRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String nroGuia;

}
