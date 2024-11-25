package com.salvai.eldar.models;

import java.time.LocalDate;

public record Person(String firstName, String lastName, String dni, LocalDate birthDate, String email){
}
