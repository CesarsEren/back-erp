package com.alo.digital.facturacion.service;

import com.alo.digital.facturacion.entity.rest.GuiaRemisionTransportista;
import com.alo.digital.facturacion.entity.rest.response.ResponseGeneraXML;

import java.io.IOException;

public interface IConsumeRestApiSunat {

    public ResponseGeneraXML generateXML(GuiaRemisionTransportista guiaRemisionTransportista) throws IOException;

    //public void envioSunat(byte[] documento);
    public ResponseGeneraXML envioSunat(byte[] documento) throws Exception;


    public ResponseGeneraXML consultarCdrByTicket(String json_trama,String nroTicket) throws Exception;

}
