package com.katasgcib.app.dao.impl;

import com.katasgcib.app.dao.BankAccountRepository;
import com.katasgcib.app.exception.WithdrawalException;
import com.katasgcib.app.model.BankAccount;
import com.katasgcib.app.model.Operation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class BankAccountRepositoryImpl implements BankAccountRepository {

    List<BankAccount> bankAccounts = new ArrayList<>();

    @Override
    public BankAccount createBankAccount(String iban, String label, Currency currency, BigDecimal balance) {
        BankAccount account = new BankAccount(iban, label, currency, balance);
        this.bankAccounts.add(account);
        return account;
    }

    @Override
    public Optional<BankAccount> findBankAccountByIban(String iban) {
        return this.bankAccounts.stream().filter(c -> c.getIban().equals(iban)).findAny();
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return this.bankAccounts;
    }

    @Override
    public BankAccount deposit(BankAccount bankAccount, BigDecimal amount) {
        return bankAccount.deposit(amount);
    }

    @Override
    public BankAccount withdrawal(BankAccount bankAccount, BigDecimal amount) throws WithdrawalException {
        return bankAccount.withdrawal(amount);
    }

    @Override
    public boolean isNoneBankAccount() {
        return this.bankAccounts.size() == 0;
    }

    @Override
    public List<Operation> getBankAccountHistory(String iban) {
        return this.bankAccounts.stream().filter(c -> c.getIban().equals(iban)).findAny().get().getHistory();
    }


}
