package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class V_UsuarioDto {

    public String out_cod_usuario;
    public String out_nombre;
    public String out_usuario;
    public String out_clave;
    public List<Rol> out_roles;

}
