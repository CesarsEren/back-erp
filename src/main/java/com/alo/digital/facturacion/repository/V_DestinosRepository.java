package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Destinos;
import com.alo.digital.facturacion.entity.V_Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface V_DestinosRepository extends JpaRepository<V_Destinos, Integer> {

}
