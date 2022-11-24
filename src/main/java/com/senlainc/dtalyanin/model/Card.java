package com.senlainc.dtalyanin.model;

import com.senlainc.dtalyanin.utils.MoneyHelper;

import java.time.LocalDateTime;

/**
 * Класс банковской карты с привязанным счетом
 */
public class Card {
    private final String number;
    private final String pin;
    private int balance;
    private LocalDateTime lockDate;

    public Card(String number, String pin, int balance) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }

    public Card(String number, String pin, int balance, LocalDateTime lockDate) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
        this.lockDate = lockDate;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public LocalDateTime getLockDate() {
        return lockDate;
    }

    public void setLockDate(LocalDateTime lockDate) {
        this.lockDate = lockDate;
    }

    public void replenishBalance(int replenishmentAmount) {
        balance += replenishmentAmount;
    }

    public void withdrawMoney(int withdrawalAmount) {
        balance -= withdrawalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return number != null ? number.equals(card.number) : card.number == null;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Карта № " + number + ", PIN - " + pin + ", баланс - " + MoneyHelper.getMoneyRepresentation(balance);
    }
}
