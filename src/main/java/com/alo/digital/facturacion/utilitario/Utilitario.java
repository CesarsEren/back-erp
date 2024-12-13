package com.alo.digital.facturacion.utilitario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class Utilitario {

		public HttpStatus validarEstadoRespuesta(Integer estado) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		switch (estado) {
		case 200:
			status = HttpStatus.OK;
			break;
		case 500:
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		case 204:
			status = HttpStatus.NO_CONTENT;
			break;
		case 400:
			status = HttpStatus.BAD_REQUEST;
			break;
		case 422:
			status = HttpStatus.UNPROCESSABLE_ENTITY;
			break;
		default:
			status = HttpStatus.NOT_FOUND;
			break;
		}
		return status;
	}

	public boolean validarResultadoNullVacio(List<Map<String, Object>> resultado) {
		return (resultado == null ? false
				: (resultado.size() == 0 ? false : (resultado.isEmpty() == true ? false : true)));
	}

	public boolean validarResultadoNull(List<Map<String, Object>> resultado) {
		boolean is_null = (resultado == null ? false
				: (resultado.size() == 0 ? false : (resultado.isEmpty() == true ? false : true)));
		if (is_null) {
			Map<String, Object> valores = resultado.get(0);
			Map.Entry<String, Object> entry = valores.entrySet().iterator().next();
			String value = entry.getValue().toString();
			log.info("-------------------------------------------");
			log.info("Valor retorno posicion 1: " +  value);
			log.info("-------------------------------------------");
			is_null = (Integer.parseInt(value) > 0 ? true : false);
		}
		return is_null;
	}

	public boolean validarNumeroEntero(Object valor) {
		try {
			Integer.parseInt(valor.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean validarCambioPassword(String in_password, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(in_password, password);
	}

	public String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer")) {
			return header.replace("Bearer", "");
		} else {
			return null;
		}
	}
}
