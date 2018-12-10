package task02.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionsConstants {
    // common section
    public static final Logger LOGGER = LoggerFactory.getLogger("TransactionService");
    public static final int NUMBER_OF_ACCOUNTS = 10;
    public static final int AWAIT_TERMINATION_TIME = 5;
    public static final TimeUnit AWAIT_TERMINATION_TIME_UNIT = TimeUnit.MINUTES;
    public static final int MULTIPLIER = 100;
    public static final AtomicInteger TRANSACTION_COUNTER = new AtomicInteger(0);

    // transfer section
    public static final int MAX_NUMBER_OF_TRANSACTIONS = 1000;
    public static final int MAX_NUMBER_OF_RUN_THREADS = 20;
    public static final int MIN_AMOUNT = 1;
    public static final int MAX_AMOUNT = 10;
    public static final int MIN_TRANSACTION_SLEEP_TIME_MS = 100;
    public static final int MAX_TRANSACTION_SLEEP_TIME_MS = 500;

    // files section
    public static final String ACCOUNTS_FILE_EXTENSION = ".acc";
    public static final String ACCOUNTS_PATH = ".//src//main//resources//accounts//";
    public static final String IO_ERROR_MESSAGE = "I/O error";

    // generator section
    public static final String[] NAMES = {
            "Ivan",
            "Petr",
            "Sidor",
            "Egor",
            "Anton",
            "Konstantin",
            "Timur",
            "Leonid",
            "Marat",
            "Umedjon",
            "Victor",
            "Tolik",
            "Alexandr",
            "Vyacheslav"
    };
    public static final long MIN_BALANCE = 0;
    public static final long MAX_BALANCE = 100;

    private TransactionsConstants() {
    }
}
