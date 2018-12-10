package task02.repository.impl;

import task02.exception.InvalidObjectInFileException;
import task02.model.Account;
import task02.repository.IAccountDAO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static task02.utils.TransactionsConstants.*;

public class AccountDAOImpl implements IAccountDAO<Account> {
    @Override
    public List<Account> getAll() {
        try (Stream<Path> stream = Files.walk(Paths.get(ACCOUNTS_PATH))) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(p -> p.getName().endsWith(ACCOUNTS_FILE_EXTENSION))
                    .map(this::getDataFromFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error(IO_ERROR_MESSAGE, e);
        }
        return new ArrayList<>();
    }

    @Override
    public void add(Account account) {
        String filename = String.format("%s%010d%s", ACCOUNTS_PATH, account.getId(), ACCOUNTS_FILE_EXTENSION);
        File file = new File(filename);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(account);
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} not found(method add)", filename, e);
        } catch (IOException e) {
            LOGGER.error(IO_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void removeAll() {
        try (Stream<Path> stream = Files.walk(Paths.get(ACCOUNTS_PATH))) {
            stream
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(p -> p.getName().endsWith(ACCOUNTS_FILE_EXTENSION))
                    .forEach(File::delete);
        } catch (IOException e) {
            LOGGER.error(IO_ERROR_MESSAGE, e);
        }
    }

    private Account getDataFromFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            Object readObject = inputStream.readObject();

            if (readObject instanceof Account) {
                Account readAccount = (Account) readObject;
                readAccount.initLock();
                return readAccount;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} not found(method getDataFromFile)", file, e);
        } catch (IOException e) {
            LOGGER.error(IO_ERROR_MESSAGE, e);
        }
        throw new InvalidObjectInFileException("object in file:" + file + " is not Account.class");
    }
}
