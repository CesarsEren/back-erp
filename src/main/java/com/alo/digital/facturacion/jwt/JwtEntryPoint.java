package com.alo.digital.facturacion.jwt;

import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.vo.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("restAuthenticationEntryPoint")
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("Error en el método commence");
		Response<Error> respuesta = new UtilGenerico<Error>().crearMensaje(null, 403,
				"No autorizado, no ha iniciado sesión", "No autorizado, inicie sesión para acceder al recurso");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), respuesta);
		} catch (Exception e) {
			throw new ServletException();
		}
	}

}
