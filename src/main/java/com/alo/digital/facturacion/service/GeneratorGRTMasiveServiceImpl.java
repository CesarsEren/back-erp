package com.alo.digital.facturacion.service;

import com.alo.digital.facturacion.entity.*;
import com.alo.digital.facturacion.entity.grt.*;
import com.alo.digital.facturacion.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class GeneratorGRTMasiveServiceImpl implements GeneratorGRTMasiveService {

    @Autowired
    B_EncomiendasRepository bEncomiendasRepository;
    @Autowired
    CierreManifiestoRepository cierreManifiestoRepository;
    @Autowired
    B_ProgramacionSalidaRepository bProgramacionSalidaRepository;

    @Autowired
    V_BusRepository vBusRepository;

    @Autowired
    V_EmpresasRepository vEmpresasRepository;

    @Override
    public void init(Integer idManifiesto) {
//        bEncomiendasRepository.findEncomiendasByNroManifiesto()
        Optional<CierreManifiesto> cierreManifiesto = cierreManifiestoRepository.findById(idManifiesto);
        List<B_Encomiendas> lsEncomiendas = bEncomiendasRepository.findEncomiendasByNroManifiesto(idManifiesto);
        if (cierreManifiesto.isPresent() && !lsEncomiendas.isEmpty()) {
            lsEncomiendas.forEach(
                    bEncomiendas -> this.generarGRT(bEncomiendas, cierreManifiesto.get())
            );
        }
    }

    @Override
    public void generarGRT(B_Encomiendas bEncomienda, CierreManifiesto cierreManifiesto) {
        Optional<B_ProgramacionSalida> bProgramacionSalida = bProgramacionSalidaRepository.findBProgramacionSalidaByCierreManifiestoID(cierreManifiesto.getID());
        Optional<V_Bus> vBus = vBusRepository.findById(bProgramacionSalida.get().getBus().trim());
        String documentoDestinatario = bEncomienda.getRuc().isEmpty() ? bEncomienda.getDniGiro().trim() : bEncomienda.getRuc().trim();
        String codigoDestinatario = documentoDestinatario.length() == 11 ? "6" : "1";

        VgrrDestinatario destinatario =
                VgrrDestinatario.builder()
                        .codigotipoidentificacion(codigoDestinatario)
                        .numerodocumentoidentificacion(documentoDestinatario)
                        .apellidosnombresdenominacionrazonsocial(bEncomienda.getConsignado())
                        .numeroautorizacion(null)
                        .codigoentidadautorizadora(null)
                        .numeroregistromtc(null)
                        .build();

        String documentoRemitente = "";
        String codigoRemitente = "";

        VgrrRemitente remitente =
                VgrrRemitente.builder()
                        .codigotipoidentificacion(codigoRemitente)
                        .numerodocumentoidentificacion(documentoRemitente)//agregar campo
                        .apellidosnombresdenominacionrazonsocial(bEncomienda.getRemitente().trim())
                        .codigoentidadautorizadora(null)
                        .numeroregistromtc(null)
                        .numeroregistromtc(null)
                        .build();

        Optional<V_Empresas> vEmpresas = vEmpresasRepository.findById(bProgramacionSalida.get().getEmpresa());
        VgrrEmisor emisor =
                VgrrEmisor.builder()
                        .codigotipodocumentoidentidad("6")
                        .numerodocumentoidentidad(vEmpresas.get().getRuc())
                        .apellidosnombresdenominacionrazonsocial(vEmpresas.get().getRazon())
                        .nombrecomercial(null)
                        .codigopais("PE")
                        .codigoubicaciongeografica(vEmpresas.get().getUbigeo())
                        .codigoestablecimientoanexo(null)
                        .departamento(vEmpresas.get().getDepartamento())
                        .provincia(vEmpresas.get().getProvincia())
                        .distrito(vEmpresas.get().getDistrito())
                        .urbanizacion(vEmpresas.get().getUrbanizacion())
                        .direccion(vEmpresas.get().getDireccion())
                        .direccionEstablecimientoAnexo(null)
                        .build();

        VgrrVehiculoPrincipal vgrrVehiculoPrincipal =
                VgrrVehiculoPrincipal.builder()
                        .numeroplaca(vBus.get().getPlaca().trim())
                        .tarjetaunicacertificado(null)
                        .numeroautorizacion(null)
                        .codigoentidadautorizadora(null)
                        .build();


        Integer nroBultos =
                Integer.parseInt(bEncomienda.getCantidad1()) +
                        Integer.parseInt(bEncomienda.getCantidad2()) +
                        Integer.parseInt(bEncomienda.getCantidad3()); /*+
                        Integer.parseInt(bEncomienda.getCantidad4());*/

        VgrrEnvio envio =
                VgrrEnvio.builder()
                        .identificador("1")
                        .codigomotivo("04")
                        .descripcionmotivo("Traslado entre establecimientos de la misma empresa")
                        .sustentodiferenciapeso(null)
                        .pesobrutoitemsseleccionados(null)
                        .unidadmedidaitemsseleccionados(null)
                        .pesobruto(bEncomienda.getKilos())
                        .unidadmedida(bEncomienda.getum)
                        .numerobultos(nroBultos)
                        .build();


        ZonedDateTime limaTime = ZonedDateTime.now(ZoneId.of("America/Lima"));

        VgrrTramo tramo =
                VgrrTramo.builder()
                        .numerocorrelativo(null)
                        .modalidad("02")
                        .fechaentregabieniniciotraslado(limaTime.toLocalDateTime())
                        .build();


    }
}
