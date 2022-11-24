package com.senlainc.dtalyanin.utils.writers;

import com.senlainc.dtalyanin.model.Atm;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Класс записи данных состояния АТМ в файл
 */
public class AtmFileWriter extends FileWriter {
    private final Atm atm;

    public AtmFileWriter(String pathToFile, Atm atm) {
        super(pathToFile);
        this.atm = atm;
    }

    @Override
    protected void saveData(BufferedWriter writer) throws IOException {
        writer.write(atm.getCurrentAmount() + valuesSeparator + atm.getMaxAmount() + valuesSeparator + atm.getMaxReplenishment());
    }
}
