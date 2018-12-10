package task02.generator;

import task02.model.Account;
import task02.transaction.Transaction;

import java.util.List;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;
import static task02.utils.TransactionsConstants.*;

public class TransferGenerator implements Runnable {
    private final List<Account> accountList;
    private final int numberOfAccounts;

    public TransferGenerator(List<Account> accountList) {
        this.accountList = accountList;
        numberOfAccounts = accountList.size();
    }

    @Override
    public void run() {
        while (!currentThread().isInterrupted() && TRANSACTION_COUNTER.get() <= MAX_NUMBER_OF_TRANSACTIONS) {
            int generateIndexOne;
            int generateIndexTwo;
            int generateAmount = current().nextInt(MIN_AMOUNT, MAX_AMOUNT) * MULTIPLIER;

            while (true) {
                generateIndexOne = current().nextInt(numberOfAccounts);
                generateIndexTwo = current().nextInt(numberOfAccounts);
                if (generateIndexOne != generateIndexTwo) {
                    break;
                }
                LOGGER.debug("Accounts with same id:{} -> cancel transfer", generateIndexOne);
            }

            try {
                new Transaction(
                        accountList.get(generateIndexOne),
                        accountList.get(generateIndexTwo),
                        generateAmount
                ).start();
            } catch (InterruptedException e) {
                LOGGER.debug("Thread:{} is interrupted", currentThread(), e);
                currentThread().interrupt();
            }
        }
    }
}
