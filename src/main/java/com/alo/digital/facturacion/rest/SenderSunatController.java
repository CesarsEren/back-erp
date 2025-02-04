package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.*;
import com.alo.digital.facturacion.entity.HistoricoTicketsGrt;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionGenerado;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import com.alo.digital.facturacion.entity.database.colas.SisColaGrt;
import com.alo.digital.facturacion.entity.rest.response.ResponseGeneraXML;
import com.alo.digital.facturacion.enumeration.ColaGrtState;
import com.alo.digital.facturacion.enumeration.EnviadoState;
import com.alo.digital.facturacion.repository.*;
import com.alo.digital.facturacion.request.RqSolicitudBinario;
import com.alo.digital.facturacion.request.RqSolicitudEnvioGrts;
import com.alo.digital.facturacion.service.IConsumeRestApiSunat;
import com.alo.digital.facturacion.utilitario.AuthUtil;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.utilitario.Utilitario;
import com.alo.digital.facturacion.vo.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("sunat")
@Slf4j
public class SenderSunatController {


    @Autowired
    V_GuiaRemisionTransportistaRepository vGuiaRemisionTransportistaRepository;

    @Autowired
    V_GuiaRemisionGeneradoRepository vGuiaRemisionGeneradoRepository;

    @Autowired
    IConsumeRestApiSunat iConsumeRestApiSunat;
    @Autowired
    Utilitario util;

    @Autowired
    SisColaGrtRepository sisColaGrtRepository;

    @Autowired
    HistoricoTicketsGrtRepository historicoTicketsGrtRepository;


