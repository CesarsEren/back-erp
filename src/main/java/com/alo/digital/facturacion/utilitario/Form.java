package com.alo.digital.facturacion.utilitario;

/*
import com.venta.delivery.portal.dto.Credenciales;
import com.venta.delivery.portal.dto.InCategoria;
import com.venta.delivery.portal.dto.InCourier;
import com.venta.delivery.portal.dto.InProducto;*/
import com.alo.digital.facturacion.dto.Credenciales;
import com.alo.digital.facturacion.vo.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Form {


    private List<Error> errores;

    @Autowired
    Input input;
    @Autowired
    Utilitario util;


    public List<Error> formCredenciales(Credenciales credenciales) {
        errores = new ArrayList<>();
        if (credenciales.in_usuario == null || input.validarVacioString(credenciales.in_usuario.trim())) {
            Error error = new Error();
            error.out_value = "in_usuario";
            error.out_mensaje = Mensajes.INPUT_VACIO;
            errores.add(error);
        }
        if (credenciales.in_clave == null || input.validarVacioString(credenciales.in_clave.trim())) {
            Error error = new Error();
            error.out_value = "in_clave";
            error.out_mensaje = Mensajes.INPUT_VACIO;
            errores.add(error);
        }
        return errores;
    }

	/*
	@Autowired
	Input input;
	@Autowired
	Utilitario util;

	private List<Error> errores;

	public List<Error> formCredenciales(Credenciales credenciales) {
		errores = new ArrayList<>();
		if (credenciales.in_usuario == null || input.validarVacioString(credenciales.in_usuario.trim())) {
			Error error = new Error();
			error.out_value = "in_usuario";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (credenciales.in_clave == null || input.validarVacioString(credenciales.in_clave.trim())) {
			Error error = new Error();
			error.out_value = "in_clave";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		return errores;
	}

	public List<Error> formCategoriaPOST(InCategoria categorias) {
		errores = new ArrayList<>();
		if (categorias.in_nombre == null || input.validarVacioString(categorias.in_nombre.trim())) {
			Error error = new Error();
			error.out_value = "in_nombre";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (categorias.in_file == null || categorias.in_file.getSize() == 0) {
			Error error = new Error();
			error.out_value = "in_file";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		return errores;
	}

	public List<Error> formCategoriaPUT(Object id, InCategoria categorias) {
		errores = new ArrayList<>();
		if (id == null || util.validarNumeroEntero(id)) {
			Error error = new Error();
			error.out_value = "codigo";
			error.out_mensaje = Mensajes.VALUE_ID;
		}
		if (categorias.in_nombre == null || input.validarVacioString(categorias.in_nombre.trim())) {
			Error error = new Error();
			error.out_value = "in_nombre";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}		
		return errores;
	}

	public List<Error> formPathVariableId(Object id) {
		errores = new ArrayList<>();
		if (id == null || util.validarNumeroEntero(id)) {
			Error error = new Error();
			error.out_value = "codigo";
			error.out_mensaje = Mensajes.VALUE_ID;
		}
		return errores;
	}

	public List<Error> formProductoPOST(InProducto productos) {
		errores = new ArrayList<>();
		if (productos.in_categoria == null || !util.validarNumeroEntero(productos.in_categoria)
				|| productos.in_categoria == 0) {
			Error error = new Error();
			error.out_value = "in_categoria";
			error.out_mensaje = Mensajes.INPUT_SELECCION_0;
			errores.add(error);
		}
		if (productos.in_nombre == null || input.validarVacioString(productos.in_nombre.trim())) {
			Error error = new Error();
			error.out_value = "in_nombre";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (productos.in_file == null || productos.in_file.getSize() == 0) {
			Error error = new Error();
			error.out_value = "in_file";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (productos.in_precio == null || productos.in_precio == 0) {
			Error error = new Error();
			error.out_value = "in_precio";
			error.out_mensaje = "El precio debe que ser mayor a 0";
			errores.add(error);
		}
		if (productos.in_precio == null || input.validarDecimal(productos.in_precio)) {
			Error error = new Error();
			error.out_value = "in_precio";
			error.out_mensaje = Mensajes.INPUT_NO_FORMATO;
			errores.add(error);
		}

		return errores;
	}

	public List<Error> formProductoPUT(Object id, InProducto productos) {
		errores = new ArrayList<>();
		if (id == null || util.validarNumeroEntero(id)) {
			Error error = new Error();
			error.out_value = "codigo";
			error.out_mensaje = Mensajes.VALUE_ID;
		}
		if (productos.in_categoria == null || !util.validarNumeroEntero(productos.in_categoria)
				|| productos.in_categoria == 0) {
			Error error = new Error();
			error.out_value = "in_categoria";
			error.out_mensaje = Mensajes.INPUT_SELECCION_0;
			errores.add(error);
		}
		if (productos.in_nombre == null || input.validarVacioString(productos.in_nombre.trim())) {
			Error error = new Error();
			error.out_value = "in_nombre";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (productos.in_precio == null || productos.in_precio == 0) {
			Error error = new Error();
			error.out_value = "in_precio";
			error.out_mensaje = "El precio debe que ser mayor a 0";
			errores.add(error);
		}
		if (productos.in_precio == null || input.validarDecimal(productos.in_precio)) {
			Error error = new Error();
			error.out_value = "in_precio";
			error.out_mensaje = Mensajes.INPUT_NO_FORMATO;
			errores.add(error);
		}
		return errores;
	}

	public List<Error> formCourierPOST(InCourier courier) {
		errores = new ArrayList<>();
		if (courier.in_documento == null || !util.validarNumeroEntero(courier.in_documento)
				|| courier.in_documento == 0) {
			Error error = new Error();
			error.out_value = "in_documento";
			error.out_mensaje = Mensajes.INPUT_SELECCION_0;
			errores.add(error);
		}
		if (courier.in_nombres == null || input.validarTexto(courier.in_nombres.trim())) {
			Error error = new Error();
			error.out_value = "in_nombres";
			error.out_mensaje = Mensajes.INPUT_NO_FORMATO;
			errores.add(error);
		}
		if (courier.in_nombres == null || input.validarVacioString(courier.in_nombres.trim())) {
			Error error = new Error();
			error.out_value = "in_nombres";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_apellidos == null || input.validarTexto(courier.in_apellidos.trim())) {
			Error error = new Error();
			error.out_value = "in_apellidos";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_apellidos == null || input.validarVacioString(courier.in_apellidos.trim())) {
			Error error = new Error();
			error.out_value = "in_apellidos";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_nro_documento == null
				|| input.validarNroDocumento(courier.in_nro_documento, courier.in_documento)) {
			Error error = new Error();
			error.out_value = "in_nro_documento";
			error.out_mensaje = Mensajes.INPUT_NO_FORMATO;
			errores.add(error);
		}
		if (courier.in_correo == null || input.validarCorreo(courier.in_correo.trim())) {
			Error error = new Error();
			error.out_value = "in_correo";
			error.out_mensaje = Mensajes.INPUT_NO_FORMATO;
			errores.add(error);
		}
		if (courier.in_correo == null || input.validarVacioString(courier.in_correo.trim())) {
			Error error = new Error();
			error.out_value = "in_correo";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_usuario == null || input.validarVacioString(courier.in_usuario.trim())) {
			Error error = new Error();
			error.out_value = "in_usuario";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_clave == null || input.validarVacioString(courier.in_clave.trim())) {
			Error error = new Error();
			error.out_value = "in_clave";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		return errores;
	}

	public List<Error> formCourierPUT(Object id, InCourier courier) {
		errores = new ArrayList<>();
		if (id == null || util.validarNumeroEntero(id)) {
			Error error = new Error();
			error.out_value = "codigo";
			error.out_mensaje = Mensajes.VALUE_ID;
		}
		if (courier.in_documento == null || !util.validarNumeroEntero(courier.in_documento)
				|| courier.in_documento == 0) {
			Error error = new Error();
			error.out_value = "in_documento";
			error.out_mensaje = Mensajes.INPUT_SELECCION_0;
			errores.add(error);
		}
		if (courier.in_nombres == null || input.validarTexto(courier.in_nombres.trim())) {
			Error error = new Error();
			error.out_value = "in_nombres";
			error.out_mensaje = Mensajes.INPUT_NO_FORMATO;
			errores.add(error);
		}
		if (courier.in_nombres == null || input.validarVacioString(courier.in_nombres.trim())) {
			Error error = new Error();
			error.out_value = "in_nombres";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_apellidos == null || input.validarTexto(courier.in_apellidos.trim())) {
			Error error = new Error();
			error.out_value = "in_apellidos";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_apellidos == null || input.validarVacioString(courier.in_apellidos.trim())) {
			Error error = new Error();
			error.out_value = "in_apellidos";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		if (courier.in_nro_documento == null
				|| input.validarNroDocumento(courier.in_nro_documento, courier.in_documento)) {
			Error error = new Error();
			error.out_value = "in_nro_documento";
			error.out_mensaje = Mensajes.INPUT_NO_FORMATO;
			errores.add(error);
		}
		if (courier.in_usuario == null || input.validarVacioString(courier.in_usuario.trim())) {
			Error error = new Error();
			error.out_value = "in_usuario";
			error.out_mensaje = Mensajes.INPUT_VACIO;
			errores.add(error);
		}
		return errores;
	}*/

}
