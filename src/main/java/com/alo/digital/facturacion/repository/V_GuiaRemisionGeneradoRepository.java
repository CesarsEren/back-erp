package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.dto.GrtGeneradoDto;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionGenerado;
import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface V_GuiaRemisionGeneradoRepository extends JpaRepository<V_GuiaRemisionGenerado, Integer> {

    public Optional<V_GuiaRemisionGenerado> findByNroguia(@Param("nroguia") String nroguia);

    @Query("SELECT vg.representacionimpresa " +
            "FROM V_GuiaRemisionGenerado as vg " +
            "WHERE vg.nroguia IN :nrosguia"
    )
    public List<byte[]> getRepresentacionesImpresas(@Param("nrosguia") List<String> nrosguia);


}
