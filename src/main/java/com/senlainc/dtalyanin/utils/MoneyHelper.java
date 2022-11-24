package com.senlainc.dtalyanin.utils;

import java.math.BigDecimal;

/**
 * Утилитный класс для работы с финансовой величиной BYN
 */
public class MoneyHelper {
    private static final int COINS_AMOUNT = 100;
    private static final int COINS_NUMBERS = 2;

    /**
     * Конвертирует целое представление BYN в строковое представление РУБЛИ.КОПЕЙКИ
     */
    public static String getMoneyRepresentation(int balance) {
        int rubles = balance / COINS_AMOUNT;
        int coins = balance % COINS_AMOUNT;
        return String.format("%d.%02d", rubles, coins);
    }

    /**
     * Проверяет соответствие double требованию наличия у величины дробной части не более сотых долей
     */
    public static boolean verifyRightMoneyFormat(double value) {
        return BigDecimal.valueOf(value).scale() <= COINS_NUMBERS;
    }
}
