package com.senlainc.dtalyanin.model.enums;

/**
 * Перечисление видов операций при авторизации
 */
public enum LoginOperation implements OperationNotificator {
    ENTER_CARD_NUMBER("Ввод номера карты"),
    ENTER_PIN("Ввод PIN"),
    BLOCKED("Проверка блокировки карты");

    private final String operationMessage;

    LoginOperation(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    @Override
    public String getMessage() {
        return operationMessage;
    }
}