    @GetMapping("/state/grts")
    public ResponseEntity<Response<Page<GuiaRemisionSendSunatDto>>> findByDateAndState(@RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "5") int size,
                                                                                       @RequestParam("fecha") String fecha,
                                                                                       @RequestParam("estado") Integer estado) {

        Pageable pageable = PageRequest.of(page, size);
        Page<V_GuiaRemisionTransportista> vGuiaRemisionTransportistas = vGuiaRemisionTransportistaRepository.findByFechaemisionAndEnviado(pageable, LocalDate.parse(fecha), estado);
        List<GuiaRemisionSendSunatDto> result =
                vGuiaRemisionTransportistas.getContent()
                        .stream()
                        .map(data -> {
                                    return GuiaRemisionSendSunatDto
                                            .builder()
                                            .sunatState(data.getEnviado())
                                            .sunatStateD(EnviadoState.fromCode(data.getEnviado()).getDescription())
                                            .nroGuia(data.getNro())
                                            .nroEncomienda(data.getNroencomienda())
                                            .xmlGenerado(data.getGenerado())
                                            .serieNumeroGuia(data.getNumeroserie().concat("-").concat(data.getCodigotipodocumento()).concat("-").concat(data.getNumerocorrelativo()))
                                            .respuestaGeneracion(data.getRespuestageneracion())
                                            .ticket(data.getNumeroticket())
                                            .respuestaSunat(data.getRespuestasunat())
                                            .build();
                                }
                        ).collect(Collectors.toList());

        Response<Page<GuiaRemisionSendSunatDto>> resultado =
                new UtilGenerico<Page<GuiaRemisionSendSunatDto>>()
                        .crearMensaje(new PageImpl<>(result, pageable, vGuiaRemisionTransportistas.getTotalElements()), 200, "listado Correcto", "Consulta realizada con éxito");
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
    }

    @PostMapping("/send/grt")
    public ResponseEntity<Response<?>> sendSunat(@RequestBody RqSolicitudBinario rqSolicitudBinario) {

        Optional<V_GuiaRemisionTransportista> vGuiaRemisionTransportista = vGuiaRemisionTransportistaRepository.findById(Integer.parseInt(rqSolicitudBinario.getNroGuia()));
        Optional<V_GuiaRemisionGenerado> vGuiaRemisionGenerado = vGuiaRemisionGeneradoRepository.findByNroguia(rqSolicitudBinario.getNroGuia());
        if (!vGuiaRemisionGenerado.isPresent() || !vGuiaRemisionTransportista.isPresent()) {
            Response<String> resultado =
                    new UtilGenerico<String>()
                            .crearMensaje("", 404, "Guia no encontrada", "No se encontró Guia");
            return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
        }
        //if(vGuiaRemisionGenerado.get())
        ResponseGeneraXML responseGeneraXML = null;
        try {
            responseGeneraXML = iConsumeRestApiSunat.envioSunat(vGuiaRemisionGenerado.get().getDocumentofirmado());
        } catch (Exception e) {
            Response<String> resultado =
                    new UtilGenerico<String>()
                            .crearMensaje("", 500, "Error en el proceso de obtención de Ticket", e.getMessage());
            return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
        }
        vGuiaRemisionTransportista.get().setEnviado(EnviadoState.EN_PROCESO.getCode());
        vGuiaRemisionTransportista.get().setNumeroticket(responseGeneraXML.getNumeroTicket());
        vGuiaRemisionTransportistaRepository.save(vGuiaRemisionTransportista.get()); //sve Numero ticket

        SisColaGrt sisColaGrt = new SisColaGrt();
        sisColaGrt.setEstado(EnviadoState.POR_ENVIAR.getCode());
        sisColaGrt.setIntento(0);
        sisColaGrt.setNroTicket(responseGeneraXML.getNumeroTicket());
        sisColaGrt.setNroGuia(Integer.parseInt(rqSolicitudBinario.getNroGuia()));
        sisColaGrtRepository.save(sisColaGrt);

        vGuiaRemisionGenerado.get().setFecharecepcion(LocalDate.parse(responseGeneraXML.getFechaRecepcion()));
        vGuiaRemisionGeneradoRepository.save(vGuiaRemisionGenerado.get());

        Response<String> resultado =
                new UtilGenerico<String>()
                        .crearMensaje("", 200, "Grt Enviado Correctamente", "Comprobante enviado correctamente");
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
    }

    boolean todosEnviados = true;

    @PostMapping("/send/grt/all")
    public ResponseEntity<Response<?>> sendSunatAllGrts(@RequestBody RqSolicitudEnvioGrts rqSolicitudEnvioGrts) {
        todosEnviados = true;
        List<V_GuiaRemisionTransportista> vGuiaRemisionTransportistas = vGuiaRemisionTransportistaRepository.findByFechaemisionAndEnviado(LocalDate.parse(rqSolicitudEnvioGrts.getFechaemision()), EnviadoState.POR_ENVIAR.getCode());

        vGuiaRemisionTransportistas.forEach(vGuiaRemisionTransportista -> {
            Optional<V_GuiaRemisionGenerado> vGuiaRemisionGenerado = vGuiaRemisionGeneradoRepository.findByNroguia(vGuiaRemisionTransportista.getNro() + "");
            if (vGuiaRemisionGenerado.isPresent()) {
                //log.info("No se encontró el xml Generado de Nro: " + vGuiaRemisionTransportista.getNro());
                ResponseGeneraXML responseGeneraXML = null;
                try {
                    responseGeneraXML = iConsumeRestApiSunat.envioSunat(vGuiaRemisionGenerado.get().getDocumentofirmado());

                    if (responseGeneraXML.getNumeroTicket() == null) {
                        todosEnviados = false;
                        vGuiaRemisionTransportista.setEnviado(EnviadoState.EN_PROCESO.getCode());
                        vGuiaRemisionTransportista.setRespuestasunat(responseGeneraXML.getDescripcionExcepcion());
                        vGuiaRemisionTransportistaRepository.save(vGuiaRemisionTransportista); //sve Numero ticket
                    } else {
                        vGuiaRemisionTransportista.setEnviado(EnviadoState.EN_PROCESO.getCode());
                        vGuiaRemisionTransportista.setNumeroticket(responseGeneraXML.getNumeroTicket());
                        vGuiaRemisionTransportistaRepository.save(vGuiaRemisionTransportista); //sve Numero ticket

                        Optional<SisColaGrt> sisColaGrt = sisColaGrtRepository.findByNroGuia(vGuiaRemisionTransportista.getNro());
                        if (sisColaGrt.isPresent()) {
                            sisColaGrt.get().setEstado(ColaGrtState.POR_CONSULTAR.getCode());
                            sisColaGrt.get().setIntento(0);
                            sisColaGrt.get().setNroTicket(responseGeneraXML.getNumeroTicket());
                            sisColaGrt.get().setNroGuia(vGuiaRemisionTransportista.getNro());
                            sisColaGrtRepository.save(sisColaGrt.get());
                        } else {
                            SisColaGrt colaGrt = new SisColaGrt();
                            colaGrt.setEstado(ColaGrtState.POR_CONSULTAR.getCode());
                            colaGrt.setIntento(0);
                            colaGrt.setNroTicket(responseGeneraXML.getNumeroTicket());
                            colaGrt.setNroGuia(vGuiaRemisionTransportista.getNro());
                            sisColaGrtRepository.save(colaGrt);
                        }
                        vGuiaRemisionGenerado.get().setFecharecepcion(LocalDate.parse(responseGeneraXML.getFechaRecepcion()));
                        vGuiaRemisionGeneradoRepository.save(vGuiaRemisionGenerado.get());
                    }

                } catch (Exception e) {
                    log.error("Error encontrado en el procesamiento de GuiaRemisión : " + vGuiaRemisionTransportista.getNro());
                }

            } else {
                log.warn("No se encontró GRT GENERADO DE NroGuia :{}", vGuiaRemisionTransportista.getNro());
            }
        });
        if (todosEnviados) {
            Response<String> resultado =
                    new UtilGenerico<String>()
                            .crearMensaje("", 200, "Comprobantes enviados, en espera de respuesta", "Comprobantes enviados, en espera de respuesta");
            return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
        } else {
            Response<String> resultado =
                    new UtilGenerico<String>()
                            .crearMensaje("", 200, "Algunos comprobantes no obtuvieron NroTicket , reintentalos más tarde", "Comprobantes enviados, en espera de respuesta");
            return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
        }


    }

    @PostMapping("/state/grt/back")
    public ResponseEntity<Response<?>> changeStateOfErrorToPorEnviar(@RequestBody RqSolicitudEnvioGrts rqSolicitudEnvioGrts) {

        List<V_GuiaRemisionTransportista> vGuiaRemisionTransportistas = vGuiaRemisionTransportistaRepository.findByFechaemisionAndEnviado(LocalDate.parse(rqSolicitudEnvioGrts.getFechaemision()), EnviadoState.EXCEPTION.getCode());

        vGuiaRemisionTransportistas.forEach(vGuiaRemisionTransportista -> {
            HistoricoTicketsGrt historicoTicketsGrt = new HistoricoTicketsGrt();
            historicoTicketsGrt.setGrtNro(vGuiaRemisionTransportista.getNro());
            historicoTicketsGrt.setNroTicket(vGuiaRemisionTransportista.getNumeroticket());
            historicoTicketsGrt.setRespuestaSunat(vGuiaRemisionTransportista.getRespuestasunat());
            historicoTicketsGrt.setUsuario(AuthUtil.obtenerUsuarioActual());
            historicoTicketsGrtRepository.save(historicoTicketsGrt);
            vGuiaRemisionTransportista.setNumeroticket(null);
            vGuiaRemisionTransportista.setEnviado(EnviadoState.POR_ENVIAR.getCode());
            vGuiaRemisionTransportista.setRespuestasunat(null);
            vGuiaRemisionTransportistaRepository.save(vGuiaRemisionTransportista);
        });

        Response<String> resultado =
                new UtilGenerico<String>()
                        .crearMensaje("", 200, "Comprobantes están listo para ser reenviados", "Comprobantes están listo para ser reenviados");
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));

    }
}