package com.salvai.eldar.controllers;

import com.salvai.eldar.models.enums.ConsoleMenuOption;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.models.exceptions.ValidationException;
import com.salvai.eldar.services.CreditCardService;
import com.salvai.eldar.services.PersonService;

import java.util.Scanner;

import static com.salvai.eldar.models.enums.ConsoleMenuOption.NONE;
import static com.salvai.eldar.models.enums.ConsoleMenuOption.SALIR;

public class ConsoleMenuController {
    private final Scanner scanner = new Scanner(System.in);
    private final PersonService personService = new PersonService();
    private final CreditCardService creditCardService = new CreditCardService();

    public void run() {

        ConsoleMenuOption consoleMenuOption = NONE ;

        while (!consoleMenuOption.equals(SALIR)) {
            System.out.println("""
                \n--- Menú Principal ---
                1. Registrar Persona
                2. Registrar Tarjeta
                3. Consultar Tarjetas por DNI
                4. Consultar Tasas por Fecha
                5. Salir""");

            System.out.print("\nIngrese la opcion deseada:");

            final var input = scanner.nextLine();
            consoleMenuOption = ConsoleMenuOption.getConsoleMenuOption(input);

            try {
                switch (consoleMenuOption) {
                    case REGISTRAR_PERSONA -> addPerson();
                    case REGISTRAR_TARJETA -> addCreditCard();
                    case CONSULTAR_TARJETAS_POR_DNI -> getCreditCardsByDni();
                    case CONSULTAR_TASAS_POR_FECHA -> getRates();
                    case SALIR -> {
                        System.out.println("Saliendo de la aplicacion...");
                        return;
                    }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (ValidationException ex){
                System.out.println("ERROR: " + ex.getMessage());
                for(var error : ex.getErrors()){
                    System.out.println(error);
                }
            } catch (Exception e) {
                System.out.println("Hubo un error inesperado.");
            }
        }
    }

    private void addPerson() throws ValidationException {
        System.out.println("Nombre: ");
        String firstName = scanner.nextLine();

        System.out.println("Apellido: ");
        String lastName = scanner.nextLine();

        System.out.println("DNI (sin puntos ni guiones): ");
        String dni = scanner.nextLine();

        System.out.println("Fecha de Nacimiento (dd-MM-yyyy): ");
        String birthDate = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        personService.registerPerson(firstName.trim(), lastName.trim(), dni.trim(), birthDate.trim(), email.trim());
        System.out.println("Persona registrada exitosamente.");
    }

    private void addCreditCard() throws ValidationException {
        System.out.printf("Marca (%s): %n", CreditCardBrand.valuesToString());
        String brand = scanner.nextLine();

        System.out.println("Numero (Sin guiones ni puntos): ");
        String number = scanner.nextLine();

        System.out.println("Fecha de Vencimiento (dd-MM-yyyy): ");
        String expirationDate = scanner.nextLine();

        System.out.println("Nombre Completo Titular: ");
        String fullName = scanner.nextLine();

        System.out.println("DNI (sin puntos ni guiones): ");
        String dni = scanner.nextLine();

        var person = personService.getPerson(dni.trim());

        creditCardService.registerCreditCard(brand.trim(), number.trim(), fullName.trim(), person, expirationDate.trim());
        System.out.println("Tarjeta registrada exitosamente.");
    }

    private void getCreditCardsByDni() throws ValidationException {
        System.out.println("DNI (sin puntos ni guiones): ");
        String dni = scanner.nextLine();

        final var cards = creditCardService.getCreditCardsByDni(dni.trim());

        if (cards.isEmpty()){
            System.out.printf("No hay tajetas asociadas al dni (%s)%n", dni);
        } else {
            System.out.printf("Las tarjetas asociadas al dni (%s) son:%n", dni);
            for(var card : cards){
                System.out.println(card);
            }
        }

    }

    private void getRates() throws ValidationException {
        System.out.println("Fecha (dd-MM-yyyy)(Dejar vacio para usar la fecha actual): ");
        String date = scanner.nextLine();

        final var rates = creditCardService.getCreditCardBrandsRateByDate(date.trim());

        System.out.println("Las tasas para las marcas son:");
        for(var rate : rates){
            System.out.printf("Marca (%s): %s%n", rate.brand(), rate.rate());
        }
    }

}
