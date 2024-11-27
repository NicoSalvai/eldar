package com.salvai.eldar;

import java.util.*;

public class AlertService {

    public Integer amountOfAlertsForExpensesAndDays(Double[] expensesValues, Integer days){
        final var expenses = new Expense[expensesValues.length];
        for (int i = 0; i < expensesValues.length; i++) {
            expenses[i] = new Expense(i, expensesValues[i]);
        }

        final SortedSet<Expense> set = new TreeSet<>();
        for (int i = 0; i < days; i++) {
            set.add(expenses[i]);
        }

        var alertCount = 0;
        for (int i = days; i < expenses.length; i++) {
            final var todayValue = expenses[i];
            set.add(todayValue);

            final var median = getMedian(set, days);

            if(todayValue.value() >= 2d * median) {
                alertCount++;
            }

            final var valueToRemove = expenses[i-days];
            set.remove(valueToRemove);
        }

        return alertCount;
    }

    private Double getMedian(SortedSet<Expense> set, Integer size){
        if (size % 2 == 0){
            return (set.stream().skip((size/2)-1).findFirst().get().value() + set.stream().skip((size/2)).findFirst().get().value()) / 2;
        }
        return set.stream().skip(size/2).findFirst().get().value();
    }
}
