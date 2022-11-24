package com.senlainc.dtalyanin.dao;

import com.senlainc.dtalyanin.exceptions.FileException;
import com.senlainc.dtalyanin.exceptions.ParsingValueException;
import com.senlainc.dtalyanin.model.Card;
import com.senlainc.dtalyanin.utils.readers.CardReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Класс получения данных карт из текстового файла
 */
public class CardFileDAO implements CardDAO {
    private Map<String, Card> cards;

    public CardFileDAO() throws ParsingValueException, FileException {
        this.cards = CardReader.loadCardsFromFile();
    }

    /**
     * Получить карту по её номеру
     */
    @Override
    public Card getCardByNumber(String number) {
        return cards.get(number);
    }

    /**
     * Получить список всех имеющихся карт
     */
    @Override
    public List<Card> getAllCards() {
        return new ArrayList<>(cards.values());
    }
}
