package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.GrtGeneradoDto;
import com.alo.digital.facturacion.dto.ManifiestoDto;
import com.alo.digital.facturacion.dto.V_GuiaDto;
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
import java.util.StringJoiner;

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

    @GetMapping("/manifiestos/guiagenerado")
    public ResponseEntity<Response<Page<GrtGeneradoDto>>> getGrtsGenerados(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "5") int size,
                                                                           @RequestParam("nrocierremanifiesto") int nroCierreManifiesto) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GrtGeneradoDto> data = cierreManifiestoRepository.findCierreManifiestoGuiasGeneradas(pageable, nroCierreManifiesto);
        data.stream().forEach(grtg -> {
            grtg.setSerieNumeroEncomienda(startsWith(grtg.getSerieNumeroEncomienda()));
            grtg.setLink_pdf("pdf/".concat(grtg.getNro() + ""));
            grtg.setLink_xml("zip/".concat(grtg.getNro() + ""));
        });
        Response<Page<GrtGeneradoDto>> resultado =
                new UtilGenerico<Page<GrtGeneradoDto>>()
                        .crearMensaje
                                (
                                        data,
                                        200,
                                        "listado Correcto",
                                        "Consulta realizada con éxito"
                                );
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
    }

    private String startsWith(String value) {
        String[] trivalue = value.split("-");
        StringJoiner stringJoiner = new StringJoiner("-");
        switch (trivalue[1]) {
            case "01":
                stringJoiner.add("F" + trivalue[0]);
                break;
            case "03":
                stringJoiner.add("B" + trivalue[0]);
                break;
            case "09":
                stringJoiner.add("T" + trivalue[0]);
                break;
        }

        stringJoiner.add(trivalue[1]);
        stringJoiner.add(trivalue[2]);
        return stringJoiner.toString();
    }

}
