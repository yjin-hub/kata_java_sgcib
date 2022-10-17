package com.katasgcib.app.service;

import com.katasgcib.app.exception.AccountCreationException;
import com.katasgcib.app.exception.NotSameCurrencyException;
import com.katasgcib.app.exception.WithdrawalException;
import com.katasgcib.app.model.BankAccount;
import com.katasgcib.app.model.Operation;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    /**
     * Description
     *
     * @param
     * @return bank account which has been created
     */
    BankAccount createBankAccount(String iban, String label, Currency currency, BigDecimal balance) throws AccountCreationException;

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
     * @return list of all bank accounts created
     */
    List<BankAccount> getBankAccounts();

    /**
     * Description
     *
     * @param iban bank accunt IBAN
     * @param amount amount of money to deposit on the account
     * @param currency currency of the amount
     * @throws NotSameCurrencyException if currency argument is different than bank account currency
     * @return the account on which the deposit action takes place
     */
    BankAccount deposit(String iban, BigDecimal amount, Currency currency) throws NotSameCurrencyException, AccountCreationException;

    /**
     * Description
     *
     * @param iban bank accunt IBAN
     * @param amount amount of money to deposit on the account
     * @param currency currency of the amount
     * @throws WithdrawalException if not enough money in account
     * @throws NotSameCurrencyException if currency is different than bank account currency
     * @return the account on which the withdrawal takes place
     */
    BankAccount withdrawal(String iban, BigDecimal amount, Currency currency) throws WithdrawalException, NotSameCurrencyException, AccountCreationException;


    /**
     * Description
     *
     * @param iban bank account IBAN
     */
    void displayOperationsHistory(String iban);

    /**
     * Description
     *
     * @param iban bank account IBAN
     * @return list of operations associated to the bank account
     */
    List<Operation> getBankAccountHistory(String iban);

    /**
     * Description
     *
     * @return true if there is none bank account created
     */
    boolean isNoneBankAccount();

    }
