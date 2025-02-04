package com.alo.digital.facturacion.service;

import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import com.alo.digital.facturacion.entity.database.colas.SisColaGrt;
import com.alo.digital.facturacion.entity.rest.response.ResponseGeneraXML;

public interface IConsumerApiToGetCdr {

    public void init(SisColaGrt sisColaGrt);

    public ResponseGeneraXML consultarState(V_GuiaRemisionTransportista vGuiaRemisionTransportista, String nroTicket);

    public void changeStateByReintento(V_GuiaRemisionTransportista vGuiaRemisionTransportista, String nroTicket);

}