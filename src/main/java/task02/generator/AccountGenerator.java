package task02.generator;

import task02.model.Account;

import static java.util.concurrent.ThreadLocalRandom.current;
import static task02.utils.TransactionsConstants.*;

public class AccountGenerator {
    private static int id;

    private AccountGenerator() {
    }

    public static Account generate() {
        return new Account(generateId(), generateName(), generateBalance());
    }

    // generate men only )
    private static String generateName() {
        String name = NAMES[current().nextInt(NAMES.length)];
        String lastName = NAMES[current().nextInt(NAMES.length)] + "ov";
        return name + " " + lastName;
    }

    private static long generateBalance() {
        return current().nextLong(MIN_BALANCE, MAX_BALANCE) * MULTIPLIER;
    }

    private static int generateId() {
        return id++;
    }
}