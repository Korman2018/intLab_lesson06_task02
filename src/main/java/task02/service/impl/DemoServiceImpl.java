package task02.service.impl;

import task02.generator.TransferGenerator;
import task02.service.IAccountService;
import task02.service.IDemoService;

import java.util.concurrent.ExecutorService;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static task02.utils.TransactionsConstants.*;

public class DemoServiceImpl implements IDemoService {

    private IAccountService accountService;

    public DemoServiceImpl() {
        accountService = new AccountServiceImpl();
    }

    public void runDemo(int numberOfAccounts) {
        if (numberOfAccounts < 2) {
            LOGGER.info("Not enough accounts:{} ! I can't complete {} successful transactions",
                    numberOfAccounts, MAX_NUMBER_OF_TRANSACTIONS);
            return;
        }

        accountService.generateAccountsFiles(numberOfAccounts);
        accountService.printAllAccountsInfo();
        LOGGER.info("Total balance before transferring:{}", accountService.getTotalSum());

        ExecutorService executorService = newFixedThreadPool(MAX_NUMBER_OF_RUN_THREADS);
        TransferGenerator transferGenerator = new TransferGenerator(accountService.getAllAccounts());

        for (int i = 0; i < MAX_NUMBER_OF_RUN_THREADS; i++) {
            executorService.execute(transferGenerator);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(AWAIT_TERMINATION_TIME, AWAIT_TERMINATION_TIME_UNIT);
        } catch (InterruptedException e) {
            LOGGER.info("Interrupt thread:{}", currentThread());
            Thread.currentThread().interrupt();
        }

        accountService.printAllAccountsInfo();
        LOGGER.info("Total balance after transferring:{}", accountService.getTotalSum());
    }
}
