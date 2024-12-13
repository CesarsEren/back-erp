package com.alo.digital.facturacion.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
	public T out_data;
	public String out_mensaje_interno;
	public String out_mensaje_cliente;
	public Integer out_estado;

}
