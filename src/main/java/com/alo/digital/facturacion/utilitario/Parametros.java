package com.alo.digital.facturacion.utilitario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class Parametros<T> {

	public SqlParameterSource inParametro(T valores, Map<String, Object> objectos) {
		SqlParameterSource sqlParameterSource = null;
		try {
			MapSqlParameterSource source = new MapSqlParameterSource();
			log.info("-------------------------------------------");
			if (valores != null) {
				Field[] array_variables = valores.getClass().getDeclaredFields();
				for (Field variable : array_variables) {
					if (!variable.getType().isAssignableFrom(MultipartFile.class)) {
						log.info(variable.getName() + " : " + variable.get(valores));
						source.addValue(variable.getName(), variable.get(valores));
					}
				}
			}
			if (objectos != null) {
				Map.Entry<String, Object> entry = objectos.entrySet().iterator().next();
				String value = entry.getValue().toString();
				log.info(entry.getKey() + " : " + entry.getValue());
				source.addValue(entry.getKey(), entry.getValue());
			}
			sqlParameterSource = source;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return sqlParameterSource;
	}

	public T out_parametros(Map<String, Object> result) {
		return null;
	}

	public List<Map<String, Object>> ejecutar(SimpleJdbcCall conexion, JdbcTemplate jdbcTemplate, T valores,
			Map<String, Object> objectos, String procedure) {
		List<Map<String, Object>> result = null;
		try {
			conexion = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedure);
			result = (valores == null && objectos == null
					? (List<Map<String, Object>>) conexion.execute().get("#result-set-1")
					: (List<Map<String, Object>>) conexion.execute(inParametro(valores, objectos))
							.get("#result-set-1"));
			log.info("-------------------------------------------");
			log.info("RESULTADO (" + procedure + "):");
			log.info(result.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return result;
	}

}
