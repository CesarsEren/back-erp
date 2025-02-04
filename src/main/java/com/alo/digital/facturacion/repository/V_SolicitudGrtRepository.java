package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Nivel;
import com.alo.digital.facturacion.entity.V_SolicitudGrt;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface V_SolicitudGrtRepository extends JpaRepository<V_SolicitudGrt, Long> {

    @Query("select vs from V_SolicitudGrt vs where vs.idManifiesto = :idManifiesto")
    public Optional<V_SolicitudGrt> findByidManifiesto(Integer idManifiesto);

    @Query("SELECT vs FROM V_SolicitudGrt vs where vs.generado = 2")
    public List<V_SolicitudGrt> findSolicitudesGRT_PorGenerar();

    @Query("SELECT vs FROM V_SolicitudGrt vs where vs.generado = 1")
    public List<V_SolicitudGrt> findSolicitudesGRT_Generadas();

}
