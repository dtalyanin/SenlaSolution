package com.senlainc.dtalyanin.controller;

import com.senlainc.dtalyanin.utils.CardMatcher;
import com.senlainc.dtalyanin.utils.MoneyHelper;
import com.senlainc.dtalyanin.viewer.ClientViewer;

import java.util.Scanner;

/**
 * Класс для получения данных с консоли ввода от пользователя
 */
public class InputController implements AutoCloseable {
    private static final String CASH_INPUT_MESSAGE = "количество денежных средств для операции";
    private static final String CARD_NUMBER_INPUT_MESSAGE = "номер карты в формате ХХХХ-ХХХХ-ХХХХ-ХХХХ";
    private static final String PIN_INPUT_MESSAGE = "4-х значный PIN";
    private static final String OPERATION_TYPE_INPUT_MESSAGE = "номер выбранной операции от 1 до ";

    private final Scanner scanner;
    private final ClientViewer viewer;

    public InputController(ClientViewer viewer) {
        this.viewer = viewer;
        this.scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }

    /**
     * Формирует int из консоли воода
     */
    private int getInt() {
        while (!scanner.hasNextInt()) {
            viewer.showIncorrectInputMessage();
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    /**
     * Формирует double из консоли воода
     */
    private double getDouble() {
        while (!scanner.hasNextDouble()) {
            viewer.showIncorrectInputMessage();
            scanner.next();
        }
        return scanner.nextDouble();
    }

    /**
     * Формирует String из консоли воода
     */
    private String getString(String message) {
        viewer.showInputMessage(message);
        String result;
        do {
            result = scanner.nextLine();
        }
        while (result.isEmpty());
        return result;
    }

    /**
     * Формирует размер денежных средств для операции
     */
    public int getCashAmount() {
        double value;
        boolean verified;
        do {
            viewer.showInputMessage(CASH_INPUT_MESSAGE);
            value = getDouble();
            verified = MoneyHelper.verifyRightMoneyFormat(value);
        } while (!verified);
        return (int) (value * 100);
    }

    /**
     * Формирует значение выбора в зависимости от переданного диапазона допустимых значений
     */
    public int getOperation(int maxRange) {
        int choice;
        do {
            viewer.showInputMessage(OPERATION_TYPE_INPUT_MESSAGE + maxRange);
            choice = getInt();
        }
        while (choice < 1 || choice > maxRange);
        return choice;
    }

    /**
     * Формирует номер карты в заданном формате
     */
    public String getCardNumber() {
        String cardNumber;
        do {
            cardNumber = getString(CARD_NUMBER_INPUT_MESSAGE);
        }
        while (!CardMatcher.isCorrectCardNumberFormat(cardNumber));
        return cardNumber;
    }

    /**
     * Формирует PIN в заданном формате
     */
    public String getPin() {
        String pin;
        do {
            pin = getString(PIN_INPUT_MESSAGE);
        }
        while (!CardMatcher.isCorrectPinFormat(pin));
        return pin;
    }

    /**
     * Метод закрытия открытых во время использования ресурсов
     */
    @Override
    public void close() {
        scanner.close();
    }
}
