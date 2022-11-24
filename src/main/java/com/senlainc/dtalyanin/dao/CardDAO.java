package com.senlainc.dtalyanin.dao;

import com.senlainc.dtalyanin.model.Card;

import java.util.List;

/**
 * Интерфейс обращения к хранилищу для получения данных карт
 */
public interface CardDAO {
    /**
     * Получить карту по её номеру
     */
    Card getCardByNumber(String number);

    /**
     * Получить список всех имеющихся карт
     */
    List<Card> getAllCards();
}
