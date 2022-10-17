package com.katasgcib.app.model;

import com.katasgcib.app.model.enums.OperationType;
import com.katasgcib.app.exception.WithdrawalException;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class BankAccount {
    private String IBAN;
    private String label;
    private LocalDateTime creationDate;
    private BigDecimal balance;
    private Currency currency;
    private List<Operation> history;

    public BankAccount(String iban, String label, Currency currency, BigDecimal balance) {
        this.IBAN = iban;
        this.label = label;
        this.currency = currency;
        this.balance = balance;
        this.creationDate = LocalDateTime.now();
        this.history = new ArrayList<>();
    }

    public String getIban() {
        return this.IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getBalance() {
        return new BigDecimal(this.balance.toString());
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public BankAccount deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        this.history.add(new Operation(OperationType.DEPOSIT, LocalDateTime.now(), new BigDecimal(amount.toString()), new BigDecimal(this.balance.toString())));
        return this;
    }

    public BankAccount withdrawal(BigDecimal amount) throws WithdrawalException {
        if (this.balance.compareTo(amount) >= 0) {
            this.balance = this.balance.subtract(amount);
            this.history.add(
                new Operation(OperationType.WITHDRAWAL, LocalDateTime.now(), new BigDecimal(amount.toString()), new BigDecimal(this.balance.toString())));
        } else {  
            throw new WithdrawalException("Not enough money in bank account");
        }
        return this;
    }

    public void displayOperationsHistory() {
        this.getHistory().stream().forEach(System.out::println);
    }

    public List<Operation> getHistory() {
        return Collections.unmodifiableList(this.history);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final BankAccount other = (BankAccount) obj;

        if (!Objects.equals(this.label, other.label)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.IBAN.hashCode();
        hash = 47 * hash + this.label.hashCode();
        return hash;
    }

    public String toString() {
        return "BankAccount {"+ "IBAN="+this.IBAN+""+"label="+this.label+"" +",balance="+this.balance+","+"creationDate="+this.creationDate+",currency="+this.currency+"}";
    }

}