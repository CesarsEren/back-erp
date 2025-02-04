package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.database.colas.SisColaGrt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SisColaGrtRepository extends JpaRepository<SisColaGrt, Integer> {

  List<SisColaGrt> findByEstado(@Param("estado") int estado);

  @Query("SELECT s FROM SisColaGrt as s WHERE s.estado in(0,2)")
  List<SisColaGrt> findQueue();

  Optional<SisColaGrt> findByNroGuia(@Param("nroGuia") int nroGuia);

}