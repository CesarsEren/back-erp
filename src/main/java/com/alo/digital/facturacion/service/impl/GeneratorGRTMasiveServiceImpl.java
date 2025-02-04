package com.alo.digital.facturacion.service.impl;

import com.alo.digital.facturacion.entity.*;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionGenerado;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import com.alo.digital.facturacion.entity.grt.*;
import com.alo.digital.facturacion.entity.rest.*;
import com.alo.digital.facturacion.entity.rest.response.ResponseGeneraXML;
import com.alo.digital.facturacion.enumeration.EnviadoState;
import com.alo.digital.facturacion.repository.*;
import com.alo.digital.facturacion.service.GeneratorGRTMasiveService;
import com.alo.digital.facturacion.service.IConsumeRestApiSunat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
    V_AgenciasRepository vAgenciasRepository;
    @Autowired
    V_DestinosRepository vDestinosRepository;
    @Autowired
    V_BusRepository vBusRepository;

    @Autowired
    V_EmpresasRepository vEmpresasRepository;

    @Autowired
    V_EmpleadosRepository vEmpleadosRepository;

    @Autowired
    V_CorrelativosElectronicosRepository vCorrelativosElectronicosRepository;

    @Autowired
    V_GuiaRemisionTransportistaRepository vGuiaRemisionTransportistaRepository;

    @Autowired
    V_GuiaRemisionGeneradoRepository vGuiaRemisionGeneradoRepository;

    @Value("${app.empresa.correlativo.codigo}")
    String empresa;
    @Value("${app.empresa.correlativo.terminal}")
    String terminal;


    @Autowired
    IConsumeRestApiSunat iConsumeRestApiSunat;

    @Override
    public void init(Integer idManifiesto) {
//        bEncomiendasRepository.findEncomiendasByNroManifiesto()
        Optional<CierreManifiesto> cierreManifiesto = cierreManifiestoRepository.findById(idManifiesto);
        List<B_Encomiendas> lsEncomiendas = bEncomiendasRepository.findEncomiendasByNroManifiesto(idManifiesto);
        if (cierreManifiesto.isPresent() && !lsEncomiendas.isEmpty()) {
            lsEncomiendas.forEach(
                    bEncomiendas -> {
                        try {
                            this.generarGRT(bEncomiendas, cierreManifiesto.get());
                        } catch (Exception e) {
                            log.error("Error en el proceso de generación de " + bEncomiendas.getSerie().concat("-" + bEncomiendas.getDocumento()) + "-".concat(bEncomiendas.getNumero()) + " - " + e);
                            //throw new RuntimeException(e);
                        }
                    }
            );
            cierreManifiesto.get().setGrtsGenerados(Boolean.TRUE);//GRTS GENERADAS - ASÍ TENGAN ERROR.
            cierreManifiestoRepository.save(cierreManifiesto.get());
        }
    }

    ZonedDateTime limaTime = ZonedDateTime.now(ZoneId.of("America/Lima"));

    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void generarGRT(B_Encomiendas bEncomienda, CierreManifiesto cierreManifiesto) throws Exception {

        ResponseGeneraXML responseGeneraXML = new ResponseGeneraXML();
        V_GuiaRemisionTransportista vguia = new V_GuiaRemisionTransportista();

        GuiaRemisionTransportista guiaRemisionTransportista = new GuiaRemisionTransportista();
        Optional<V_GuiaRemisionTransportista> findguia = vGuiaRemisionTransportistaRepository.findByNroencomienda(bEncomienda.getNro().intValue());
        Optional<B_ProgramacionSalida> bProgramacionSalida = bProgramacionSalidaRepository.findBProgramacionSalidaByCierreManifiestoID(cierreManifiesto.getID());

        if (!bProgramacionSalida.isPresent()) {
            vguia.setRespuestageneracion("Programacion SALIDA NO ENCONTRADA");
        }

        Optional<V_Bus> vBus = vBusRepository.findById(bProgramacionSalida.get().getBus().trim());

        Optional<V_CorrelativosElectronicos> vCorrelativosElectronicos = vCorrelativosElectronicosRepository.findByEmpresaAndTerminal(empresa, terminal, "31");
        guiaRemisionTransportista.setCodigoTipoDocumento("31");

        if (findguia.isPresent()) {
            guiaRemisionTransportista.setNumeroSerie(findguia.get().getNumeroserie());//Traer de BCorrelativosElectronicos
            guiaRemisionTransportista.setNumeroCorrelativo(findguia.get().getNumerocorrelativo());

            if (findguia.get().getEnviado() != null) {
                if (findguia.get().getEnviado().equals(EnviadoState.ACEPTADO.getCode())) {
                    log.warn("Comprobante ya fue enviado y aceptado, no puede regenerarse");
                    throw new Exception("Comprobante ya fue enviado y aceptado, no puede regenerarse");
                    //return;
                }
            }

        } else if (!vCorrelativosElectronicos.isPresent()) {
            log.info("Correlativo no encontrado empresa = {},terminal = {} , tipo_documento ={}", empresa, terminal, "31");
            vguia.setRespuestageneracion("Correlativo no encontrado empresa = " + empresa + ",terminal = " + terminal + " , tipo_documento = 31");
        } else {//if (guiaRemisionTransportista.getNumeroSerie() == null && guiaRemisionTransportista.getNumeroCorrelativo() == null) {
            guiaRemisionTransportista.setNumeroSerie("V" + vCorrelativosElectronicos.get().getSerie());//Traer de BCorrelativosElectronicos
            guiaRemisionTransportista.setNumeroCorrelativo(String.format("%08d", vCorrelativosElectronicos.get().getCorrelativo().intValue()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

        LocalTime horaActual = LocalTime.now(ZoneId.of("America/Lima"));
        LocalDateTime fechaHoraCompleta = LocalDateTime.of(bProgramacionSalida.get().getFecha().toLocalDate(), horaActual);

        String fechaaa = fechaHoraCompleta.format(formatter);

        log.info("fechaaa {}", fechaaa);
        guiaRemisionTransportista.setFechaEmision(fechaaa+"-05:00");
        guiaRemisionTransportista.setHoraEmision(fechaaa+"-05:00");
        guiaRemisionTransportista.setObservaciones(null);
        guiaRemisionTransportista.setOGuiaBaja(null);
        //guiaRemisionTransportista.setLDocumentoReferencia(Arrays.asList(buildNombreDocumento(bEncomienda)));
        guiaRemisionTransportista.setLDocumentoReferencia(new ArrayList<>());
        guiaRemisionTransportista.setORemitente(buildRemitente(bEncomienda));
        guiaRemisionTransportista.setODestinatario(buildDestinatario(bEncomienda));
        guiaRemisionTransportista.setOProveedor(null);
        guiaRemisionTransportista.setOComprador(null);
        guiaRemisionTransportista.setOTerceroPagaServicio(null);
        try {
            if (!vBus.isPresent()) {
                vguia.setRespuestageneracion(" Nro de bus no encontrado");
            }
            guiaRemisionTransportista.setOEnvio(buildEnvio(bEncomienda, bProgramacionSalida.get(), vBus.get()));
            guiaRemisionTransportista.setOEmisor(buildEmisor(bProgramacionSalida.get()));
        } catch (Exception e) {
            log.error("{}", e);
            vguia.setRespuestageneracion(e.toString());
        }

        guiaRemisionTransportista.setLDetalleGuia(buildDetallesGuia(bEncomienda));
        guiaRemisionTransportista.setOTransportista(buildOtransportista(bEncomienda));

        Boolean generado = Boolean.TRUE;
        try {
            responseGeneraXML = iConsumeRestApiSunat.generateXML(guiaRemisionTransportista);
            if (responseGeneraXML == null) {
                responseGeneraXML = new ResponseGeneraXML();

            } else {
                responseGeneraXML.setDescripcionMensaje("XML Generado Correctamente");
            }
        } catch (Exception e) {
            log.error("mensaje de error en generación {}", e.toString());
            generado = Boolean.FALSE;
            assert responseGeneraXML != null;
            responseGeneraXML.setDescripcionMensaje(e.toString());
        }

        vguia = getRemisionTransportista(bEncomienda, guiaRemisionTransportista, responseGeneraXML, cierreManifiesto, generado, vCorrelativosElectronicos.get());
        vGuiaRemisionTransportistaRepository.save(vguia);

        if (generado) {
            V_GuiaRemisionGenerado vGuiaRemisionGenerado = getRemisionGenerado(responseGeneraXML, vguia);
            vGuiaRemisionGeneradoRepository.save(vGuiaRemisionGenerado);
        }

    }


    private Transportista buildOtransportista(B_Encomiendas bEncomienda) {

        Optional<V_Empresas> vEmpresas = vEmpresasRepository.findById(bEncomienda.getEmpresa());

        return Transportista.builder()
                .apellidosNombresDenominacionRazonSocial(vEmpresas.get().getRazon().trim())
                .codigoEntidadAutorizadora(null)
                .codigoTipoIdentificacion("6")
                .numeroAutorizacion("")
                .numeroDocumentoIdentificacion(vEmpresas.get().getRuc().trim())
                .numeroRegistroMTC(null)
                .build();
    }

    private V_GuiaRemisionTransportista getRemisionTransportista(B_Encomiendas bEncomiendas, GuiaRemisionTransportista guiaRemisionTransportista, ResponseGeneraXML responseGeneraXML, CierreManifiesto manifiesto, Boolean generado, V_CorrelativosElectronicos vCorrelativosElectronicos) {
        Optional<V_GuiaRemisionTransportista> vguia = vGuiaRemisionTransportistaRepository.findByNroencomienda(bEncomiendas.getNro().intValue());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String requestBody = gson.toJson(guiaRemisionTransportista);


        if (!vguia.isPresent()) {
            vguia = Optional.of(new V_GuiaRemisionTransportista());
        }
        log.info("2da trama {}",requestBody);
        vguia.get().setTrama_json(requestBody);
        vguia.get().setNroencomienda(bEncomiendas.getNro().intValue());
        vguia.get().setNrocierremanifiesto(manifiesto.getID());
        if (vguia.get().getRespuestageneracion() == null || vguia.get().getRespuestageneracion().isEmpty()) {
            vguia.get().setRespuestageneracion(responseGeneraXML.getDescripcionMensaje());
        }
        vguia.get().setCodigotipodocumento(guiaRemisionTransportista.getCodigoTipoDocumento());

        if (vguia.get().getNumeroserie() == null || vguia.get().getNumeroserie().isEmpty()) {
            vguia.get().setNumeroserie(guiaRemisionTransportista.getNumeroSerie());
            vguia.get().setNumerocorrelativo(String.format("%08d", Integer.parseInt(guiaRemisionTransportista.getNumeroCorrelativo())));
            actualizarCorrelativo(vCorrelativosElectronicos);
        }


        LocalDateTime localDateTime = LocalDateTime.parse(guiaRemisionTransportista.getFechaEmision().substring(0,guiaRemisionTransportista.getFechaEmision().length()-6).trim());
        LocalDate fEmision = localDateTime.toLocalDate();
        LocalDateTime localDateTime1 = LocalDateTime.parse(guiaRemisionTransportista.getHoraEmision().substring(0,guiaRemisionTransportista.getFechaEmision().length()-6).trim());
        LocalTime fHora = localDateTime1.toLocalTime();

        vguia.get().setFechaemision(fEmision);
        vguia.get().setHoraemision(fHora);


        vguia.get().setObservaciones(guiaRemisionTransportista.getObservaciones());
        vguia.get().setOguiabaja(null);
        if (!guiaRemisionTransportista.getLDocumentoReferencia().isEmpty()) {
            vguia.get().setLdocumentoreferencia(guiaRemisionTransportista.getLDocumentoReferencia().get(0).toString());
        }
        vguia.get().setOproveedor(null);
        vguia.get().setOcomprador(null);
        vguia.get().setOterceropagaservicio(null);
        vguia.get().setParametrosadicionalesreporte(guiaRemisionTransportista.getParametrosAdicionalesReporte());
        vguia.get().setFelectronico(Boolean.TRUE);
        vguia.get().setEnviado(EnviadoState.POR_ENVIAR.getCode());
        vguia.get().setGenerado(generado);
        vguia.get().setRespuestasunat(null);
        //vguia.get().setNumeroticket(responseGeneraXML.getNumeroTicket());
        return vguia.get();
    }

    private V_GuiaRemisionGenerado getRemisionGenerado(ResponseGeneraXML responseGeneraXML, V_GuiaRemisionTransportista vGuiaRemisionTransportista) {
        Optional<V_GuiaRemisionGenerado> findvGuiaRemisionGenerado = vGuiaRemisionGeneradoRepository.findByNroguia(vGuiaRemisionTransportista.getNro() + "");
        V_GuiaRemisionGenerado vGuiaRemisionGenerado = new V_GuiaRemisionGenerado();
        if (findvGuiaRemisionGenerado.isPresent()) {
            vGuiaRemisionGenerado = findvGuiaRemisionGenerado.get();
        }
        vGuiaRemisionGenerado.setNroguia(vGuiaRemisionTransportista.getNro() + "");
        //vGuiaRemisionGenerado.setNumeroticket(responseGeneraXML.getNumeroTicket());
        vGuiaRemisionGenerado.setResultadopresentacion(responseGeneraXML.getResultadoPresentacion());
        vGuiaRemisionGenerado.setCodigobarras(responseGeneraXML.getCodigoRespuesta());
        vGuiaRemisionGenerado.setDescripcionrespuesta(responseGeneraXML.getDescripcionRespuesta());
        //byte[] consrecepcion = Base64.getDecoder().decode(responseGeneraXML.getConstanciaRecepcion());
        vGuiaRemisionGenerado.setConstanciarecepcion(null);
        vGuiaRemisionGenerado.setDescripcionexcepcion(responseGeneraXML.getDescripcionExcepcion());
        vGuiaRemisionGenerado.setDescripcionmensaje(responseGeneraXML.getDescripcionMensaje());
        if (responseGeneraXML.getDocumentoFirmado() != null) {
            byte[] docuFirmado = Base64.getDecoder().decode(responseGeneraXML.getDocumentoFirmado());
            vGuiaRemisionGenerado.setDocumentofirmado(docuFirmado);
        }
        vGuiaRemisionGenerado.setCodigoexcepcion(responseGeneraXML.getCodigoExcepcion());
        if (responseGeneraXML.getRepresentacionImpresa() != null) {
            byte[] repreImpresa = Base64.getDecoder().decode(responseGeneraXML.getRepresentacionImpresa());
            vGuiaRemisionGenerado.setRepresentacionimpresa(repreImpresa);
        }
        vGuiaRemisionGenerado.setFecharecepcion(responseGeneraXML.getFechaRecepcion() != null ? LocalDate.parse(responseGeneraXML.getFechaRecepcion()) : null);
        vGuiaRemisionGenerado.setValorresumen(responseGeneraXML.getValorResumen());//CodeHash
        vGuiaRemisionGenerado.setCodigobarras(responseGeneraXML.getCodigoBarras());
        vGuiaRemisionGenerado.setEstadoproceso(responseGeneraXML.getEstadoProceso());
        vGuiaRemisionGenerado.setFirmadigital(responseGeneraXML.getFirmaDigital());
        vGuiaRemisionGenerado.setCodigomensaje(responseGeneraXML.getCodigoMensaje());
        if (responseGeneraXML.getObservaciones() != null) {
            vGuiaRemisionGenerado.setObservaciones(responseGeneraXML.getObservaciones().toString());
        } else {
            vGuiaRemisionGenerado.setObservaciones("");
        }
        return vGuiaRemisionGenerado;
    }

    private void actualizarCorrelativo(V_CorrelativosElectronicos vCorrelativosElectronicos) {
        vCorrelativosElectronicos.setCorrelativo(vCorrelativosElectronicos.getCorrelativo().add(BigDecimal.ONE));
        vCorrelativosElectronicosRepository.save(vCorrelativosElectronicos);
    }

    public Envio buildEnvio(B_Encomiendas bEncomienda, B_ProgramacionSalida bProgramacionSalida, V_Bus vBus) throws Exception {
        Integer nroBultos =
                Integer.parseInt(bEncomienda.getCantidad1().trim()) +
                        Integer.parseInt(bEncomienda.getCantidad2().trim()) +
                        Integer.parseInt(bEncomienda.getCantidad3().trim());
        Optional<V_Empresas> vEmpresas = vEmpresasRepository.findById(bProgramacionSalida.getEmpresa());
        Optional<V_Destinos> vDestinos = vDestinosRepository.findById(bProgramacionSalida.getDestino());

        if (!vEmpresas.isPresent()) {
            throw new Exception("Empresa no encontrada : " + bProgramacionSalida.getEmpresa());
        }
        if (!vDestinos.isPresent()) {
            throw new Exception("Destino no encontrado : " + bProgramacionSalida.getDestino());
        }
        Optional<V_Agencias> agenciaOrigen = vAgenciasRepository.findById(vDestinos.get().getOrigen());
        Optional<V_Agencias> agenciaDestino = vAgenciasRepository.findById(bEncomienda.getDestino1());
        if (!agenciaOrigen.isPresent()) {
            throw new Exception("Agencia de origen no encontrada");
        }

        if (!agenciaDestino.isPresent()) {
            throw new Exception("Agencia de destino no encontrada");
        }

        Optional<V_Empleados> chofer = vEmpleadosRepository.findById(bProgramacionSalida.getPiloto().trim());
        Optional<V_Empleados> copiloto = vEmpleadosRepository.findById(bProgramacionSalida.getCoPiloto().trim());
        if (!chofer.isPresent()) {
            throw new Exception("Chofer no encontrado :" + bProgramacionSalida.getPiloto());
        }

        Conductor conducCopiloto = null;
        if (copiloto.isPresent()) {

            conducCopiloto = Conductor.builder()
                    .codigoTipoIdentificacion("1")
                    .numeroDocumentoIdentificacion(copiloto.get().getDni().trim())
                    .nombres(copiloto.get().getNombres())
                    .apellidos(copiloto.get().getApellidos())
                    .numeroLicenciaConducir(copiloto.get().getBrevete().trim())
                    .build();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

        LocalTime horaActual = LocalTime.now(ZoneId.of("America/Lima"));
        LocalDateTime fechaHoraCompleta = LocalDateTime.of(bProgramacionSalida.getFecha().toLocalDate(), horaActual);

        return Envio.builder()
                .identificador("1")
                .codigoMotivo("04")
                .descripcionMotivo("Traslado entre establecimientos de la misma empresa")
                .sustentoDiferenciaPeso(null)
                .pesoBrutoItemsSeleccionados(null)
                .unidadMedidaItemsSeleccionados(null)
                .pesoBruto(Double.parseDouble(bEncomienda.getKilos() + ""))
                .unidadMedida(changeUm(bEncomienda.getUM().trim()))
                .numeroBultos(nroBultos)
                .lTramo(new Tramo[]{
                        Tramo.builder()
                                .numeroCorrelativo(null)
                                .modalidad("02")
                                .fechaEntregaBienInicioTraslado(fechaHoraCompleta.format(formatter)+"-05:00")
                                .tipoEvento(null)
                                .oTransportista(null)
                                .oConductorPrincipal(
                                        Conductor.builder()
                                                .codigoTipoIdentificacion("1")
                                                .numeroDocumentoIdentificacion(chofer.get().getDni().trim())
                                                .nombres(chofer.get().getNombres())
                                                .apellidos(chofer.get().getApellidos())
                                                .numeroLicenciaConducir(chofer.get().getBrevete().trim())
                                                .build()
                                )
                                .lConductorSecundario(new Conductor[]{conducCopiloto})
                                .build()
                })
                .lContenedor(null)
                .lIndicadores(null)
                .oVehiculoPrincipal(Vehiculo.builder()
                        .numeroPlaca(vBus.getPlaca().trim())
                        .tarjetaUnicaCertificado(null)
                        .numeroAutorizacion(null)
                        .codigoEntidadAutorizadora(null)
                        .build())
                .lVehiculoSecundario(null)
                .codigoPuertoAeropuerto(null)
                .tipoLocacion(null)
                .nombrePuertoAeropuerto(null)
                .oDireccionPuntoPartida(
                        Direccion.builder()
                                .ubigeo(agenciaOrigen.get().getUbigeo() != null ? agenciaOrigen.get().getUbigeo().trim() : null)
                                .descripcion(agenciaOrigen.get().getDireccion() != null ? agenciaOrigen.get().getDireccion().trim() : null)
                                .numeroRucAsociado(vEmpresas.get().getRuc().trim())
                                .codigoEstablecimiento(null)
                                .puntoGeorreferenciaLongitud(Double.parseDouble(agenciaOrigen.get().getLongitud() != null ? agenciaOrigen.get().getLongitud().trim() : "0"))
                                .puntoGeorreferenciaLatitud(Double.parseDouble(agenciaOrigen.get().getLatitud() != null ? agenciaOrigen.get().getLatitud().trim() : "0"))
                                .build())
                .oDireccionPuntoLlegada(
                        Direccion.builder()
                                .ubigeo(agenciaDestino.get().getUbigeo() != null ? agenciaDestino.get().getUbigeo().trim() : null)
                                .descripcion(agenciaDestino.get().getDireccion() != null ? agenciaDestino.get().getDireccion().trim() : null)
                                .numeroRucAsociado(vEmpresas.get().getRuc().trim())
                                .codigoEstablecimiento(null)
                                .puntoGeorreferenciaLongitud(Double.parseDouble(agenciaDestino.get().getLongitud() != null ? agenciaDestino.get().getLongitud().trim() : "0"))
                                .puntoGeorreferenciaLatitud(Double.parseDouble(agenciaDestino.get().getLatitud() != null ? agenciaDestino.get().getLatitud().trim() : "0"))
                                .build()
                )
                .oEmpresaSubcontrata(null)
                .build();
    }

    private String changeUm(String UM) {
        if (UM.trim().equals("KG")) return "KGM";
        if (UM.trim().equals("TN")) return "TNE";
        else return UM;
    }

    private List<GrtDetalle> buildDetallesGuia(B_Encomiendas bEncomienda) {
        List<GrtDetalle> grtDetalles = new ArrayList<>();

        int nroOrdenCount = 0;
        if (!bEncomienda.getCantidad1().trim().equals("0")) {
            grtDetalles.add(GrtDetalle
                    .builder()
                    .numeroOrden(++nroOrdenCount)
                    .cantidad(Double.parseDouble(bEncomienda.getCantidad1()))
                    .oProducto(Producto.builder()
                            .codigo(null)
                            .descripcion(bEncomienda.getDescripcion1().trim())
                            .codigoUnidadMedida(changeUm(bEncomienda.getUM1().trim()))
                            .codigoSunat(null)
                            .build())
                    .oGuia(null)
                    .lPropiedadItemAdicional(new String[]{})
                    .build());
        }

        if (!bEncomienda.getCantidad2().trim().equals("0")) {

            grtDetalles.add(GrtDetalle
                    .builder()
                    .numeroOrden(++nroOrdenCount)
                    .cantidad(Double.parseDouble(bEncomienda.getCantidad2()))
                    .oProducto(Producto.builder()
                            .codigo(null)
                            .descripcion(bEncomienda.getDescripcion2().trim())
                            .codigoUnidadMedida(changeUm(bEncomienda.getUM2().trim()))
                            .codigoSunat(null)
                            .build())
                    .oGuia(null)
                    .lPropiedadItemAdicional(new String[]{})
                    .build());

        }

        if (!bEncomienda.getCantidad3().trim().equals("0")) {

            grtDetalles.add(GrtDetalle
                    .builder()
                    .numeroOrden(++nroOrdenCount)
                    .cantidad(Double.parseDouble(bEncomienda.getCantidad3()))
                    .oProducto(Producto.builder()
                            .codigo(null)
                            .descripcion(bEncomienda.getDescripcion3().trim())
                            .codigoUnidadMedida(changeUm(bEncomienda.getUM3().trim()))
                            .codigoSunat(null)
                            .build())
                    .oGuia(null)
                    .lPropiedadItemAdicional(new String[]{})
                    .build());
        }
        return grtDetalles;
    }

    private Emisor buildEmisor(B_ProgramacionSalida bProgramacionSalida) throws Exception {


        Optional<V_Empresas> vEmpresas = vEmpresasRepository.findById(bProgramacionSalida.getEmpresa());

        if (!vEmpresas.isPresent()) {
            throw new Exception("Empresa no encontrada");
        }

        return Emisor.builder()
                .codigoTipoDocumentoIdentidad("6")
                .numeroDocumentoIdentidad(vEmpresas.get().getRuc().trim())
                .apellidosNombresDenominacionRazonSocial(vEmpresas.get().getRazon().trim())
                .nombreComercial(null)
                .codigoPais("PE")
                .codigoUbicacionGeografica(vEmpresas.get().getUbigeo().trim())
                .codigoEstablecimientoAnexo(null)
                .departamento(vEmpresas.get().getDepartamento().trim())
                .provincia(vEmpresas.get().getProvincia().trim())
                .distrito(vEmpresas.get().getDistrito().trim())
                .urbanizacion(vEmpresas.get().getUrbanizacion().trim())
                .direccion(vEmpresas.get().getDireccion().trim())
                .direccionEstablecimientoAnexo(null)
                .build();

    }

    private Destinatario buildDestinatario(B_Encomiendas bEncomienda) {

        String documentoDestinatario = bEncomienda.getRuc_consignado() == null || bEncomienda.getRuc_consignado().trim().isEmpty() ?
                bEncomienda.getDni_consignado() : bEncomienda.getRuc_consignado();
        String codigoDestinatario = documentoDestinatario.trim().length() == 11 ? "6" : "1";

        return Destinatario
                .builder()
                .codigoTipoIdentificacion(codigoDestinatario.trim())
                .numeroDocumentoIdentificacion(documentoDestinatario.trim())
                .apellidosNombresDenominacionRazonSocial(bEncomienda.getConsignado().trim())
                .numeroAutorizacion(null)
                .codigoEntidadAutorizadora(null)
                .numeroRegistroMTC(null)
                .build();
    }

    public DocumentoReferencia buildNombreDocumento(B_Encomiendas bEncomienda) {
        DocumentoReferencia res = new DocumentoReferencia();
        //Optional<V_Empresas> vEmpresas = vEmpresasRepository.findById(bEncomienda.getEmpresa().trim());
        String prefijo = "";
        switch (bEncomienda.getDocumento()) {
            case "01":
                prefijo = "F";
                break;
            case "03":
                prefijo = "B";
                break;
            case "09":
                prefijo = "T";
                break;
        }
        //return prefijo + bEncomienda.getSerie().trim() + "-".concat(bEncomienda.getDocumento().trim()) + "-".concat(bEncomienda.getNumero().trim());
        res.setCodigoTipoDocumento(bEncomienda.getDocumento().trim());
        res.setNumero(
                //vEmpresas.get().getRuc().trim().concat("-")
                //.concat(bEncomienda.getDocumento().trim()).concat("-")
                prefijo + bEncomienda.getSerie().trim().concat("-")
                        .concat(bEncomienda.getNumero().trim()));
        return res;
    }

    public Remitente buildRemitente(B_Encomiendas bEncomienda) {

        String documentoRemitente = bEncomienda.getRuc() == null || bEncomienda.getRuc().trim().isEmpty() ? bEncomienda.getDniGiro().trim() : bEncomienda.getRuc().trim();
        String codigoRemitente = documentoRemitente.trim().length() == 11 ? "6" : "1";

        return Remitente.builder()
                .codigoTipoIdentificacion(codigoRemitente)
                .numeroDocumentoIdentificacion(documentoRemitente.trim())//agregar campo
                .apellidosNombresDenominacionRazonSocial(bEncomienda.getRemitente().trim())
                .codigoEntidadAutorizadora(null)
                .numeroRegistroMTC(null)
                .build();
    }
/*
    public void generarGRT2(B_Encomiendas bEncomienda, CierreManifiesto cierreManifiesto) {
        Optional<B_ProgramacionSalida> bProgramacionSalida = bProgramacionSalidaRepository.findBProgramacionSalidaByCierreManifiestoID(cierreManifiesto.getID());
        Optional<V_Bus> vBus = vBusRepository.findById(bProgramacionSalida.get().getBus().trim());
        //String documentoDestinatario = bEncomienda.getRuc().isEmpty() ? bEncomienda.getDniGiro().trim() : bEncomienda.getRuc().trim();
        String documentoDestinatario = bEncomienda.getRuc_consignado().isEmpty() ? bEncomienda.getDni_consignado().trim() : bEncomienda.getRuc_consignado().trim();
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

        String documentoRemitente = bEncomienda.getRuc().trim().isEmpty() ? bEncomienda.getDniGiro().trim() : bEncomienda.getRuc().trim();
        String codigoRemitente = documentoRemitente.length() == 11 ? "6" : "1";

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
                        .numerodocumentoidentidad(vEmpresas.get().getRuc().trim())
                        .apellidosnombresdenominacionrazonsocial(vEmpresas.get().getRazon().trim())
                        .nombrecomercial(null)
                        .codigopais("PE")
                        .codigoubicaciongeografica(vEmpresas.get().getUbigeo().trim())
                        .codigoestablecimientoanexo(null)
                        .departamento(vEmpresas.get().getDepartamento().trim())
                        .provincia(vEmpresas.get().getProvincia().trim())
                        .distrito(vEmpresas.get().getDistrito().trim())
                        .urbanizacion(vEmpresas.get().getUrbanizacion().trim())
                        .direccion(vEmpresas.get().getDireccion().trim())
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
                        Integer.parseInt(bEncomienda.getCantidad3());

        VgrrEnvio envio =
                VgrrEnvio.builder()
                        .identificador("1")
                        .codigomotivo("04")
                        .descripcionmotivo("Traslado entre establecimientos de la misma empresa")
                        .sustentodiferenciapeso(null)
                        .pesobrutoitemsseleccionados(null)
                        .unidadmedidaitemsseleccionados(null)
                        .pesobruto(bEncomienda.getKilos())
                        .unidadmedida(changeUm(bEncomienda.getUM()))
                        .numerobultos(nroBultos)
                        .build();


        ZonedDateTime limaTime = ZonedDateTime.now(ZoneId.of("America/Lima"));

        VgrrTramo tramo =
                VgrrTramo.builder()
                        .numerocorrelativo(null)
                        .modalidad("02")
                        .fechaentregabieniniciotraslado(limaTime.toLocalDateTime())
                        .tipoevento(null)
                        .build();

        String[] partes = bProgramacionSalida.get().getPilotoD().split(" ");
        String apellidos = String.join(" ", Arrays.copyOfRange(partes, 0, 2));
        String nombres = String.join(" ", Arrays.copyOfRange(partes, 2, partes.length));


        VgrrConductorSecundario vgrrConductorSecundario =
                VgrrConductorSecundario.builder()
                        .codigotipoidentificacion("1")
                        .numerodocumentoidentificacion("")
                        .nombres(nombres)
                        .apellidos(apellidos)
                        .numerolicenciaconducir(bProgramacionSalida.get().getPilotoB())
                        .build();

        Optional<V_Agencias> vAgenciasPartida = vAgenciasRepository.findById(bEncomienda.getAgencia().trim());
        Optional<V_Agencias> vAgenciasLlegada = vAgenciasRepository.findById(bEncomienda.getDestino1().trim());

        if (vAgenciasPartida.isPresent()) {
            VgrrDireccion oDireccionPuntoPartida
                    = VgrrDireccion
                    .builder()
                    .ubigeo(vAgenciasPartida.get().getUbigeo())
                    .puntogeorreferencialongitud(new BigDecimal(vAgenciasPartida.get().getLongitud().trim()))
                    .puntogeorreferencialatitud(new BigDecimal(vAgenciasPartida.get().getLatitud().trim()))
                    .numerorucasociado(vEmpresas.get().getRuc().trim())
                    .descripcion(vAgenciasPartida.get().getDireccion().trim())
                    .build();
        }

        if (vAgenciasLlegada.isPresent()) {
            VgrrDireccion oDireccionPuntoLlegada
                    = VgrrDireccion
                    .builder()
                    .ubigeo(vAgenciasLlegada.get().getUbigeo())
                    .puntogeorreferencialongitud(new BigDecimal(vAgenciasLlegada.get().getLongitud().trim()))
                    .puntogeorreferencialatitud(new BigDecimal(vAgenciasLlegada.get().getLatitud().trim()))
                    .numerorucasociado(vEmpresas.get().getRuc().trim())
                    .descripcion(vAgenciasLlegada.get().getDireccion().trim())
                    .build();
        }


        List<VgrrDetalleGuia> vgrrDetalleGuias = new ArrayList<>();

        int id = 1;

        if (bEncomienda.getCantidad1().isEmpty()) {
            VgrrDetalleGuia vgrrDetalleGuia =
                    VgrrDetalleGuia.builder()
                            .numeroorden(id++)
                            .cantidad(new BigDecimal(bEncomienda.getCantidad1()))
                            .productodescripcion(bEncomienda.getDescripcion1())
                            .codigounidadmedida(bEncomienda.getUM1())
                            .build();
            vgrrDetalleGuias.add(vgrrDetalleGuia);
        }

        if (bEncomienda.getCantidad2().isEmpty()) {
            VgrrDetalleGuia vgrrDetalleGuia =
                    VgrrDetalleGuia.builder()
                            .numeroorden(id++)
                            .cantidad(new BigDecimal(bEncomienda.getCantidad2()))
                            .productodescripcion(bEncomienda.getDescripcion2())
                            .codigounidadmedida(bEncomienda.getUM2())
                            .build();
            vgrrDetalleGuias.add(vgrrDetalleGuia);
        }

        if (bEncomienda.getCantidad3().isEmpty()) {
            VgrrDetalleGuia vgrrDetalleGuia =
                    VgrrDetalleGuia.builder()
                            .numeroorden(id++)
                            .cantidad(new BigDecimal(bEncomienda.getCantidad3()))
                            .productodescripcion(bEncomienda.getDescripcion3())
                            .codigounidadmedida(bEncomienda.getUM3())
                            .build();
            vgrrDetalleGuias.add(vgrrDetalleGuia);
        }


        //VgrrDetalleGuia vgrrDetalleGuia

    }*/
}
