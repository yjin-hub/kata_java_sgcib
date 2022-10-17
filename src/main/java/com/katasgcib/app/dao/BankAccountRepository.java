package com.katasgcib.app.dao;

import com.katasgcib.app.exception.WithdrawalException;
import com.katasgcib.app.model.BankAccount;
import com.katasgcib.app.model.Operation;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {

    /**
     * Description
     *
     * @param
     * @return bank account created
     */
    BankAccount createBankAccount(String iban, String label, Currency currency, BigDecimal balance);

    /**
     * Description
     *
     * @param
     * @return bank account with specified IBAN
     */
    Optional<BankAccount> findBankAccountByIban(String iban);

    /**
     * Description
     *
     * @return list of all bank accounts
     */
    List<BankAccount> getBankAccounts();

    /**
     * Description
     *
     * @param bankAccount bank account
     * @param amount amount of money to deposit on account
     * @return the account on which the deposit action takes place
     */
    BankAccount deposit(BankAccount bankAccount, BigDecimal amount);

    /**
     * Description
     *
     * @param bankAccount bank account
     * @param amount amount of money to deposit on the account
     * @throws WithdrawalException if not enough money in account
     * @return the account on which the withdrawal takes place
     */
    BankAccount withdrawal(BankAccount bankAccount, BigDecimal amount) throws WithdrawalException;

    /**
     * Description
     *
     * @return true if there is none bank account created
     */
    boolean isNoneBankAccount();

    /**
     * Description
     *
     * @param iban bank account IBAN
     * @return list of operations associated to the bank account
     */
    List<Operation> getBankAccountHistory(String iban);
}
