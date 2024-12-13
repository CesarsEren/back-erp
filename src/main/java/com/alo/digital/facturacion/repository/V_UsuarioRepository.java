package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface V_UsuarioRepository extends JpaRepository<V_Usuarios, String> {

    V_Usuarios findByUsuario(String usuario);

}
