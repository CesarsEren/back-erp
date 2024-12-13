package com.alo.digital.facturacion.rest;

import com.alo.digital.facturacion.dto.Credenciales;
import com.alo.digital.facturacion.dto.Jwt;
import com.alo.digital.facturacion.jwt.JwtProvider;
import com.alo.digital.facturacion.utilitario.Form;
import com.alo.digital.facturacion.utilitario.Mensajes;
import com.alo.digital.facturacion.utilitario.UtilGenerico;
import com.alo.digital.facturacion.utilitario.Utilitario;
import com.alo.digital.facturacion.vo.Error;
import com.alo.digital.facturacion.vo.Response;
import com.alo.digital.facturacion.vo.ResponseList;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class CredencialesController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvides;
    @Autowired
    Utilitario util;
    @Autowired
    Form form;

   // @PostMapping(name = "credenciales", produces = "application/json", consumes = {MediaType.APPLICATION_JSON_VALUE})
    //@RequestMapping( name = "credenciales", method = RequestMethod.POST)
    @PostMapping("/credenciales")
    @ResponseBody
    public ResponseEntity<?> validarAcceso(@RequestBody Credenciales credenciales) {
        log.trace("---INICIA PROCESO LOGIN");

        try {
            List<Error> errores = form.formCredenciales(credenciales);
            if (!errores.isEmpty()) {
                ResponseList<Error> response = new UtilGenerico<Error>().crearMensajeList(errores, 422,
                        Mensajes.MSG_VALIDACION_INPUT, Mensajes.MSG_VALIDACION_INPUT);
                return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credenciales.in_usuario, credenciales.in_clave));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvides.generarToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Jwt _jwt = new Jwt();
            _jwt.setOut_token(jwt);
            _jwt.setOut_usuario(userDetails.getUsername());
            _jwt.setOut_authorities(userDetails.getAuthorities());

            Response<Jwt> resultado = new UtilGenerico<Jwt>().crearMensaje(_jwt, 200,
                    "Resultado OK, usuario encontrado", Mensajes.MSG_CLIENTE_LOGIN_OK);

            return new ResponseEntity<>(resultado, util.validarEstadoRespuesta(resultado.getOut_estado()));
        } catch (Exception e) {
            log.error(e.getMessage());
            Error error = new Error("in_usuario", Mensajes.MSG_CLIENTE_LOGIN_ERROR);
            Response<Error> response = new UtilGenerico<Error>().crearMensaje(error, 422, e.toString(),
                    Mensajes.MSG_CLIENTE_EXCEPCION);
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping(path = "{clave}")
    @ResponseBody
    @Hidden
    public ResponseEntity<?> encryptarPassword(@PathVariable("clave") String clave) {

        String clave_encryp = passwordEncoder.encode(clave);
        return new ResponseEntity<>(clave_encryp, HttpStatus.OK);

    }
}