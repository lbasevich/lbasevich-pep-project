package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     *
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account createAccountService(Account account) {
        return accountDAO.createAccount(account);
    }

    /**
     *
     * @param account an account object.
     * @return The persisted account if the account is valid.
     */
    public Account logInService(Account account) {
          return accountDAO.logIn(account);
     }

}
