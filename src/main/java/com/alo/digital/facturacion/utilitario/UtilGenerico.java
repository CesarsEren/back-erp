package com.alo.digital.facturacion.utilitario;


import com.alo.digital.facturacion.vo.Response;
import com.alo.digital.facturacion.vo.ResponseList;

import java.util.List;

public class UtilGenerico<T> {
	public Response<T> crearMensaje(T data, Integer codigo, String msg_interno, String msg_cliente) {
		Response<T> respuesta = new Response<>();
		if (data != null) {
			respuesta.out_data = data;
		}
		respuesta.out_estado = codigo;
		respuesta.out_mensaje_interno = msg_interno;
		respuesta.out_mensaje_cliente = msg_cliente;
		return respuesta;
	}

	public ResponseList<T> crearMensajeList(List<T> data, Integer codigo, String msg_interno, String msg_cliente) {
		ResponseList<T> respuesta = new ResponseList<>();
		if (data != null) {
			respuesta.out_data = data;
		}
		respuesta.out_estado = codigo;
		respuesta.out_mensaje_interno = msg_interno;
		respuesta.out_mensaje_cliente = msg_cliente;
		return respuesta;
	}
}
