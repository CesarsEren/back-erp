package com.alo.digital.facturacion.entity.database;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "v_guiaremisiontransportista")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class V_GuiaRemisionTransportista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Integer nro;
    Integer nroencomienda;
    Integer nrocierremanifiesto;
    String codigotipodocumento;
    String numeroserie;
    String numerocorrelativo;
    LocalDate fechaemision;
    LocalTime horaemision;
    String observaciones;
    String oguiabaja;
    String ldocumentoreferencia;
    //V_Remitente oRemitente;
    //V_Destinatario oDestinatario;
    String oproveedor;
    String ocomprador;
    String oterceropagaservicio;
    //V_Envio oEnvio;
    //V_Emisor oEmisor;
    //List<V_GrtDetalle> lDetalleGuia;
    String parametrosadicionalesreporte;
    Boolean felectronico;
    Boolean generado;
    Integer enviado;
    //Boolean aceptado;
    String respuestasunat;
    String respuestageneracion;
    String numeroticket;
    String trama_json;
    //String urlPdf;
    //String urlXml;
}
