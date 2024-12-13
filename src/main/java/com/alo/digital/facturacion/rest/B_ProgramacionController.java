package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.B_ProgramacionSalidaDto;
import com.alo.digital.facturacion.dto.ConductorDto;
import com.alo.digital.facturacion.entity.B_ProgramacionSalida;
import com.alo.digital.facturacion.entity.CierreManifiesto;
import com.alo.digital.facturacion.entity.V_Agencias;
import com.alo.digital.facturacion.entity.V_Bus;
import com.alo.digital.facturacion.repository.B_ProgramacionSalidaRepository;
import com.alo.digital.facturacion.repository.CierreManifiestoRepository;
import com.alo.digital.facturacion.repository.V_AgenciasRepository;
import com.alo.digital.facturacion.repository.V_BusRepository;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.utilitario.Utilitario;
import com.alo.digital.facturacion.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class B_ProgramacionController {

    @Autowired
    V_BusRepository vBusRepository;

    @Autowired
    B_ProgramacionSalidaRepository bProgramacionSalidaRepository;

    @Autowired
    CierreManifiestoRepository cierreManifiestoRepository;

    @Autowired
    V_AgenciasRepository vAgenciasRepository;
    @Autowired
    Utilitario util;

    @GetMapping("buses")
    public List<V_Bus> findAll() {
        return vBusRepository.findAll();
    }

    @GetMapping("programacion")
    public ResponseEntity<Response<B_ProgramacionSalidaDto>> findConductoresByNroProgramacion(@RequestParam("ID") int ID) {
        B_ProgramacionSalidaDto bProgramacionSalidaDto = new B_ProgramacionSalidaDto();
        Optional<B_ProgramacionSalida> bProgramacionSalida = bProgramacionSalidaRepository.findBProgramacionSalidaByCierreManifiestoID(ID);
        Optional<CierreManifiesto> cierreManifiesto = cierreManifiestoRepository.findById(ID);

        if (bProgramacionSalida.isPresent() && cierreManifiesto.isPresent()) {

            B_ProgramacionSalida bps = bProgramacionSalida.get();
            bProgramacionSalidaDto.setNro(bps.getNro() + "");
            bProgramacionSalidaDto.setDestinod(bps.getDestinoD().trim());

            Optional<V_Bus> vBus = vBusRepository.findById(bps.getBus());
            if (vBus.isPresent()) {
                bProgramacionSalidaDto.setNroBus(vBus.get().getCodigo().trim());
                bProgramacionSalidaDto.setPlacaBus(vBus.get().getPlaca().trim());
                bProgramacionSalidaDto.setMarcaBus(vBus.get().getMarca().trim());
            }
            Optional<V_Agencias> vAgencia = vAgenciasRepository.findById(cierreManifiesto.get().getAgencia());

            //  bProgramacionSalidaDto.setAgenciaOrigen(cierreManifiesto.get().getAgencia().trim() + "-" + cierreManifiesto.get().getAgenciaD().trim());
            if (vAgencia.isPresent()) {
                bProgramacionSalidaDto.setAgenciaOrigen(vAgencia.get().getCodigo().trim().concat("-").concat(vAgencia.get().getDetalle().trim()));
                bProgramacionSalidaDto.setDireccionAgenciaOrigen(vAgencia.get().getDireccion().trim());
            }

            List<ConductorDto> conductores = new ArrayList<>();
            conductores.add(new ConductorDto(bps.getPilotoD().trim(), bps.getPilotoB().trim()));
            conductores.add(new ConductorDto(bps.getCoPilotoD().trim(), bps.getCoPilotoB().trim()));
            conductores.add(new ConductorDto(bps.getCoPilotoD2().trim(), bps.getCoPilotoB2().trim()));
            bProgramacionSalidaDto.setConductores(conductores);

            Response<B_ProgramacionSalidaDto> resultado =
                    new UtilGenerico<B_ProgramacionSalidaDto>()
                            .crearMensaje(bProgramacionSalidaDto, 200, "listado Correcto", "Consulta realizada con éxito");
            return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
        } else {
            Response<B_ProgramacionSalidaDto> resultado =
                    new UtilGenerico<B_ProgramacionSalidaDto>()
                            .crearMensaje(null, 404, "listado Correcto", "Consulta realizada con éxito");
            return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
        }
    }

}
