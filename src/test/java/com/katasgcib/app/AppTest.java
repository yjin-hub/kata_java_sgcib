package com.katasgcib.app;

import com.katasgcib.app.exception.AccountCreationException;
import com.katasgcib.app.exception.NotSameCurrencyException;
import com.katasgcib.app.exception.WithdrawalException;
import com.katasgcib.app.model.BankAccount;
import com.katasgcib.app.service.BankAccountService;
import com.katasgcib.app.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    BankAccount account;
    BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        account = new BankAccount("FR6030003000502714267262V91", "John Doe", Currency.getInstance(Locale.FRANCE), new BigDecimal(5000));
        bankAccountService = new BankAccountServiceImpl();
        bankAccountService.getBankAccounts().add(account);
    }

    @Test
    @DisplayName("account creation")
    public void accountCreation() throws AccountCreationException {
        bankAccountService.createBankAccount("FR4898ghds", "Bob", Currency.getInstance("EUR"), new BigDecimal(1000));
        Assertions.assertEquals(bankAccountService.getBankAccounts().size(), 2, "size error");
    }

    @Test
    @DisplayName("deposit test")
    public void depositTest() throws NotSameCurrencyException, AccountCreationException {
        BankAccount accountUpdated = bankAccountService.deposit("FR6030003000502714267262V91", new BigDecimal(2000), Currency.getInstance(Locale.FRANCE));
        assertNotNull(accountUpdated);
        Assertions.assertEquals(0, accountUpdated.getBalance().compareTo(new BigDecimal(7000)), "deposit test failed");
        Assertions.assertEquals(accountUpdated.getHistory().size(), 1, "size error");
    }

    @Test
    @DisplayName("withdrawal test")
    public void withdrawalTest() throws WithdrawalException, NotSameCurrencyException, AccountCreationException {
        BankAccount accountUpdated = bankAccountService.withdrawal("FR6030003000502714267262V91", new BigDecimal(2000), Currency.getInstance(Locale.FRANCE));
        assertNotNull(accountUpdated);
        Assertions.assertEquals(0, accountUpdated.getBalance().compareTo(new BigDecimal(3000)), "withdrawal test failed");
        Assertions.assertEquals(accountUpdated.getHistory().size(), 1);
    }

    @Test
    @DisplayName("deposit + withdrawal")
    public void depositAndWithdrawalTest() throws WithdrawalException, NotSameCurrencyException, AccountCreationException {
        BankAccount accountDeposit = bankAccountService.deposit("FR6030003000502714267262V91", new BigDecimal(2000), Currency.getInstance(Locale.FRANCE));
        assertNotNull(accountDeposit);
        Assertions.assertEquals(0, accountDeposit.getBalance().compareTo(new BigDecimal(7000)), "deposit test failed");
        Assertions.assertEquals(accountDeposit.getHistory().size(), 1);

        BankAccount accountWithdrawal = bankAccountService.withdrawal("FR6030003000502714267262V91", new BigDecimal(6500), Currency.getInstance(Locale.FRANCE));
        assertNotNull(accountWithdrawal);
        Assertions.assertEquals(0, accountWithdrawal.getBalance().compareTo(new BigDecimal(500)), "withdrawal test failed");
        Assertions.assertEquals(accountWithdrawal.getHistory().size(), 2);
    }

    @Test
    @DisplayName("withdrawal not possible")
    public void withdrawalExceptionTest() {
        WithdrawalException exception = Assertions.assertThrows(WithdrawalException.class, () -> {
            BankAccount accountWithdrawal = bankAccountService.withdrawal("FR6030003000502714267262V91", new BigDecimal(10000), Currency.getInstance(Locale.FRANCE));
        });
        Assertions.assertEquals("Not enough money in bank account", exception.getMessage());
    }
}
