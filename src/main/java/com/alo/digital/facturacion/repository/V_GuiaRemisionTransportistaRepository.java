package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.database.V_GuiaRemisionTransportista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface V_GuiaRemisionTransportistaRepository extends JpaRepository<V_GuiaRemisionTransportista, Integer> {

    Optional<V_GuiaRemisionTransportista> findByNroencomienda(@Param("nroencomienda") Integer nroencomienda);

    @Query("SELECT v FROM V_GuiaRemisionTransportista v " +
            "WHERE v.fechaemision = :fechaemision " +
            "AND ( (:enviado = 0 AND v.enviado IN (0,1)) OR (:enviado <> 0 AND v.enviado = :enviado) )")
    Page<V_GuiaRemisionTransportista> findByFechaemisionAndEnviado(Pageable pageable, @Param("fechaemision") LocalDate fechaemision, @Param("enviado") Integer enviado);

    List<V_GuiaRemisionTransportista> findByFechaemisionAndEnviado(@Param("fechaemision") LocalDate fechaemision, @Param("enviado") Integer enviado);

}
