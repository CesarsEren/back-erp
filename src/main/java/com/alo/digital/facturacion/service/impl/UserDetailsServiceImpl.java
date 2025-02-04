package com.alo.digital.facturacion.service.impl;

import com.alo.digital.facturacion.dto.Autenticacion;
import com.alo.digital.facturacion.dto.Rol;
import com.alo.digital.facturacion.dto.V_UsuarioDto;
import com.alo.digital.facturacion.entity.V_Nivel;
import com.alo.digital.facturacion.entity.V_Usuarios;
import com.alo.digital.facturacion.repository.V_NivelRepository;
import com.alo.digital.facturacion.repository.V_UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    V_UsuarioRepository vUsuarioRepository;
    @Autowired
    V_NivelRepository vNivelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        V_Usuarios resultado = vUsuarioRepository.findByUsuario(username);

        V_UsuarioDto vUsuarioDto = new V_UsuarioDto();
        vUsuarioDto.setOut_usuario(resultado.getUsuario().trim());
        vUsuarioDto.setOut_cod_usuario(resultado.getCodusuario().trim());
        vUsuarioDto.setOut_clave(resultado.getPassword().trim());
        vUsuarioDto.setOut_nombre(resultado.getNomusuario().trim());

        Optional<V_Nivel> vNivel = vNivelRepository.findByCodigo(resultado.getNivel());
        List<Rol> niveles = new ArrayList<>();
        vNivel.ifPresent(v_nivel -> niveles.add(new Rol(v_nivel.getDescripcion().trim())));
        vUsuarioDto.setOut_roles(niveles);
        return Autenticacion.build(vUsuarioDto);
    }

}
