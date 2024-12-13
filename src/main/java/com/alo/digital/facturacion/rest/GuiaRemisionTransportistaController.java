package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.ManifiestoDto;
import com.alo.digital.facturacion.dto.V_AgenciasDto;
import com.alo.digital.facturacion.dto.V_GuiaDto;
import com.alo.digital.facturacion.repository.CierreManifiestoRepository;
import com.alo.digital.facturacion.repository.V_AgenciasRepository;
import com.alo.digital.facturacion.repository.V_GuiaRepository;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.utilitario.Utilitario;
import com.alo.digital.facturacion.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "*")
public class GuiaRemisionTransportistaController {

    @Autowired
    V_GuiaRepository vGuiaRepository;
    @Autowired
    Utilitario util;

    @GetMapping("/guiabymanifiesto")
    public ResponseEntity<Response<Page<V_GuiaDto>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size,
                                                            @RequestParam("nro") int nro) {
        Pageable pageable = PageRequest.of(page, size);
        Page<V_GuiaDto> data = null;//vGuiaRepository.findGuiasByNroManifiesto(pageable, nro);
        Response<Page<V_GuiaDto>> resultado =
                new UtilGenerico<Page<V_GuiaDto>>()
                        .crearMensaje
                                (
                                data,
                                200,
                                "listado Correcto",
                                "Consulta realizada con éxito"
                        );
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
    }

}
