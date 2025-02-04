package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.Jwt;
import com.alo.digital.facturacion.dto.ManifiestoDto;
import com.alo.digital.facturacion.dto.V_AgenciasDto;
import com.alo.digital.facturacion.entity.V_Agencias;
import com.alo.digital.facturacion.repository.CierreManifiestoRepository;
import com.alo.digital.facturacion.repository.V_AgenciasRepository;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.utilitario.Utilitario;
import com.alo.digital.facturacion.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(value = "*")
public class AgenciaController {

    @Autowired
    V_AgenciasRepository vAgenciasRepository;

    @Autowired
    Utilitario util;

    @GetMapping("/agencias")
    public ResponseEntity<Response<List<V_AgenciasDto>>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("User: " + authentication.getName());
        } else {
            log.info("No user is authenticated");
        }

        List<V_AgenciasDto> data = new ArrayList<>();
        vAgenciasRepository.findAll()
                .forEach(dt -> {
                    data.add(new V_AgenciasDto(dt.getCodigo().trim(), dt.getDetalle().trim()));
                });
        Response<List<V_AgenciasDto>> resultado =
                new UtilGenerico<List<V_AgenciasDto>>()
                        .crearMensaje(data, 200, "listado Correcto", "Consulta realizada con éxito");
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
    }
//2888
}
