package com.alo.digital.facturacion.dto;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autenticacion implements UserDetails {
    public String out_nombre;
    public String out_usuario;
    public String out_clave;
    public Collection<? extends GrantedAuthority> out_autoridades;

    public static Autenticacion build(V_UsuarioDto usuarios) {
        List<GrantedAuthority> autorizados = usuarios.out_roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.out_nombre)).collect(Collectors.toList());
        return new Autenticacion(usuarios.out_nombre, usuarios.out_usuario,
                usuarios.out_clave, autorizados);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return out_autoridades;
    }

    @Override
    public String getPassword() {
        return out_clave;
    }

    @Override
    public String getUsername() {
        return out_usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
