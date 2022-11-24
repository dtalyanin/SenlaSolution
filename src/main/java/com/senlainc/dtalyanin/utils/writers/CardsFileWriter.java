package com.senlainc.dtalyanin.utils.writers;

import com.senlainc.dtalyanin.model.Card;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Класс записи данных карт в файл
 */
public class CardsFileWriter extends FileWriter {
    List<Card> cards;

    public CardsFileWriter(String pathToFile, List<Card> cards) {
        super(pathToFile);
        this.cards = cards;
    }

    @Override
    protected void saveData(BufferedWriter writer) throws IOException {
        for (Card card: cards) {
            String strCard = card.getNumber() + valuesSeparator + card.getPin() + valuesSeparator + card.getBalance() +
                    (card.getLockDate() != null ? valuesSeparator + card.getLockDate() : "");
            writer.write(strCard);
            writer.newLine();
        }
    }
}
