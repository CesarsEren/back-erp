package com.alo.digital.facturacion.enumeration;

public enum ColaGrtState {

    POR_CONSULTAR(0, "POR CONSULTAR"),
    ACEPTADO(1, "ACEPTADO"),
    EXCEPTION(2, "EXCEPTION"),
    REINTENTOS_SUPERADOS(3, "REINTENTOS SUPERADOS");

    private final int code;
    private final String description;

    ColaGrtState(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ColaGrtState fromCode(int code) {
        for (ColaGrtState state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        throw new IllegalArgumentException("Código no válido: " + code);
    }

}
