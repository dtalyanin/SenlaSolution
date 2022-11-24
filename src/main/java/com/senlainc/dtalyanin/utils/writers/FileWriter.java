package com.senlainc.dtalyanin.utils.writers;

import com.senlainc.dtalyanin.exceptions.FileException;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Класс записи данных в файл
 */
public abstract class FileWriter {
    protected final String pathToFile;
    protected final String valuesSeparator;

    public FileWriter(String pathToFile) {
        this.pathToFile = pathToFile;
        this.valuesSeparator = " ";
    }

    /**
     * Метод сохранения данных в файл
     */
    public void save() throws FileException {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(pathToFile))) {
            saveData(writer);
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    /**
     * Метод сохранения конкретных данных в файл
     */
    protected abstract void saveData(BufferedWriter writer) throws IOException;
}
