package org.example;

public class BankAccount {

    // Fields..

    private final int accountNumber;
    private String ownerName;
    private AccountType accountType;
    private double balance;

    private static int accountCount = 0;

    private static final double MIN_INITIAL_BALANCE = 100.0;

    //Constructor..

    public BankAccount(int accountNumber,
                       String ownerName,
                       AccountType accountType,
                       double balance) {

        //Validation..

        if(accountNumber <= 0) {
            throw new IllegalArgumentException(
                    "Account number must be greater than zero.."
            );
        }

        if(ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Owner name cannot be blank..."
            );
        }

        if(accountType == null) {
            throw new IllegalArgumentException(
                    "Account type cannot be null"
            );
        }

        if(balance < MIN_INITIAL_BALANCE) {
            throw new IllegalArgumentException(
                    "Initial balance must be at least " +
                            MIN_INITIAL_BALANCE
            );
        }

        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.accountType = accountType;
        this.balance = balance;

        accountCount++;
    }

    //Methods

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public double getBalance() {
        return this.balance;
    }

    public static int getAccountCount() {
        return accountCount;
    }

    public void deposit(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException(
                    "The amount cannot be less than zero.."
            );
        }

        balance += amount;
    }

    public boolean withdraw(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException(
                    "The amount must be greater than zero.."
            );
        }

        if(amount > balance)
            return false;

        balance -= amount;

        return true;
    }

    public boolean transfarTo(BankAccount target, double amount) {
        if(target == null) {
            throw new IllegalArgumentException(
                    "Target account cannot be null.."
            );
        }

        if(target == this) {
            throw new IllegalArgumentException(
                    "Cannot transfer money to the same account.."
            );
        }

        if(amount <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero.."
            );
        }

        if(!withdraw(amount)) {
            return false;
        }

        target.deposit(amount);

        return true;
    }

    public void displayAccount() {
        System.out.println("--------------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Owner : " + ownerName);
        System.out.println("Type : " + accountType);
        System.out.printf("Balance : $%.2f%n", balance);
        System.out.println("--------------------------------");
    }

}
