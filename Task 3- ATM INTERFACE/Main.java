import java.util.*;

class Transaction {
    String type;
    double amount;
    String transactionId;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.transactionId = generateTransactionId();
    }

    private String generateTransactionId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        Random random = new Random();
        StringBuilder transactionId = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(characters.length());
            transactionId.append(characters.charAt(index));
        }
        return transactionId.toString();
    }

    @Override
    public String toString() {
        return "[Type: " + type + "]  [Amount: $" + amount + "]  [Transaction ID: " + transactionId + "]";
    }
}

class BankAccount {
    String fullName;
    String userId;
    String pin;
    String accountNumber;
    double balance;
    StringBuilder transactionHistory;

    public BankAccount(String fullName, String userId, String pin) {
        this.fullName = fullName;
        this.userId = userId;
        this.pin = pin;
        this.accountNumber = generateAccountNumber();
        this.balance = 0;
        this.transactionHistory = new StringBuilder();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accNumber = new StringBuilder("ACC");
        for (int i = 0; i < 7; i++) {
            accNumber.append(random.nextInt(10));
        }
        return accNumber.toString();
    }

    public boolean checkPin(String inputPin) {
        return pin.equals(inputPin);
    }
}

class ATM {
    BankAccount currentAccount;
    Scanner scanner;

    public ATM() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        int choice;

        while (true) {
            printAsterisks();
            System.out.println("***** Welcome to the ATM Interface *****");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("****************************************");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using the ATM Interface!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printAsterisks() {
        for (int i = 0; i < 40; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    private void register() {
        printAsterisks();
        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter a user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter a 4-digit PIN: ");
        String pin = scanner.nextLine();

        currentAccount = new BankAccount(fullName, userId, pin);

        System.out.println("Registration successful.");
        System.out.println("Your Account Number: " + currentAccount.accountNumber);
    }

    private void login() {
        boolean loggedIn = false;

        while (!loggedIn) {
            printAsterisks();
            System.out.print("Enter user ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            if (currentAccount != null && currentAccount.userId.equals(userId) && currentAccount.checkPin(pin)) {
                loggedIn = true;
                mainMenu();
            } else {
                System.out.println("Invalid user ID or PIN. Please try again.");
            }
        }
    }

    private void mainMenu() {
        int choice;

        while (true) {
            printAsterisks();
            System.out.println("\n***** Welcome, " + currentAccount.fullName + " *****");
            System.out.println("Account Number: " + currentAccount.accountNumber);
            System.out.println("Main Menu:");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.println("****************************************");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    performWithdrawal();
                    break;
                case 2:
                    performDeposit();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    displayTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM Interface!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void performWithdrawal() {
        printAsterisks();
        System.out.print("Enter withdrawal amount $: ");
        double amount = scanner.nextDouble();
        if (amount > currentAccount.balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        currentAccount.balance -= amount;
        currentAccount.transactionHistory.append(new Transaction("Withdrawal", amount)).append("\n");
        System.out.println("Withdrawal successful.");
    }

    private void performDeposit() {
        printAsterisks();
        System.out.print("Enter deposit amount $: ");
        double amount = scanner.nextDouble();
        currentAccount.balance += amount;
        currentAccount.transactionHistory.append(new Transaction("Deposit", amount)).append("\n");
        System.out.println("Deposit successful.");
    }

    private void checkBalance() {
        printAsterisks();
        System.out.println("Your current balance is $" + currentAccount.balance);
    }

    private void performTransfer() {
        printAsterisks();
        System.out.print("Enter recipient's account number: ");
        String recipientAccountNumber = scanner.nextLine();
        System.out.print("Enter transfer amount $: ");
        double amount = scanner.nextDouble();

        if (amount > currentAccount.balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        currentAccount.balance -= amount;
        currentAccount.transactionHistory.append(new Transaction("Transfer to " + recipientAccountNumber, amount)).append("\n");
        System.out.println("Transfer successful.");
    }

    private void displayTransactionHistory() {
        printAsterisks();
        System.out.println("Transaction History:");
        String[] transactions = currentAccount.transactionHistory.toString().split("\n");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}
