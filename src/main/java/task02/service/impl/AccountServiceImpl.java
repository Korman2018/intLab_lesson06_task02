package task02.service.impl;

import task02.model.Account;
import task02.repository.IAccountDAO;
import task02.repository.impl.AccountDAOImpl;
import task02.service.IAccountService;

import java.util.List;

import static task02.generator.AccountGenerator.generate;
import static task02.utils.TransactionsConstants.LOGGER;

public class AccountServiceImpl implements IAccountService {
    private IAccountDAO<Account> accountDAO;
    private List<Account> localAccounts;

    public AccountServiceImpl() {
        accountDAO = new AccountDAOImpl();
        localAccounts = accountDAO.getAll();
    }

    @Override
    public List<Account> getAllAccounts() {
        return localAccounts;
    }

    @Override
    public long getTotalSum() {
        return getAllAccounts().stream().map(Account::getBalance).reduce(0L, Long::sum);
    }

    @Override
    public void generateAccountsFiles(int numberOfAccounts) {
        removeAllAccounts();
        for (int i = 0; i < numberOfAccounts; i++) {
            accountDAO.add(generate());
            localAccounts = accountDAO.getAll();
        }
    }

    @Override
    public void removeAllAccounts() {
        accountDAO.removeAll();
        localAccounts = accountDAO.getAll();
    }

    @Override
    public void printAllAccountsInfo() {
        LOGGER.info("All accounts info:");
        getAllAccounts().forEach(x -> LOGGER.info(x.toString()));
        LOGGER.info("End of all accounts info.");
    }
}
