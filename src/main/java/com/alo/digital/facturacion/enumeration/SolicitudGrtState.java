package com.alo.digital.facturacion.enumeration;

public enum SolicitudGrtState {

    POR_ENVIAR(0, "Sin generar"),
    GENERADO(1, "Generado"),
    EN_PROCESO(2, "En Proceso");


    private final int code;
    private final String description;

    SolicitudGrtState(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SolicitudGrtState fromCode(int code) {
        for (SolicitudGrtState state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        throw new IllegalArgumentException("Código no válido: " + code);
    }

    }
