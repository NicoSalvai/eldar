package com.salvai.eldar;

public record Expense(Integer day, Double value) implements Comparable<Expense>{

    @Override
    public int compareTo(Expense o) {
        final var valueComparation = this.value.compareTo(o.value);
        if (valueComparation == 0){
            return this.day.compareTo(o.day);
        }
        return valueComparation;
    }
}