package com.alo.digital.facturacion.service;

import com.alo.digital.facturacion.entity.B_Encomiendas;
import com.alo.digital.facturacion.entity.B_ProgramacionSalida;
import com.alo.digital.facturacion.entity.CierreManifiesto;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface GeneratorGRTMasiveService {

    public void init(Integer idManifiesto);

    public void generarGRT(B_Encomiendas bEncomienda, CierreManifiesto cierreManifiesto);

    

}
