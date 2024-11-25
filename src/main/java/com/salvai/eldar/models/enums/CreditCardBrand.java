package com.salvai.eldar.models.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum CreditCardBrand {
    VISA, NARA, AMEX;

    public static String valuesToString(){
        return Arrays.stream(values()).map(Enum::toString).collect(Collectors.joining(","));
    }
}
