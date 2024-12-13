package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Empresas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface V_EmpresasRepository extends CrudRepository<V_Empresas,String> {

}
