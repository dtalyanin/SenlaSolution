package com.senlainc.dtalyanin.utils;

/**
 * Класс проверки соответствия введенных данных регулярному выражению
 */
public class CardMatcher {
    private final static String CARD_REG = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
    private final static String PIN_REG = "\\d{4}";

    public CardMatcher() {
    }

    /**
     * Проверяет введенный номер карты на соответствие заданному формату
     */
    public static boolean isCorrectCardNumberFormat(String number) {
        return number.matches(CARD_REG);
    }

    /**
     * Проверяет введенный PIN на соответствие заданному формату
     */
    public static boolean isCorrectPinFormat(String pin) {
        return pin.matches(PIN_REG);
    }
}
