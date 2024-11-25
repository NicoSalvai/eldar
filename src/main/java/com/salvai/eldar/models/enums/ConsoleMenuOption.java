package com.salvai.eldar.models.enums;


import java.util.Arrays;

public enum ConsoleMenuOption {
    REGISTRAR_PERSONA("1"), REGISTRAR_TARJETA("2"), CONSULTAR_TARJETAS_POR_DNI("3"),
    CONSULTAR_TASAS_POR_FECHA("4"), SALIR("5"), NONE("");

    private final String value;

    ConsoleMenuOption(String value) {
        this.value = value;
    }

    public static ConsoleMenuOption getConsoleMenuOption(String value){
        return Arrays.stream(ConsoleMenuOption.values())
            .filter(option -> option.value.equals(value))
            .findFirst()
            .orElse(NONE);
    }
}
