package com.alo.digital.facturacion.utilitario;

public class ExpresionRegular {
	public static String espacios = "/\\s/g/";
	public static String correo = "/ ^.+@[^\\.].*\\.[a-zA-Z]{2,}$/";
	public static String cadena = "/[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$/";
	public static String decimales = "/^[0-9]+([.][0-9]+)?$/";
	public static String dni = "/^[0-9]{8}$/";
	public static String pasaporte = "/^[a-z]{3}[0-9]{6}[a-z]?$/i/";
	public static String carne_extranjeria = "/^[0-9]{12}$/";
}
