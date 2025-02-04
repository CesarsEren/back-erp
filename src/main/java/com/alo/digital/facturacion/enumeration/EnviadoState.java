package com.alo.digital.facturacion.enumeration;

public enum EnviadoState {
    POR_ENVIAR(0, "Por enviar"),
    EN_PROCESO(1, "En proceso"),
    ACEPTADO(2, "Aceptado"),
    EXCEPTION(3, "Exception");

    private final int code;
    private final String description;

    EnviadoState(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static EnviadoState fromCode(int code) {
        for (EnviadoState state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        throw new IllegalArgumentException("Código no válido: " + code);
    }
}
