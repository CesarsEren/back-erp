package com.alo.digital.facturacion.utilitario;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Input {
	public boolean validarVacioString(String cadena) {
		return (cadena == null ? true : (cadena == "" ? true : false));
	}

	public String quitarEspacios(String cadena) {
		return cadena.replace(ExpresionRegular.espacios, "");
	}

	public boolean validarCorreo(String cadena) {
		return !Pattern.matches(ExpresionRegular.correo, cadena);
	}

	public boolean validarTexto(String cadena) {
		return !Pattern.matches(ExpresionRegular.cadena, cadena);
	}

	public boolean validarDecimal(Object cadena) {
		try {
			Double.parseDouble(cadena.toString());
			String[] separado = cadena.toString().split("\\.");
			return (separado[1].length()> 2 ? true: false);
		} catch (NumberFormatException e) {
			return true;
		}		
	}

	public boolean validarDni(Object cadena) {
		return !Pattern.matches(ExpresionRegular.dni, cadena.toString());
	}

	public boolean validarCorreo(Object cadena) {
		return !Pattern.matches(ExpresionRegular.correo, cadena.toString());
	}

	public boolean validarPasaporte(Object cadena) {
		return !Pattern.matches(ExpresionRegular.dni, cadena.toString());
	}

	public boolean validarCarnetExtranjeria(Object cadena) {
		return !Pattern.matches(ExpresionRegular.dni, cadena.toString());
	}

	public boolean validarNroDocumento(String numero, Integer tipo) {
		boolean estado = true;
		switch (tipo) {
		case 1:
			estado = validarDni(numero);
			break;
		case 2:
			estado = validarCarnetExtranjeria(numero);
		case 3:
			estado = validarPasaporte(numero);
		default:
			break;
		}
		return estado;

	}
}
