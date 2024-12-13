package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.B_Encomiendas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface B_EncomiendasRepository extends JpaRepository<B_Encomiendas,Integer> {


    @Query("SELECT be FROM CierreManifiestoD as cmd  " +
            "JOIN B_Encomiendas as be " +
            "ON cmd.nroEncomienda = be.nro " +
            "WHERE cmd.id = :ID")
    public Page<B_Encomiendas> findEncomiendasByNroManifiesto(Pageable pageable, @Param("ID") Integer ID);

    @Query("SELECT be FROM CierreManifiestoD as cmd  " +
            "JOIN B_Encomiendas as be " +
            "ON cmd.nroEncomienda = be.nro " +
            "WHERE cmd.id = :ID")
    public List<B_Encomiendas> findEncomiendasByNroManifiesto( @Param("ID") Integer ID);

}
