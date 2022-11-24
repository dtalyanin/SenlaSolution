package com.senlainc.dtalyanin.model.enums;

/**
 * Перечисление возможных операций со счетом
 */
public enum CardOperation implements OperationNotificator {
    SHOW_BALANCE("Посмотреть баланс"),
    REPLENISHMENT("Пополнение счета"),
    WITHDRAW("Снятие денежных средств со счета");

    private final String operationMessage;

    CardOperation(String operationMessage) {
        this.operationMessage = operationMessage;
    }


    @Override
    public String getMessage() {
        return operationMessage;
    }
}
