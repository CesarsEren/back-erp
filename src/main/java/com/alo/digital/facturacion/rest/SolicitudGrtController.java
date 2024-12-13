package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.utilitario.Utilitario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*")
@Slf4j
public class SolicitudGrtController {

    @Autowired
    Utilitario util;




}
