package com.katasgcib.app.service.impl;

import com.katasgcib.app.App;
import com.katasgcib.app.dao.impl.BankAccountRepositoryImpl;
import com.katasgcib.app.exception.AccountCreationException;
import com.katasgcib.app.exception.NotSameCurrencyException;
import com.katasgcib.app.exception.WithdrawalException;
import com.katasgcib.app.model.BankAccount;
import com.katasgcib.app.model.Operation;
import com.katasgcib.app.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    BankAccountRepositoryImpl bankAccountDao = new BankAccountRepositoryImpl();

    @Override
    public BankAccount createBankAccount(String iban, String label, Currency currency, BigDecimal balance) throws AccountCreationException {
        if (this.bankAccountDao.findBankAccountByIban(iban).isPresent()) throw new AccountCreationException("IBAN already exist");
        return this.bankAccountDao.createBankAccount(iban, label, currency, balance);
    }

    @Override
    public Optional<BankAccount> findBankAccountByIban(String iban) {
        return this.bankAccountDao.findBankAccountByIban(iban);
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return this.bankAccountDao.getBankAccounts();
    }

    @Override
    public boolean isNoneBankAccount() {
        return this.bankAccountDao.isNoneBankAccount();
    }

    @Override
    public BankAccount deposit(String iban, BigDecimal amount, Currency currency) throws NotSameCurrencyException, AccountCreationException {
        Optional<BankAccount> bankAccount = this.findBankAccountByIban(iban);
        if (bankAccount.isPresent()) {
            if (bankAccount.get().getCurrency() != currency) {
                throw new NotSameCurrencyException("Not same currency");
            }
            return this.bankAccountDao.deposit(bankAccount.get(), amount);
        } else {
            throw new AccountCreationException("Bank account does not exist");
        }
    }

    @Override
    public BankAccount withdrawal(String iban, BigDecimal amount, Currency currency) throws NotSameCurrencyException, AccountCreationException, WithdrawalException {
        Optional<BankAccount> bankAccount = this.findBankAccountByIban(iban);
        if (bankAccount.isPresent()) {
            if (bankAccount.get().getCurrency() != currency) {
                throw new NotSameCurrencyException("Not same currency");
            }
            return this.bankAccountDao.withdrawal(bankAccount.get(), amount);
        } else {
            throw new AccountCreationException("Bank account does not exist");
        }
    }

    @Override
    public void displayOperationsHistory(String iban) {
        Optional<BankAccount> bankAccount = bankAccountDao.findBankAccountByIban(iban);
        if (bankAccount.isPresent() && bankAccount.get().getHistory().size() > 0) {
            bankAccount.get().displayOperationsHistory();
        } else {
            logger.info("None operations has been found for this account");
        }
    }

    @Override
    public List<Operation> getBankAccountHistory(String iban) {
        return this.bankAccountDao.getBankAccountHistory(iban);
    }

}
