package com.alo.digital.facturacion.jwt;

import com.alo.digital.facturacion.dto.Autenticacion;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Integer expiration = 36000;

	public String generarToken(Authentication authentication) {
		Autenticacion usuario = (Autenticacion) authentication.getPrincipal();
		return Jwts.builder().setSubject(usuario.out_usuario).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))//.claim("ruc", usuario.out_ruc)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String getNombreUsuarioFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public String getRucUsuarioFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("ruc").toString();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Token mal formado");
		} catch (UnsupportedJwtException e) {
			log.error("Token no soportado");
		} catch (ExpiredJwtException e) {
			log.error("Token expirado");
		} catch (IllegalArgumentException e) {
			log.error("Token vacío");
		} catch (SignatureException e) {
			log.error("Fallo en la firma");
		}
		return false;
	}
}
