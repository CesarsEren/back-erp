package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_CorrelativosElectronicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface V_CorrelativosElectronicosRepository extends JpaRepository<V_CorrelativosElectronicos, Integer> {

    @Query("SELECT ce FROM V_CorrelativosElectronicos ce WHERE ce.empresa = :empresa AND ce.terminal = :terminal AND ce.documento = :documento")
    public Optional<V_CorrelativosElectronicos> findByEmpresaAndTerminal(@Param("empresa") String empresa, @Param("terminal") String terminal, @Param("documento") String documento);

}
