package com.senlainc.dtalyanin.exceptions;

/**
 * Исключение при получении данных из строкового значения
 */
public class ParsingValueException extends Exception {
    private String operation;

    public ParsingValueException(String operation, String message) {
        super(message);
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Ошибка операции '" + operation + "', причина: " + getMessage();
    }
}
