package task02.transaction;

import task02.model.Account;

import java.util.concurrent.locks.Lock;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.util.concurrent.ThreadLocalRandom.current;
import static task02.utils.TransactionsConstants.*;

public class Transaction {
    private Account accountOne;
    private Account accountTwo;
    private long amount;

    public Transaction(Account one, Account two, long amount) {
        this.accountOne = one;
        this.accountTwo = two;
        this.amount = amount;
    }

    public void start() throws InterruptedException {
        // do some work
        sleep(current().nextInt(MIN_TRANSACTION_SLEEP_TIME_MS, MAX_TRANSACTION_SLEEP_TIME_MS));

        long idOne = accountOne.getId();
        long idTwo = accountTwo.getId();
        Lock lockOne = accountOne.getLock();
        Lock lockTwo = accountTwo.getLock();

        // exchange locks order if needed
        if (idOne > idTwo) {
            lockTwo.lock();
            lockOne.lock();
        } else {
            lockOne.lock();
            lockTwo.lock();
        }

        try {
            long balanceOne = accountOne.getBalance();
            long newBalanceOne = balanceOne - amount;

            if (newBalanceOne < 0) {
                if (TRANSACTION_COUNTER.get() > MAX_NUMBER_OF_TRANSACTIONS) {
                    currentThread().interrupt();
                } else {
                    LOGGER.warn("not enough money on account with id:{} balance:{}, amount to charge off:{} -> cancel transfer",
                            idOne, balanceOne, amount);
                }
                return;
            }

            int numberOfCurrentCorrectTransaction = TRANSACTION_COUNTER.getAndIncrement() + 1;

            if (numberOfCurrentCorrectTransaction > MAX_NUMBER_OF_TRANSACTIONS) {
                LOGGER.debug("number of transactions exceeded", numberOfCurrentCorrectTransaction);
                currentThread().interrupt();
                return;
            }

            // transaction is OK and will be done
            long balanceTwo = accountTwo.getBalance();
            accountOne.setBalance(newBalanceOne);
            accountTwo.setBalance(balanceTwo + amount);
            LOGGER.info("Sending {} from account with id:{} to account with id:{}",
                    amount, idOne, idTwo, numberOfCurrentCorrectTransaction);
            LOGGER.debug("account {} old balance:{} new balance:{}", idOne, balanceOne, accountOne.getBalance());
            LOGGER.debug("account {} old balance:{} new balance:{}", idTwo, balanceTwo, accountTwo.getBalance());
        } finally {
            lockTwo.unlock();
            lockOne.unlock();
        }
    }
}
