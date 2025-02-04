package com.alo.digital.facturacion.service.impl;

import com.alo.digital.facturacion.entity.database.V_GuiaRemisionGenerado;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import com.alo.digital.facturacion.entity.database.colas.SisColaGrt;
import com.alo.digital.facturacion.entity.rest.response.ResponseGeneraXML;
import com.alo.digital.facturacion.enumeration.ColaGrtState;
import com.alo.digital.facturacion.enumeration.EnviadoState;
import com.alo.digital.facturacion.repository.SisColaGrtRepository;
import com.alo.digital.facturacion.repository.V_GuiaRemisionGeneradoRepository;
import com.alo.digital.facturacion.repository.V_GuiaRemisionTransportistaRepository;
import com.alo.digital.facturacion.service.IConsumeRestApiSunat;
import com.alo.digital.facturacion.service.IConsumerApiToGetCdr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
public class ConsumerApiToGetCdrImpl implements IConsumerApiToGetCdr {

    @Autowired
    V_GuiaRemisionTransportistaRepository vGuiaRemisionTransportistaRepository;

    @Autowired
    V_GuiaRemisionGeneradoRepository vGuiaRemisionGeneradoRepository;

    @Autowired
    IConsumeRestApiSunat iConsumeRestApiSunat;

    @Autowired
    SisColaGrtRepository sisColaGrtRepository;

    @Override
    public void init(SisColaGrt sisColaGrt) {

        Optional<V_GuiaRemisionTransportista> vGuiaRemisionTransportista = vGuiaRemisionTransportistaRepository.findById(sisColaGrt.getNroGuia());
        Optional<V_GuiaRemisionGenerado> vGuiaRemisionGenerado = vGuiaRemisionGeneradoRepository.findByNroguia(sisColaGrt.getNroGuia() + "");

        if (vGuiaRemisionTransportista.isPresent() && vGuiaRemisionGenerado.isPresent()) {

            try {
                ResponseGeneraXML responseGeneraXML = consultarState(vGuiaRemisionTransportista.get(), sisColaGrt.getNroTicket());
                if (responseGeneraXML != null) {

                    if (responseGeneraXML.getCodigoRespuesta() != null && responseGeneraXML.getCodigoRespuesta().equals("0") && responseGeneraXML.getCodigoExcepcion() == null) { //ACEPTADO

                        vGuiaRemisionTransportista.get().setEnviado(EnviadoState.ACEPTADO.getCode());
                        vGuiaRemisionGenerado.get().setDescripcionmensaje(responseGeneraXML.getDescripcionMensaje());
                        vGuiaRemisionGenerado.get().setDescripcionrespuesta(responseGeneraXML.getDescripcionRespuesta());
                        byte[] constanciaRecepcion = Base64.getDecoder().decode(responseGeneraXML.getConstanciaRecepcion());
                        vGuiaRemisionGenerado.get().setConstanciarecepcion(constanciaRecepcion);
                        vGuiaRemisionGenerado.get().setCodigobarras(responseGeneraXML.getCodigoBarras());
                        if (responseGeneraXML.getObservaciones() != null) {
                            vGuiaRemisionGenerado.get().setObservaciones(responseGeneraXML.getObservaciones().toString());
                        }
                        vGuiaRemisionGenerado.get().setEstadoproceso(responseGeneraXML.getEstadoProceso());
                        vGuiaRemisionGenerado.get().setDescripcionexcepcion("");
                        vGuiaRemisionGenerado.get().setResultadopresentacion(responseGeneraXML.getResultadoPresentacion());
                        vGuiaRemisionGenerado.get().setCodigorespuesta(responseGeneraXML.getCodigoRespuesta());
                        vGuiaRemisionGeneradoRepository.save(vGuiaRemisionGenerado.get());
                        vGuiaRemisionTransportista.get().setRespuestasunat(responseGeneraXML.getDescripcionRespuesta());
                        vGuiaRemisionTransportistaRepository.save(vGuiaRemisionTransportista.get());

                        sisColaGrt.setEstado(ColaGrtState.ACEPTADO.getCode());

                    }
                    if (responseGeneraXML.getCodigoExcepcion() != null) {
                        vGuiaRemisionTransportista.get().setEnviado(EnviadoState.EXCEPTION.getCode());
                        vGuiaRemisionGenerado.get().setDescripcionexcepcion(responseGeneraXML.getDescripcionExcepcion());
                        vGuiaRemisionGenerado.get().setCodigorespuesta(responseGeneraXML.getCodigoRespuesta());
                        vGuiaRemisionGenerado.get().setDescripcionrespuesta(responseGeneraXML.getDescripcionRespuesta());
                        //vGuiaRemisionGenerado.get().setDescripcionmensaje(responseGeneraXML.getDescripcionExcepcion());
                        if (responseGeneraXML.getConstanciaRecepcion() != null) {
                            byte[] constanciaRecepcion = Base64.getDecoder().decode(responseGeneraXML.getConstanciaRecepcion());
                            vGuiaRemisionGenerado.get().setConstanciarecepcion(constanciaRecepcion);
                        }

                        vGuiaRemisionGeneradoRepository.save(vGuiaRemisionGenerado.get());
                        if (responseGeneraXML.getDescripcionRespuesta() == null) {
                            vGuiaRemisionTransportista.get().setRespuestasunat(responseGeneraXML.getDescripcionExcepcion());
                        } else {
                            vGuiaRemisionTransportista.get().setRespuestasunat(responseGeneraXML.getDescripcionRespuesta());
                        }
                        vGuiaRemisionTransportistaRepository.save(vGuiaRemisionTransportista.get());

                    }
                }

            } catch (Exception e) {
                log.error(e.getMessage());
                //throw new RuntimeException(e);
            } finally {
                sisColaGrtRepository.save(sisColaGrt);
            }

        }
    }

    @Override
    public ResponseGeneraXML consultarState(V_GuiaRemisionTransportista vGuiaRemisionTransportista, String nroTicket) {
        try {
            return iConsumeRestApiSunat.consultarCdrByTicket(vGuiaRemisionTransportista.getTrama_json(), nroTicket);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void changeStateByReintento(V_GuiaRemisionTransportista vGuiaRemisionTransportista, String nroTicket) {

        //  vGuiaRemisionTransportista.setRespuestasunat("La cantidad de reintentos para obtención de respuesta, fue superada");

    }

}
