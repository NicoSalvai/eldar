package com.salvai.eldar;

public class Main {
    public static void main(String[] args) {
        final var alertService = new AlertService();
        final var expenses = new Double[]{50d, 10d, 20d, 50d, 70d, 80d, 50d, 120d, 10d, 20d, 30d, 40d, 20d, 100d};
        final var days = 4;

        System.out.printf("Recibirá %s alerta/s", alertService.amountOfAlertsForExpensesAndDays(expenses,days));
    }

}