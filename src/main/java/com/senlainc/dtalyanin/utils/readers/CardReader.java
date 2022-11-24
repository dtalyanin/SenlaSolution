package com.senlainc.dtalyanin.utils.readers;

import com.senlainc.dtalyanin.exceptions.FileException;
import com.senlainc.dtalyanin.exceptions.ParsingValueException;
import com.senlainc.dtalyanin.model.Card;
import com.senlainc.dtalyanin.utils.CardMatcher;
import com.senlainc.dtalyanin.utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

/**
 * Класс чтения данных карт из данных файла
 */
public class CardReader {
    private static final int CARD_NUMBER_INDEX = 0;
    private static final int PIN_INDEX = 1;
    private static final int BALANCE_INDEX = 2;
    private static final int BLOCKED_TIME_INDEX = 3;
    private static final int FIELDS_AMOUNT = 4;

    private final static String ADD_CARD_EXC_MESSAGE = "добавление карты ";
    private static final String CARD_BALANCE_EXC_MESSAGE = "неверное значение баланса ";
    private static final String BALANCE_OPERATION_EXC = "получение баланса карты ";
    private static final String NO_DATA_IN_FILE = "файл пустой и не содержит данных";

    /**
     * Получение данных карт, сохраненных как ключ(номер карты) - значение(карта с данными)
     */
    public static HashMap<String, Card> loadCardsFromFile() throws ParsingValueException, FileException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.CARDS_FILE_NAME))) {
            HashMap<String, Card> cards = new HashMap<>();
            String data;
            while ((data = reader.readLine()) != null) {
                data = data.trim();
                if (data.length() != 0) {
                    Card card = getCardFromLine(data.split(Constants.SPACE_REG));
                    if (cards.putIfAbsent(card.getNumber(), card) != null) {
                        throw new ParsingValueException(ADD_CARD_EXC_MESSAGE + card.getNumber(), "дубликат данных");
                    }
                }
            }
            if (cards.size() == 0) {
                throw new FileException(NO_DATA_IN_FILE);
            }
            return cards;
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    /**
     * Анализ и инициализация карты значениями полученных полей
     */
    private static Card getCardFromLine(String[] strValues) throws ParsingValueException {
        String cardNumber = strValues[CARD_NUMBER_INDEX];
        String pin = strValues[PIN_INDEX];
        String strBalance = strValues[BALANCE_INDEX];
        if (!CardMatcher.isCorrectCardNumberFormat(cardNumber)) {
            throw new ParsingValueException(ADD_CARD_EXC_MESSAGE + cardNumber, "не сооветсвует формату ХХХХ-ХХХХ-ХХХХ-ХХХХ");
        }
        if (!CardMatcher.isCorrectPinFormat(pin)) {
            throw new ParsingValueException(ADD_CARD_EXC_MESSAGE + cardNumber, "PIN должен содержать 4 цифры");
        }
        int balance = getBalanceFromString(strBalance, cardNumber);
        Card card = new Card(cardNumber, pin, balance);
        checkBlockedField(strValues, card);
        return card;
    }

    /**
     * Получает значение баланса
     */
    private static int getBalanceFromString(String strBalance, String cardNumber) throws ParsingValueException {
        try {
            int balance = Integer.parseInt(strBalance);
            if (balance < 0) {
                throw new ParsingValueException(BALANCE_OPERATION_EXC + cardNumber,
                        CARD_BALANCE_EXC_MESSAGE + strBalance);
            }
            return balance;
        } catch (NumberFormatException e) {
            throw new ParsingValueException(BALANCE_OPERATION_EXC + cardNumber,
                    CARD_BALANCE_EXC_MESSAGE + strBalance);
        }
    }

    /**
     * Проверяет наличие в значениях поля блокировки и в случае присутствия устанавливает данное поле для карты
     */
    private static void checkBlockedField(String[] strValues, Card card) throws ParsingValueException {
        if (strValues.length == FIELDS_AMOUNT) {
            String strBlockedTime = strValues[BLOCKED_TIME_INDEX];
            try {
                LocalDateTime blockedTime = LocalDateTime.parse(strBlockedTime);
                card.setLockDate(blockedTime);
            } catch (DateTimeParseException e) {
                throw new ParsingValueException(ADD_CARD_EXC_MESSAGE + card.getNumber(),
                        "некорректное время " + strBlockedTime);
            }
        }
    }
}
