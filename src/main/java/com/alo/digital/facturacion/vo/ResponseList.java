package com.alo.digital.facturacion.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseList<T> {
	public List<T> out_data;
	public String out_mensaje_interno;
	public String out_mensaje_cliente;
	public Integer out_estado;

}
