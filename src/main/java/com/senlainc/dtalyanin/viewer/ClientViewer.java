package com.senlainc.dtalyanin.viewer;

import com.senlainc.dtalyanin.model.enums.CardOperation;
import com.senlainc.dtalyanin.model.enums.ContinueChoice;
import com.senlainc.dtalyanin.model.Operation;
import com.senlainc.dtalyanin.utils.Constants;
import com.senlainc.dtalyanin.utils.MoneyHelper;

/**
 * Класс вывода данных в консоль
 */
public class ClientViewer {

    public void showIncorrectInputMessage() {
        System.out.println("Неверный формат ввода, попробуйте ещё раз:");
    }

    public void showInputMessage(String message) {
        System.out.println("Введите " + message + ":");
    }

    public void showException(Exception exception) {
        System.err.println(exception);
    }

    public void showIncorrectPin(int attemptsLeft) {
        System.out.println("Введен неправильный PIN. Попробуйте ещё раз (осталось попыток: " +
                (Constants.MAX_ATTEMPTS_TO_ENTER_PIN - attemptsLeft) + ")");
    }

    public void showBalance(int balance) {
        System.out.println("На счету " + MoneyHelper.getMoneyRepresentation(balance) + " BYN");
    }

    public void showOperationMenu() {
        CardOperation[] types = CardOperation.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i].getMessage());
        }
    }

    public void showContinueMenu() {
        ContinueChoice[] types = ContinueChoice.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i].getMessage());
        }
    }

    public void showOperationResult(Operation operation) {
        System.out.println(operation);
    }

    public void showSuccessfulSave(String target) {
        System.out.println("Данные " + target + " обновлены успешно");
    }

    public void showWorksEnd() {
        System.out.println("Работа завершена. Благодарин за использование!");
    }
}
