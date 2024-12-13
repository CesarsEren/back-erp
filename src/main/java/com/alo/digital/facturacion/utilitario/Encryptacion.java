package com.alo.digital.facturacion.utilitario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Encryptacion {
	@Autowired
	PasswordEncoder passwordEncoder;

	public String encryptar(String cadena) {
		return passwordEncoder.encode(cadena);
	}

}
