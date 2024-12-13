package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Nivel;
import com.alo.digital.facturacion.entity.V_Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface V_NivelRepository extends JpaRepository<V_Nivel, String> {

    Optional<V_Nivel> findByCodigo(String s);
}
