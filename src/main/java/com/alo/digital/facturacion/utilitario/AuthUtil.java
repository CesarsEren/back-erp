package com.alo.digital.facturacion.utilitario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static String obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
            return authentication.getName(); // Retorna el nombre de usuario (username)
        }
        return null; // Si no hay usuario autenticado
    }
}
