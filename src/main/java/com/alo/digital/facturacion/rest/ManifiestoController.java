package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.ManifiestoDto;
import com.alo.digital.facturacion.entity.V_Usuarios;
import com.alo.digital.facturacion.repository.CierreManifiestoRepository;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.utilitario.Utilitario;
import com.alo.digital.facturacion.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(value = "*")
@Slf4j
public class ManifiestoController {

    @Autowired
    CierreManifiestoRepository cierreManifiestoRepository;

    @Autowired
    Utilitario util;

    @GetMapping("/manifiestos")
    public ResponseEntity<Response<Page<ManifiestoDto>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "20") int size,
                                                                @RequestParam(defaultValue = "001") String agencia,
                                                                @RequestParam("fecha") String fecha
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ManifiestoDto> data = cierreManifiestoRepository.findCierreManifiestoSummary(pageable, agencia, LocalDate.parse(fecha).atStartOfDay());
        Response<Page<ManifiestoDto>> resultado =
                new UtilGenerico<Page<ManifiestoDto>>()
                        .crearMensaje(data, 200, "listado Correcto", "Consulta realizada con éxito");
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
    }
}
