package com.senlainc.dtalyanin;

import com.senlainc.dtalyanin.controller.InputController;
import com.senlainc.dtalyanin.dao.CardFileDAO;
import com.senlainc.dtalyanin.exceptions.FileException;
import com.senlainc.dtalyanin.exceptions.ParsingValueException;
import com.senlainc.dtalyanin.model.Atm;
import com.senlainc.dtalyanin.model.Operation;
import com.senlainc.dtalyanin.model.enums.CardOperation;
import com.senlainc.dtalyanin.model.enums.ContinueChoice;
import com.senlainc.dtalyanin.model.enums.LoginOperation;
import com.senlainc.dtalyanin.services.AtmService;
import com.senlainc.dtalyanin.utils.Constants;
import com.senlainc.dtalyanin.utils.readers.AtmReader;
import com.senlainc.dtalyanin.utils.writers.AtmFileWriter;
import com.senlainc.dtalyanin.utils.writers.CardsFileWriter;
import com.senlainc.dtalyanin.utils.writers.FileWriter;
import com.senlainc.dtalyanin.viewer.ClientViewer;

public class Runner {
    public static void main(String[] args) {

        ClientViewer viewer = new ClientViewer();
        try {
            Atm atm = AtmReader.getAtm();
            CardFileDAO dao = new CardFileDAO();
            AtmService service = new AtmService(atm, dao);

            try (InputController controller = new InputController(viewer)) {
                int choiceEnterCardNumber;
                int rangeOfContinueChoices = ContinueChoice.values().length;
                do {
                    Operation loginOperation = logIn(controller, service, viewer);
                    viewer.showOperationResult(loginOperation);
                    if (loginOperation.isSuccessful()) {
                        doOperationsWithCard(controller, service, viewer);
                        break;
                    }
                    viewer.showContinueMenu();
                    choiceEnterCardNumber = controller.getOperation(rangeOfContinueChoices);
                } while (choiceEnterCardNumber != rangeOfContinueChoices);
                viewer.showWorksEnd();
                saveInFiles(service, viewer, atm);
            }
        } catch (ParsingValueException | FileException e) {
            viewer.showException(e);
        }
    }

    /**
     * ???????????????????????? ???????????????? ?????????????? ?????????? ?? ?????????????????? ?????????????? ?? ?????????????????? ?? ?????? ?????? ???? ??????????????????????????
     */
    private static Operation logInByCardNumber(AtmService service, String cardNumber) {
        Operation operation;
        if (service.isCardWithNumberExist(cardNumber)) {
            if (service.isCardNotBlocked()) {
                operation = new Operation(LoginOperation.ENTER_CARD_NUMBER, cardNumber);
            } else {
                operation = new Operation(LoginOperation.BLOCKED, cardNumber, "?????????? ?????????????????????????? ?? ?????????????????? ??????????");
            }
        } else {
            operation = new Operation(LoginOperation.ENTER_CARD_NUMBER, cardNumber, "?????????? ?? ?????????????????? ?????????????? ???? ?????????????? ?? ??????????????");
        }
        return operation;
    }

    /**
     * ???????????????????????? ?????????????????????? ???? ???????????? ?????????? ?? PIN
     */
    private static Operation logIn(InputController controller, AtmService service, ClientViewer viewer) {
        String cardNumber = controller.getCardNumber();
        Operation operation = logInByCardNumber(service, cardNumber);
        if (operation.isSuccessful()) {
            int attemptsToEnterPin = 0;
            int rangeOfContinueChoices = ContinueChoice.values().length;
            String pin;
            int choiceEnterPin = 1;
            do {
                pin = controller.getPin();
                if (service.isPinCorrect(pin)) {
                    break;
                } else {
                    attemptsToEnterPin++;
                }
                if (attemptsToEnterPin != Constants.MAX_ATTEMPTS_TO_ENTER_PIN) {
                    viewer.showIncorrectPin(attemptsToEnterPin);
                    viewer.showContinueMenu();
                    choiceEnterPin = controller.getOperation(rangeOfContinueChoices);
                }
            }
            while (attemptsToEnterPin < Constants.MAX_ATTEMPTS_TO_ENTER_PIN && choiceEnterPin != rangeOfContinueChoices);
            if (attemptsToEnterPin == Constants.MAX_ATTEMPTS_TO_ENTER_PIN) {
                service.blockCard();
                operation = new Operation(LoginOperation.ENTER_PIN, cardNumber, "PIN ???????????? ?????????????????????? 3 ???????? - ?????????? ??????????????????????????");
            } else if (choiceEnterPin == rangeOfContinueChoices) {
                operation = new Operation(LoginOperation.ENTER_PIN, cardNumber, "???? ???????????? ???????????????????? PIN");
            }
        }
        return operation;
    }

    /**
     * ?????????? ???????????????? ?????? ????????????????????
     */
    private static void doOperationsWithCard(InputController controller, AtmService service, ClientViewer viewer) {
        int choiceOperation;
        int rangeOfContinueChoices = ContinueChoice.values().length;
        do {
            executeCertainOperation(controller, service, viewer);
            viewer.showContinueMenu();
            choiceOperation = controller.getOperation(rangeOfContinueChoices);
        }
        while (choiceOperation != rangeOfContinueChoices);
    }

    /**
     * ???????????????????? ???????????????? ???? ????????????
     */
    private static void executeCertainOperation(InputController controller, AtmService service, ClientViewer viewer) {
        CardOperation cardOperation;
        viewer.showOperationMenu();
        int choice = controller.getOperation(CardOperation.values().length);
        cardOperation = CardOperation.values()[choice - 1];
        switch (cardOperation) {
            case SHOW_BALANCE:
                viewer.showBalance(service.getBalance());
                break;
            case REPLENISHMENT:
                int replenishmentAmount = controller.getCashAmount();
                viewer.showOperationResult(service.replenishBalance(replenishmentAmount));
                break;
            case WITHDRAW:
                int withdrawAmount = controller.getCashAmount();
                viewer.showOperationResult(service.withdrawMoney(withdrawAmount));
                break;
        }
    }

    /**
     * ???????????????????? ???????????? ?? ?????????? ???? ????????????????????
     */
    private static void saveInFiles(AtmService service, ClientViewer viewer, Atm atm) throws FileException {
        if (service.isAtmModified()) {
            FileWriter atmWriter = new AtmFileWriter(Constants.ATM_FILE_NAME, atm);
            atmWriter.save();
            viewer.showSuccessfulSave("ATM");
        }
        if (service.isDaoModified()) {
            FileWriter cardsWriter = new CardsFileWriter(Constants.CARDS_FILE_NAME, service.getAllCards());
            cardsWriter.save();
            viewer.showSuccessfulSave("????????");
        }
    }
}
