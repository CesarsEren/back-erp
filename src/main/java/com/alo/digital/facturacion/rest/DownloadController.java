package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.SolicitudGrtDto;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionGenerado;
import com.alo.digital.facturacion.dto.V_GuiaRemisionGeneradoDto;
import com.alo.digital.facturacion.repository.CierreManifiestoRepository;
import com.alo.digital.facturacion.repository.V_GuiaRemisionGeneradoRepository;
import com.alo.digital.facturacion.request.RqSolicitudBinario;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("download")
public class DownloadController {

    @Autowired
    V_GuiaRemisionGeneradoRepository vGuiaRemisionGeneradoRepository;

    @GetMapping
    public List<V_GuiaRemisionGeneradoDto> findAllRepository() {
        List<V_GuiaRemisionGenerado> vGuiaRemisionGenerados = vGuiaRemisionGeneradoRepository.findAll();
        List<V_GuiaRemisionGeneradoDto> vGuiaRemisionGeneradoDtos = new ArrayList<>();

        vGuiaRemisionGenerados.forEach(vGuiaRemisionGenerado -> {
            V_GuiaRemisionGeneradoDto vGuiaRemisionGeneradoDto = new V_GuiaRemisionGeneradoDto();
            vGuiaRemisionGeneradoDto.setId(vGuiaRemisionGeneradoDto.getId());
            vGuiaRemisionGeneradoDto.setRepresentacionimpresa(Base64.getEncoder().encodeToString(vGuiaRemisionGenerado.getRepresentacionimpresa()));
            vGuiaRemisionGeneradoDto.setDocumentofirmado(Base64.getEncoder().encodeToString(vGuiaRemisionGenerado.getDocumentofirmado()));
            vGuiaRemisionGeneradoDtos.add(vGuiaRemisionGeneradoDto);
        });
        return vGuiaRemisionGeneradoDtos;
    }

    @PostMapping("pdf/{id}")
    public ResponseEntity<byte[]> getPdf(@RequestBody RqSolicitudBinario rq) {
        Optional<V_GuiaRemisionGenerado> vGuiaRemisionGenerados = vGuiaRemisionGeneradoRepository.findByNroguia(rq.getNroGuia());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", rq.getNombreDocumento().concat(".pdf")); // Nombre del archivo
        return vGuiaRemisionGenerados.map(vGuiaRemisionGenerado -> new ResponseEntity<>(vGuiaRemisionGenerado.getRepresentacionimpresa(), headers, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND));
    }

    @Autowired
    CierreManifiestoRepository cierreManifiestoRepository;

    @PostMapping("all-pdfs")
    public ResponseEntity<byte[]> getAllPdf(@RequestBody SolicitudGrtDto solicitudGrtDto) throws IOException {
        List<String> nrosGuia = cierreManifiestoRepository.findListCierreManifiestoGuiasGeneradas(solicitudGrtDto.getIdManifiesto());
        //List<V_GuiaRemisionGenerado> vGuiaRemisionGenerados = vGuiaRemisionGeneradoRepository.getRepresentacionesImpresas(nrosGuia);

        //List<byte[]> pdfs = vGuiaRemisionGenerados.stream().map(V_GuiaRemisionGenerado::getRepresentacionimpresa).collect(Collectors.toList());
        List<byte[]> pdfs = vGuiaRemisionGeneradoRepository.getRepresentacionesImpresas(nrosGuia);
        byte[] pdfMerge = mergePdfs(pdfs);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "MANIFIESTO-PDFS".concat(LocalDate.now().toString()).concat(".pdf")); // Nombre del archivo
        return new ResponseEntity<>(pdfMerge, headers, HttpStatus.OK);
    }


    public byte[] mergePdfs(List<byte[]> pdfFiles) throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        merger.setDestinationStream(outputStream);

        for (byte[] pdfData : pdfFiles) {
            merger.addSource(new java.io.ByteArrayInputStream(pdfData));
        }

        merger.mergeDocuments(null);
        return outputStream.toByteArray();
    }


    @PostMapping("zip/{id}")
    public ResponseEntity<byte[]> getXML(@RequestBody RqSolicitudBinario rq) {
        Optional<V_GuiaRemisionGenerado> vGuiaRemisionGenerados = vGuiaRemisionGeneradoRepository.findByNroguia(rq.getNroGuia());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setContentDispositionFormData("attachment", rq.getNombreDocumento().concat(".zip")); // Nombre del archivo
        return vGuiaRemisionGenerados.map(vGuiaRemisionGenerado -> new ResponseEntity<>(vGuiaRemisionGenerado.getDocumentofirmado(), headers, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND));
    }


    @PostMapping("cdr/{id}")
    public ResponseEntity<byte[]> getCdr(@RequestBody RqSolicitudBinario rq) {
        Optional<V_GuiaRemisionGenerado> vGuiaRemisionGenerados = vGuiaRemisionGeneradoRepository.findByNroguia(rq.getNroGuia());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setContentDispositionFormData("attachment", "R-" + rq.getNombreDocumento().concat(".zip")); // Nombre del archivo
        return vGuiaRemisionGenerados.map(vGuiaRemisionGenerado -> new ResponseEntity<>(vGuiaRemisionGenerado.getConstanciarecepcion(), headers, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND));
    }

}
