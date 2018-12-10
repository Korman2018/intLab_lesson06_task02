package task02.service;

import task02.model.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAllAccounts();

    long getTotalSum();

    void generateAccountsFiles(int numberOfFiles);

    void removeAllAccounts();

    void printAllAccountsInfo();
}
