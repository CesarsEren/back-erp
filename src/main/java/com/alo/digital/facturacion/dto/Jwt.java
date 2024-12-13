package com.alo.digital.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jwt {
	private String out_token;
	private String out_bearer = "Bearer";
	private String out_usuario;
	private Collection<? extends GrantedAuthority> out_authorities;
}
