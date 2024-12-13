package com.alo.digital.facturacion.entity.grt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "v_grr_documento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VgrrDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer documentoid;

    private String codigotipodocumento;
    private String numeroserie;
    private String numerocorrelativo;
    private LocalDateTime fechaemision;
    private LocalTime horaemision;
    private String observaciones;
    private Integer remitenteid;
    private Integer destinatarioid;
    private Integer enviosid;
    private Integer emisorid;

    // Getters and Setters
}
