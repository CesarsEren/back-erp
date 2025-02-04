package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.SolicitudGrtIndividualDto;
import com.alo.digital.facturacion.entity.B_Encomiendas;
import com.alo.digital.facturacion.entity.CierreManifiesto;
import com.alo.digital.facturacion.entity.V_SolicitudGrt;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import com.alo.digital.facturacion.repository.B_EncomiendasRepository;
import com.alo.digital.facturacion.repository.CierreManifiestoRepository;
import com.alo.digital.facturacion.repository.V_GuiaRemisionTransportistaRepository;
import com.alo.digital.facturacion.service.GeneratorGRTMasiveService;
import com.alo.digital.facturacion.utilitario.Mensajes;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.vo.Error;
import com.alo.digital.facturacion.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*")
public class GeneracionGrtIndividualController {


    @Autowired
    GeneratorGRTMasiveService generatorGRTMasiveService;
    @Autowired
    V_GuiaRemisionTransportistaRepository vGuiaRemisionTransportistaRepository;
    @Autowired
    B_EncomiendasRepository bEncomiendasRepository;
    @Autowired
    CierreManifiestoRepository cierreManifiestoRepository;

    @PostMapping("/generar-grt-individual")
    @ResponseBody
    public ResponseEntity<?> solicitudGeneracionGrt(@RequestBody SolicitudGrtIndividualDto solicitudGrtDto) {
        Optional<V_GuiaRemisionTransportista> vGuiaRemisionTransportista = vGuiaRemisionTransportistaRepository.findById(solicitudGrtDto.getIdGuiaRemisionTransportista());
        if (!vGuiaRemisionTransportista.isPresent()) {
            com.alo.digital.facturacion.vo.Error error = new com.alo.digital.facturacion.vo.Error("id_manifiesto", Mensajes.MSG_CLIENTE_LOGIN_ERROR);
            Response<com.alo.digital.facturacion.vo.Error> response = new UtilGenerico<Error>().crearMensaje(error, 404, "No se encontró la GRT Solicitada",
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Optional<B_Encomiendas> bEncomiendas = bEncomiendasRepository.findById(new BigDecimal(vGuiaRemisionTransportista.get().getNroencomienda()));
        if (!bEncomiendas.isPresent()) {
            com.alo.digital.facturacion.vo.Error error = new com.alo.digital.facturacion.vo.Error("id_manifiesto", Mensajes.MSG_CLIENTE_LOGIN_ERROR);
            Response<com.alo.digital.facturacion.vo.Error> response = new UtilGenerico<Error>().crearMensaje(error, 404, "No se encontró la Encomienda Relacionada",
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Optional<CierreManifiesto> cierreManifiesto = cierreManifiestoRepository.findById(solicitudGrtDto.getIdManifiesto());
        if (!cierreManifiesto.isPresent()) {
            com.alo.digital.facturacion.vo.Error error = new com.alo.digital.facturacion.vo.Error("id_manifiesto", Mensajes.MSG_CLIENTE_LOGIN_ERROR);
            Response<com.alo.digital.facturacion.vo.Error> response = new UtilGenerico<Error>().crearMensaje(error, 404, "No se encontró el Nro de Manifiesto",
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            generatorGRTMasiveService.generarGRT(bEncomiendas.get(), cierreManifiesto.get());
            Response<?> response = new UtilGenerico<V_SolicitudGrt>().crearMensaje(null, 201, "Comprobante generado con éxito",
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            com.alo.digital.facturacion.vo.Error error = new com.alo.digital.facturacion.vo.Error("id_manifiesto", Mensajes.MSG_CLIENTE_LOGIN_ERROR);
            Response<com.alo.digital.facturacion.vo.Error> response = new UtilGenerico<Error>().crearMensaje(error, 500, e.getLocalizedMessage(),
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
