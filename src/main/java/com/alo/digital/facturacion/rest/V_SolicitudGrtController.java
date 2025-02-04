package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.SolicitudGrtDto;
import com.alo.digital.facturacion.dto.SolicitudGrtIndividualDto;
import com.alo.digital.facturacion.entity.B_Encomiendas;
import com.alo.digital.facturacion.entity.CierreManifiesto;
import com.alo.digital.facturacion.entity.V_SolicitudGrt;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import com.alo.digital.facturacion.enumeration.SolicitudGrtState;
import com.alo.digital.facturacion.repository.B_EncomiendasRepository;
import com.alo.digital.facturacion.repository.CierreManifiestoRepository;
import com.alo.digital.facturacion.repository.V_GuiaRemisionTransportistaRepository;
import com.alo.digital.facturacion.repository.V_SolicitudGrtRepository;
import com.alo.digital.facturacion.service.GeneratorGRTMasiveService;
import com.alo.digital.facturacion.utilitario.Mensajes;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.vo.Error;
import com.alo.digital.facturacion.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*")
public class V_SolicitudGrtController {

    @Autowired
    V_SolicitudGrtRepository vSolicitudGrtRepository;

    @PostMapping("/solicitudgrt")
    @ResponseBody
    public ResponseEntity<?> solicitudGeneracionGrt(@RequestBody SolicitudGrtDto solicitudGrtDto) {
        Optional<V_SolicitudGrt> vSolicitudGrt = vSolicitudGrtRepository.findByidManifiesto(solicitudGrtDto.getIdManifiesto());
        if (!vSolicitudGrt.isPresent()) {
            V_SolicitudGrt vS = new V_SolicitudGrt();
            vS.setIdManifiesto(solicitudGrtDto.getIdManifiesto());
            vS.setGenerado(SolicitudGrtState.EN_PROCESO.getCode());

            // Crear LocalDateTime en el momento actual
            LocalDateTime localDateTime = LocalDateTime.now();

            // Asignar la zona horaria de Lima
            ZoneId zoneLima = ZoneId.of("America/Lima");
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneLima);

            vS.setFecha_solicitud(zonedDateTime.toLocalDateTime());
            vSolicitudGrtRepository.save(vS);
            Response<?> response = new UtilGenerico<V_SolicitudGrt>().crearMensaje(vS, 201, "La solicitud de GRT se generó correctamente, Recargue la búsqueda",
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            Error error = new Error("id_manifiesto", Mensajes.MSG_CLIENTE_LOGIN_ERROR);
            Response<Error> response = new UtilGenerico<Error>().crearMensaje(error, 422, "Solicitud de manifiesto Error",
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }



}
