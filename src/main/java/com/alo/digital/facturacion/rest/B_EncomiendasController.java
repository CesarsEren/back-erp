package com.alo.digital.facturacion.rest;


import com.alo.digital.facturacion.dto.B_EncomiendasDto;
import com.alo.digital.facturacion.entity.B_Encomiendas;
import com.alo.digital.facturacion.entity.B_ProgramacionSalida;
import com.alo.digital.facturacion.repository.B_EncomiendasRepository;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.utilitario.Utilitario;
import com.alo.digital.facturacion.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*")
public class B_EncomiendasController {

    @Autowired
    B_EncomiendasRepository bEncomiendasRepository;

    @Autowired
    Utilitario util;

    @GetMapping("encomiendas")
    public ResponseEntity<Response<Page<B_EncomiendasDto>>> findBEncomiendasByManifiestoID(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = "5") int size,
                                                                                           @RequestParam("ID") int ID) {
        Pageable pageable = PageRequest.of(page, size);
        Page<B_Encomiendas> bEncomiendas = bEncomiendasRepository.findEncomiendasByNroManifiesto(pageable, ID);
        List<B_EncomiendasDto> result =
                bEncomiendas.getContent()
                        .stream()
                        .map(data -> {
                                    B_EncomiendasDto bEncomiendasDto = new B_EncomiendasDto();
                                    bEncomiendasDto.setNro(data.getNro().toString());
                                    bEncomiendasDto.setCostototal(data.getTotalreal().toString());

                                    bEncomiendasDto.setPesototal(data.getKilos().toString());
                                    bEncomiendasDto.setSerienumero(data.getSerie().concat("-").concat(data.getNumero()));
                                    bEncomiendasDto.setNrobultos((
                                            Double.parseDouble(Optional.ofNullable(data.getCantidad1()).orElse("0")) +
                                                    Double.parseDouble(Optional.ofNullable(data.getCantidad2()).orElse("0")) +
                                                    Double.parseDouble(Optional.ofNullable(data.getCantidad3()).orElse("0")) +
                                                    Double.parseDouble(Optional.ofNullable(data.getCantidad4()).orElse("0"))) +
                                            ""
                                    );
                                    bEncomiendasDto.setRemitente(data.getRemitente().trim());
                                    bEncomiendasDto.setConsignado(data.getConsignado().trim());
                                    bEncomiendasDto.setDestinod(data.getDestino1d().trim());
                                    return bEncomiendasDto;
                                }
                        ).collect(Collectors.toList());

        Response<Page<B_EncomiendasDto>> resultado =
                new UtilGenerico<Page<B_EncomiendasDto>>()
                        .crearMensaje(new PageImpl<>(result, pageable, bEncomiendas.getTotalElements()), 200, "listado Correcto", "Consulta realizada con éxito");
        return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));


    }

}
