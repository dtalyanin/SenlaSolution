package com.senlainc.dtalyanin.model.enums;

/**
 * Перечисление вариантов продолжения работы в приложении
 */
public enum ContinueChoice implements OperationNotificator {
    CONTINUE("Продолжить работу"), EXIT("Завершить работу");

    private final String continueMessage;

    ContinueChoice(String continueMessage) {
        this.continueMessage = continueMessage;
    }

    @Override
    public String getMessage() {
        return continueMessage;
    }
}
