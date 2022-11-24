package com.senlainc.dtalyanin.exceptions;

/**
 * Исключение при работе с файловой системой
 */
public class FileException extends Exception {
    public FileException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Ошибка доступа к файлу: " + getMessage();
    }
}
