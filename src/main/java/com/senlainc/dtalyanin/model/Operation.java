package com.senlainc.dtalyanin.model;

import com.senlainc.dtalyanin.model.enums.OperationNotificator;

/**
 * Класс представления результата выполнения операции с картой
 */
public class Operation {
    private OperationNotificator type;
    private String cardNumber;
    private String errorMessage;

    public Operation(OperationNotificator type, String cardNumber) {
        this.type = type;
        this.cardNumber = cardNumber;
    }

    public Operation(OperationNotificator type, String cardNumber, String errorMessage) {
        this.type = type;
        this.cardNumber = cardNumber;
        this.errorMessage = errorMessage;
    }

    public OperationNotificator getType() {
        return type;
    }

    public void setType(OperationNotificator type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return errorMessage == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (type != null ? !type.equals(operation.type) : operation.type != null) return false;
        if (cardNumber != null ? !cardNumber.equals(operation.cardNumber) : operation.cardNumber != null) return false;
        return errorMessage != null ? errorMessage.equals(operation.errorMessage) : operation.errorMessage == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return type.getMessage() + " - операция " +
                (isSuccessful() ? "завершена успешно" : "не выполнена: " + errorMessage);
    }
}
