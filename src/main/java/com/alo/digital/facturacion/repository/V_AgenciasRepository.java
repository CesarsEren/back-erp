package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Agencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface V_AgenciasRepository extends JpaRepository<V_Agencias,String> {



}
