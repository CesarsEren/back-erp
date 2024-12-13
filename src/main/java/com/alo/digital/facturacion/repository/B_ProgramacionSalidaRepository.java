package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.dto.ManifiestoDto;
import com.alo.digital.facturacion.entity.B_ProgramacionSalida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface B_ProgramacionSalidaRepository extends JpaRepository<B_ProgramacionSalida,Integer>{
    @Query("SELECT bs " +
            "FROM CierreManifiesto as cm " +
            "JOIN B_ProgramacionSalida as bs " +
            "ON cm.nroProg = bs.nro "+
            "WHERE cm.agencia <> '888' " +
            "AND cm.ID = :ID")
    Optional<B_ProgramacionSalida> findBProgramacionSalidaByCierreManifiestoID(@Param("ID") Integer ID);

}
