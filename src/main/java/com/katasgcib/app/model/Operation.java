package com.katasgcib.app.model;

import com.katasgcib.app.model.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


public class Operation {
    private OperationType type;
    private LocalDateTime creationDate;
    private BigDecimal amount;
    private BigDecimal balance;

    public Operation(OperationType type, LocalDateTime creationDate, BigDecimal amount, BigDecimal balance) {
        this.type = type;
        this.creationDate = creationDate;
        this.amount = amount;
        this.balance = balance;
    }

    public OperationType getType() {
        return this.type;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Operation other = (Operation) obj;

        if (!(this.type == other.getType()
                && this.creationDate == other.getCreationDate()
                && Objects.equals(this.amount, other.getAmount())
                && Objects.equals(this.getBalance(), other.getBalance()))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 77 * hash + this.type.hashCode();
        hash = 77 * hash + this.creationDate.hashCode();
        hash = 77 * hash + this.amount.hashCode();
        hash = 77 * hash + this.balance.hashCode();
        return hash;
    }

    public String toString() {
        return "Operation {"+"type="+type+"" +",creationDate="+creationDate+","+"amount="+amount+","+"balance after ="+balance+"}";
    }
}