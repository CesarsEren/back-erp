package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Bus;
import com.alo.digital.facturacion.entity.V_Empleados;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface V_EmpleadosRepository extends JpaRepository<V_Empleados,String> {

}
