package com.senlainc.dtalyanin.model;

import com.senlainc.dtalyanin.utils.MoneyHelper;

/**
 * Класс представления внутреннего состояния банкомата
 */
public class Atm {
    private final int maxAmount;
    private final int maxReplenishment;
    private int currentAmount;

    public Atm( int currentAmount, int maxAmount, int maxReplenishment) {
        this.currentAmount = currentAmount;
        this.maxAmount = maxAmount;
        this.maxReplenishment = maxReplenishment;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getMaxReplenishment() {
        return maxReplenishment;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void depositMoney(int amount) {
        currentAmount += amount;
    }

    public void giveMoney(int amount) {
        currentAmount -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Atm atm = (Atm) o;

        if (maxAmount != atm.maxAmount) return false;
        if (maxReplenishment != atm.maxReplenishment) return false;
        return currentAmount == atm.currentAmount;
    }

    @Override
    public int hashCode() {
        int result = maxAmount;
        result = 31 * result + maxReplenishment;
        result = 31 * result + currentAmount;
        return result;
    }

    @Override
    public String toString() {
        return "ATM: текущие количество - " + MoneyHelper.getMoneyRepresentation(currentAmount) +
                " BYN, максимальная вместимость - " + MoneyHelper.getMoneyRepresentation(maxAmount) +
                " BYN, максимально возможное пополнение - " + MoneyHelper.getMoneyRepresentation(maxReplenishment);
    }
}
