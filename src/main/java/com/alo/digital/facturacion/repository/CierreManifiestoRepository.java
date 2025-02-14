package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.dto.GrtGeneradoDto;
import com.alo.digital.facturacion.dto.ManifiestoDto;
import com.alo.digital.facturacion.entity.CierreManifiesto;
import com.alo.digital.facturacion.entity.V_Nivel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CierreManifiestoRepository extends JpaRepository<CierreManifiesto, Integer> {

    @Query("SELECT new com.alo.digital.facturacion.dto.ManifiestoDto(cm.ID,TRIM(cm.agenciaD),bs.nro,TRIM(bs.destinoD),bs.fecha,bs.servicio,TRIM(bs.servicioD),bs.bus, COUNT(cmd.id) , ISNULL(sgrt.generado,cm.grtsGenerados)) " +
            "FROM CierreManifiesto as cm " +
            "JOIN B_ProgramacionSalida as bs ON cm.nroProg = bs.nro " +
            "JOIN CierreManifiestoD as cmd ON cm.ID = cmd.id " +
            "LEFT JOIN V_SolicitudGrt as sgrt ON cm.ID = sgrt.idManifiesto " +
            "WHERE cm.agencia <> '888' " +
            "AND cm.agencia = :agencia AND bs.fecha = :fecha " +
            "AND bs.tercero = 0 " +
            "GROUP BY cm.ID,cm.agenciaD,bs.nro,bs.destinoD,bs.fecha,bs.servicio,bs.servicioD,bs.bus, cmd.id,sgrt.generado,cm.grtsGenerados")
    Page<ManifiestoDto> findCierreManifiestoSummary(Pageable pageable, @Param("agencia") String agencia, @Param("fecha") LocalDateTime fecha);


    @Query("SELECT new com.alo.digital.facturacion.dto.GrtGeneradoDto(grt.nro,be.serie+'-'+be.documento+'-'+be.numero ,grt.numeroserie+'-'+grt.codigotipodocumento+'-'+grt.numerocorrelativo,grt.generado) " +
            "FROM CierreManifiesto as cm " +
            "JOIN V_GuiaRemisionTransportista as grt " +
            "on cm.ID = grt.nrocierremanifiesto " +
            "JOIN B_Encomiendas as be " +
            "ON be.nro = grt.nroencomienda " +
            "WHERE cm.ID = :id")
    Page<GrtGeneradoDto> findCierreManifiestoGuiasGeneradas(Pageable pageable, @Param("id") Integer ID);

    @Query("SELECT grt.nro+'' " +
            "FROM CierreManifiesto as cm " +
            "JOIN V_GuiaRemisionTransportista as grt " +
            "on cm.ID = grt.nrocierremanifiesto " +
            "JOIN B_Encomiendas as be " +
            "ON be.nro = grt.nroencomienda " +
            "WHERE cm.ID = :id")
    List<String> findListCierreManifiestoGuiasGeneradas(@Param("id") Integer ID);
}
