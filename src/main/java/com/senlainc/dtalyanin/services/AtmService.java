package com.senlainc.dtalyanin.services;

import com.senlainc.dtalyanin.dao.CardDAO;
import com.senlainc.dtalyanin.exceptions.FileException;
import com.senlainc.dtalyanin.exceptions.ParsingValueException;
import com.senlainc.dtalyanin.model.Atm;
import com.senlainc.dtalyanin.model.Card;
import com.senlainc.dtalyanin.model.Operation;
import com.senlainc.dtalyanin.model.enums.CardOperation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис по работе с картами в хранилище
 */
public class AtmService {
    private static final int LOCK_HOURS_DURATION = 24;

    private final Atm atm;
    private final CardDAO dao;
    private Card usedCard;
    private boolean atmModified;
    private boolean daoModified;

    public AtmService(Atm atm, CardDAO dao) throws ParsingValueException, FileException {
        this.atm = atm;
        this.dao = dao;
        this.atmModified = false;
        this.daoModified = false;
    }

    /**
     * Получить список всех имеющихся карт из хранилища
     */
    public List<Card> getAllCards() {
        return dao.getAllCards();
    }

    /**
     * Проверяет наличие карты с указанным номером и в случае успеха устанавливает найденную карту как используемую
     */
    public boolean isCardWithNumberExist(String cardNumber) {
        usedCard = dao.getCardByNumber(cardNumber);
        return usedCard != null;
    }

    /**
     * Проверяет наличие блокировки карты
     */
    public boolean isCardNotBlocked() {
        if (usedCard.getLockDate() != null) {
            verifyUnblockTime();
        }
        return usedCard.getLockDate() == null;
    }

    /**
     * Возвращает соответсвие введенного PIN установленному для карты
     */
    public boolean isPinCorrect(String pin) {
        return usedCard.getPin().equals(pin);
    }

    /**
     * Блокирует карту на текущее время
     */
    public void blockCard() {
        usedCard.setLockDate(LocalDateTime.now());
        daoModified = true;
    }

    /**
     * Получает баланс используемой карты
     */
    public int getBalance() {
        return usedCard.getBalance();
    }

    /**
     * Производит пополнение денежных средств на счету карты путем внесения в АТМ
     */
    public Operation replenishBalance(int replenishmentAmount) {
        Operation operation = new Operation(CardOperation.REPLENISHMENT, usedCard.getNumber());
        if (replenishmentAmount > atm.getMaxReplenishment()) {
            operation.setErrorMessage("сумма пополнения больше допустимой (" + atm.getMaxReplenishment() + ")");
        } else if ((atm.getCurrentAmount() + replenishmentAmount) > atm.getMaxAmount()) {
            operation.setErrorMessage("превышена вместимость банкомата");
        } else {
            usedCard.replenishBalance(replenishmentAmount);
            atm.depositMoney(replenishmentAmount);
            daoModified = true;
            atmModified = true;
        }
        return operation;
    }

    /**
     * Производит списание денежных средств со счета путем выдачи наличных в АТМ
     */
    public Operation withdrawMoney(int withdrawAmount) {
        Operation operation = new Operation(CardOperation.WITHDRAW, usedCard.getNumber());
        if (atm.getCurrentAmount() < withdrawAmount) {
            operation.setErrorMessage("в банкомате недостаточно денег");
        } else if (usedCard.getBalance() < withdrawAmount) {
            operation.setErrorMessage("недостаточно денег на счету");
        } else {
            usedCard.withdrawMoney(withdrawAmount);
            atm.giveMoney(withdrawAmount);
            daoModified = true;
            atmModified = true;
        }
        return operation;
    }

    /**
     * Проверяет истечение блокировки карты
     */
    private void verifyUnblockTime() {
        if (usedCard.getLockDate().plusHours(LOCK_HOURS_DURATION).isBefore(LocalDateTime.now())) {
            usedCard.setLockDate(null);
            daoModified = true;
        }
    }

    public boolean isAtmModified() {
        return atmModified;
    }

    public boolean isDaoModified() {
        return daoModified;
    }
}
