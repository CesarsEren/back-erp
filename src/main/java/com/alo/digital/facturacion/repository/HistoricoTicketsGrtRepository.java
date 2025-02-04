package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.HistoricoTicketsGrt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoTicketsGrtRepository extends JpaRepository<HistoricoTicketsGrt,Integer> {

}
