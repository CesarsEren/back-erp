package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.dto.ManifiestoDto;
import com.alo.digital.facturacion.dto.V_GuiaDto;
import com.alo.digital.facturacion.entity.CierreManifiesto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface V_GuiaRepository extends JpaRepository<CierreManifiesto, Integer> {

    /*
    @Query("SELECT new com.alo.digital.facturacion.dto.V_GuiaDto() " +
            "from ")
    Page<V_GuiaDto> findGuiasByNroManifiesto(Pageable pageable, @Param("idmanifiesto") Integer idmanifiesto);
*/

}