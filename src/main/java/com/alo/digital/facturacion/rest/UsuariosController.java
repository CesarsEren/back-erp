package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.entity.V_Usuarios;
import com.alo.digital.facturacion.repository.V_UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuariosController {

    @Autowired
    V_UsuarioRepository vUsuarioRepository;

    @GetMapping("/usuarios")
    public List<V_Usuarios> getAll() {

        return vUsuarioRepository.findAll();
    }
}
