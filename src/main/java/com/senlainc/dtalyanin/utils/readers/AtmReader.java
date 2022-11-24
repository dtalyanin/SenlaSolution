package com.senlainc.dtalyanin.utils.readers;

import com.senlainc.dtalyanin.exceptions.FileException;
import com.senlainc.dtalyanin.exceptions.ParsingValueException;
import com.senlainc.dtalyanin.model.Atm;
import com.senlainc.dtalyanin.utils.Constants;

import java.io.*;

/**
 * Класс для инициализации АТМ из данных файла
 */
public class AtmReader {

    private static final int CURRENT_AMOUNT_INDEX = 0;
    private static final int MAX_AMOUNT_INDEX = 1;
    private static final int MAX_REPLENISHMENT_INDEX = 2;
    private static final int VALUES_AMOUNT = 3;

    private static final String DOWNLOAD_DATA_EXC_MESSAGE = "загрузка данных ATM";

    public AtmReader() {
    }

    /**
     * Чтение данных из файла и инициализация АТМ
     */
    public static Atm getAtm() throws FileException, ParsingValueException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.ATM_FILE_NAME))) {
            String atmStrValues = reader.readLine();
            if (atmStrValues == null) {
                throw new ParsingValueException(DOWNLOAD_DATA_EXC_MESSAGE, "нет данных для запуска АТМ");
            }
            return parsingValuesAngGetAtm(atmStrValues.trim().split(Constants.SPACE_REG));
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    /**
     * Анализ и инициализация АТМ значениями полученных полей
     */
    private static Atm parsingValuesAngGetAtm(String[] strValues) throws ParsingValueException {
        if (strValues.length < VALUES_AMOUNT) {
            throw new ParsingValueException(DOWNLOAD_DATA_EXC_MESSAGE, "недостаточно данных для инициализации");
        }
        String currentAmountStr = strValues[CURRENT_AMOUNT_INDEX];
        String maxAmountStr = strValues[MAX_AMOUNT_INDEX];
        String maxReplenishmentStr = strValues[MAX_REPLENISHMENT_INDEX];
        try {
            int currentAmount = Integer.parseInt(currentAmountStr);
            int maxAmount = Integer.parseInt(maxAmountStr);
            int maxReplenishment = Integer.parseInt(maxReplenishmentStr);
            return new Atm(currentAmount, maxAmount, maxReplenishment);
        } catch (NumberFormatException e) {
            throw new ParsingValueException(DOWNLOAD_DATA_EXC_MESSAGE, "неверное значение баланса " + e.getMessage());
        }
    }
}
