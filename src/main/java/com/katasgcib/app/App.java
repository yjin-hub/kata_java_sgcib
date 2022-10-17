package com.katasgcib.app;

import com.katasgcib.app.exception.AccountCreationException;
import com.katasgcib.app.exception.NotSameCurrencyException;
import com.katasgcib.app.exception.WithdrawalException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.katasgcib.app.model.enums.OperationType;
import com.katasgcib.app.service.BankAccountService;
import com.katasgcib.app.service.impl.BankAccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App
{

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final Scanner sc = new Scanner(System.in);

    private static boolean inProcess = true;

    private static final BankAccountService bankAccountService = new BankAccountServiceImpl();

    public static void main( String[] args ) throws WithdrawalException, NotSameCurrencyException, AccountCreationException {
        initProgram();
    }

    public static void initProgram() throws WithdrawalException, NotSameCurrencyException, AccountCreationException {
        while (inProcess) {
            logger.info("Enter a number according to wanted operation : \n" +
                    "1 - Account creation\n" +
                    "2 - Deposit to an account\n" +
                    "3 - Withdrawal from an account\n" +
                    "4 - Display all operations of an account\n"+
                    "5 - Quit");
            int actionChoice = 0;
            try {
                logger.info("Choose an action (1 2 3 4 ou 5)");
                actionChoice = sc.nextInt();
            } catch (InputMismatchException ex){
                logger.info("Invalid Input. Only digits !");
                sc.nextLine();
            }
            checkUserInput(actionChoice);
        }
    }

    public static void checkUserInput(int actionChoice) throws WithdrawalException, NotSameCurrencyException, AccountCreationException {
        switch (actionChoice) {
            case 1:
                handleAccountCreation();
                break;
            case 2:
                handleDepositAction();
                break;
            case 3:
                handleWithdrawalAction();
                break;
            case 4:
                handlePrintOperationsAction();
                break;
            case 5:
                inProcess = false;
                break;
            default:
                logger.info("Input can be only digits between 1 and 5");
        }
    }

    public static void handleAccountCreation() throws AccountCreationException {
        logger.info("Enter an iban :");
        String iban = sc.next();
        logger.info("Enter your name :");
        String label = sc.next();
        String currency = handleCurrencyInput();
        BigDecimal amount = handleAmountInput();
        bankAccountService.createBankAccount(iban, label, Currency.getInstance(currency), amount);
    }

    public static void handleDepositAction() throws AccountCreationException, WithdrawalException, NotSameCurrencyException {
        logger.info("Enter an iban :");
        String iban = sc.next();
        String currency = handleCurrencyInput();
        BigDecimal amount = handleAmountInput();
        parseMoneyAction(OperationType.DEPOSIT, iban, currency, amount);
        logger.info("Deposit performed");
    }

    public static void handleWithdrawalAction() throws AccountCreationException, WithdrawalException, NotSameCurrencyException {
        logger.info("Enter an iban :");
        String iban = sc.next();
        String currency = handleCurrencyInput();
        BigDecimal amount = handleAmountInput();
        parseMoneyAction(OperationType.WITHDRAWAL, iban, currency, amount);
        logger.info("Withdrawal performed");
    }

    public static void handlePrintOperationsAction() {
        logger.info("Enter an iban :");
        String iban = sc.next();
        bankAccountService.displayOperationsHistory(iban);
    }

    public static boolean isValidCurrency(String currencyName) {
        Set<String> availableCurrencies = Currency.getAvailableCurrencies().stream().map(Currency::getCurrencyCode).collect(Collectors.toSet());
        return availableCurrencies.contains(currencyName);
    }

    public static void parseMoneyAction(OperationType type, String iban, String currency, BigDecimal amount) throws NotSameCurrencyException, WithdrawalException, AccountCreationException {
        if (type == OperationType.DEPOSIT) {
            bankAccountService.deposit(iban, amount, Currency.getInstance(currency));
        } else {
            bankAccountService.withdrawal(iban, amount, Currency.getInstance(currency));
        }
    }

    public static String handleCurrencyInput() {
        String currency;
        logger.info("Enter a currency :");
        do {
            currency = sc.next();
            if (!isValidCurrency(currency)) logger.info("Enter a valid currency ! Example : EUR, USD, GBP, INR");
        } while (!isValidCurrency(currency));
        return currency;
    }


    public static BigDecimal handleAmountInput() {
        Scanner scanner = new Scanner(System.in);
        boolean invalidValue = true;
        BigDecimal amount = null;
        logger.info("Enter an amount :");
        do {
            try {
                amount = scanner.nextBigDecimal();
                invalidValue = false;
            }
            catch (InputMismatchException e) {
                logger.info("Invalid input, enter an amount again");
                scanner = new Scanner(System.in);
            }
        } while (invalidValue);
        return amount;
    }




}
