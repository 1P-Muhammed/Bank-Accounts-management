package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final int MAX_ACCOUNTS = 10;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BankAccount[] accounts =
                new BankAccount[MAX_ACCOUNTS];

        boolean running = true;

        while (running) {

            showMenu();

            int choice = readInt(scanner, "Enter choice: ");

            switch (choice) {

                case 1:
                    createAccount(scanner, accounts);
                    break;

                case 2:
                    deposit(scanner, accounts);
                    break;

                case 3:
                    withdraw(scanner, accounts);
                    break;

                case 4:
                    transfer(scanner, accounts);
                    break;

                case 5:
                    displayAccount(scanner, accounts);
                    break;

                case 6:
                    displayAllAccounts(accounts);
                    break;

                case 7:
                    showStatistics(accounts);
                    break;

                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }

        scanner.close();
    }

    static void showMenu() {

        System.out.println();
        System.out.println("==============================");
        System.out.println("    BANK MANAGEMENT SYSTEM");
        System.out.println("==============================");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Display Account");
        System.out.println("6. Display All Accounts");
        System.out.println("7. Show Bank Statistics");
        System.out.println("0. Exit");
    }

    static void createAccount(
            Scanner scanner,
            BankAccount[] accounts) {

        if (BankAccount.getAccountCount()
                >= accounts.length) {

            System.out.println("Bank is full.");
            return;
        }

        System.out.println(
                "\n========== CREATE ACCOUNT =========="
        );

        int accountNumber =
                readInt(scanner, "Enter account number: ");

        if (accountNumber <= 0) {
            System.out.println(
                    "Account number must be greater than zero."
            );
            return;
        }

        if (findAccountIndex(
                accounts,
                accountNumber) != -1) {

            System.out.println(
                    "Account number already exists."
            );
            return;
        }

        scanner.nextLine();

        String ownerName =
                readNonEmptyString(
                        scanner,
                        "Enter owner name: "
                );

        AccountType accountType =
                readAccountType(scanner);

        double balance =
                readDouble(
                        scanner,
                        "Enter initial balance: "
                );

        try {

            BankAccount account =
                    new BankAccount(
                            accountNumber,
                            ownerName,
                            accountType,
                            balance
                    );

            int index = findEmptyIndex(accounts);

            accounts[index] = account;

            System.out.println(
                    "Account created successfully."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void deposit(
            Scanner scanner,
            BankAccount[] accounts) {

        System.out.println("\n========== DEPOSIT ==========");

        int accountNumber =
                readInt(
                        scanner,
                        "Enter account number: "
                );

        int index =
                findAccountIndex(
                        accounts,
                        accountNumber
                );

        if (index == -1) {
            System.out.println("Account not found.");
            return;
        }

        double amount =
                readDouble(
                        scanner,
                        "Enter amount: "
                );

        try {

            accounts[index].deposit(amount);

            System.out.printf(
                    "Deposit successful.%n" +
                            "New balance: $%.2f%n",
                    accounts[index].getBalance()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void withdraw(
            Scanner scanner,
            BankAccount[] accounts) {

        System.out.println("\n========== WITHDRAW ==========");

        int accountNumber =
                readInt(
                        scanner,
                        "Enter account number: "
                );

        int index =
                findAccountIndex(
                        accounts,
                        accountNumber
                );

        if (index == -1) {
            System.out.println("Account not found.");
            return;
        }

        double amount =
                readDouble(
                        scanner,
                        "Enter amount: "
                );

        try {

            boolean success =
                    accounts[index].withdraw(amount);

            if (success) {

                System.out.printf(
                        "Withdrawal successful.%n" +
                                "New balance: $%.2f%n",
                        accounts[index].getBalance()
                );

            } else {

                System.out.println(
                        "Insufficient balance."
                );
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void transfer(
            Scanner scanner,
            BankAccount[] accounts) {

        System.out.println("\n========== TRANSFER ==========");

        int senderNumber =
                readInt(
                        scanner,
                        "Enter sender account number: "
                );

        int senderIndex =
                findAccountIndex(
                        accounts,
                        senderNumber
                );

        if (senderIndex == -1) {
            System.out.println(
                    "Sender account not found."
            );
            return;
        }

        int receiverNumber =
                readInt(
                        scanner,
                        "Enter receiver account number: "
                );

        int receiverIndex =
                findAccountIndex(
                        accounts,
                        receiverNumber
                );

        if (receiverIndex == -1) {
            System.out.println(
                    "Receiver account not found."
            );
            return;
        }

        double amount =
                readDouble(
                        scanner,
                        "Enter amount: "
                );

        try {

            boolean success =
                    accounts[senderIndex].transfarTo(
                            accounts[receiverIndex],
                            amount
                    );

            if (success) {

                System.out.println(
                        "Transfer successful."
                );

            } else {

                System.out.println(
                        "Transfer failed. " +
                                "Insufficient balance."
                );
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    static void displayAccount(
            Scanner scanner,
            BankAccount[] accounts) {

        int accountNumber =
                readInt(
                        scanner,
                        "Enter account number: "
                );

        int index =
                findAccountIndex(
                        accounts,
                        accountNumber
                );

        if (index == -1) {
            System.out.println("Account not found.");
            return;
        }

        accounts[index].displayAccount();
    }

    static void displayAllAccounts(
            BankAccount[] accounts) {

        System.out.println(
                "\n========== ALL ACCOUNTS =========="
        );

        boolean found = false;

        for (BankAccount account : accounts) {

            if (account != null) {

                account.displayAccount();

                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No accounts found."
            );
        }
    }

    static void showStatistics(
            BankAccount[] accounts) {

        double totalBalance = 0;

        BankAccount richestAccount = null;

        for (BankAccount account : accounts) {

            if (account != null) {

                totalBalance += account.getBalance();

                if (richestAccount == null ||
                        account.getBalance()
                                > richestAccount.getBalance()) {

                    richestAccount = account;
                }
            }
        }

        System.out.println(
                "\n========== BANK STATISTICS =========="
        );

        System.out.println(
                "Total Accounts : "
                        + BankAccount.getAccountCount()
        );

        System.out.printf(
                "Total Balance  : $%.2f%n",
                totalBalance
        );

        if (richestAccount != null) {

            System.out.println(
                    "Richest Owner  : "
                            + richestAccount.getOwnerName()
            );

            System.out.printf(
                    "Highest Balance: $%.2f%n",
                    richestAccount.getBalance()
            );
        }
    }

    static int findAccountIndex(
            BankAccount[] accounts,
            int accountNumber) {

        for (int i = 0;
             i < accounts.length;
             i++) {

            if (accounts[i] != null &&
                    accounts[i].getAccountNumber()
                            == accountNumber) {

                return i;
            }
        }

        return -1;
    }

    static int findEmptyIndex(
            BankAccount[] accounts) {

        for (int i = 0;
             i < accounts.length;
             i++) {

            if (accounts[i] == null) {
                return i;
            }
        }

        return -1;
    }

    static int readInt(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            try {

                return scanner.nextInt();

            } catch (InputMismatchException e) {

                System.out.println(
                        "Invalid input. " +
                                "Enter an integer."
                );

                scanner.next();
            }
        }
    }

    static double readDouble(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            try {

                return scanner.nextDouble();

            } catch (InputMismatchException e) {

                System.out.println(
                        "Invalid input. " +
                                "Enter a number."
                );

                scanner.next();
            }
        }
    }

    static String readNonEmptyString(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine();

            if (!input.isBlank()) {
                return input.trim();
            }

            System.out.println(
                    "Owner name cannot be blank."
            );
        }
    }

    static AccountType readAccountType(
            Scanner scanner) {

        while (true) {

            System.out.print(
                    "Enter account type " +
                            "(SAVINGS/CURRENT): "
            );

            String input =
                    scanner.nextLine()
                            .trim()
                            .toUpperCase();

            try {

                return AccountType.valueOf(input);

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid account type."
                );
            }
        }
    }
}