import java.util.*;
import java.time.LocalDate;

class BankAccount {
    private String accountNumber;
    private String holderName;
    private String holderAddress;
    private LocalDate openingDate;
    private double currentBalance;
    private MyQueue<Transaction> transactions;


    // Bank Account attributes
    public BankAccount(String accountNumber, String holderName, String holderAddress, LocalDate openingDate, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.holderAddress = holderAddress;
        this.openingDate = openingDate;
        this.currentBalance = initialBalance;
        this.transactions = new MyQueue<>(4); // Capacity of 4 transactions
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }
    
    public String getHolderAddress() {
        return holderAddress;
    }
    
    public LocalDate getOpeningDate() {
        return openingDate;
    }
    
    public double getCurrentBalance() {
        return currentBalance;
    }    


    //Validation for making sure that balance is not <0
    public void addTransaction(String type, double amount) {
        if (type.equals("withdrawal") && currentBalance < amount) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal.");
        }
        if (amount <0){
            throw new IllegalArgumentException("Amount entered must be a positive value");
        }
        // update the value of amount
        if (type.equals("withdrawal")) {
            currentBalance -= amount;
        } else if (type.equals("deposit")) {
            currentBalance += amount;
        }

        transactions.enqueue(new Transaction(type, amount, LocalDate.now()));
    }

    //Method displayed when testing through the console
    public void displayDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Address: " + holderAddress);
        System.out.println("Opening Date: " + openingDate);
        System.out.println("Current Balance: " + currentBalance);
    }

    //Sorts transactions based on amount in ascending order
    public String displaySortedTransactions() {
        List<Transaction> transactionList = transactions.toList(); // Convert to list
        SortUtils.insertionSort(transactionList); // Use custom insertion sort
        
        StringBuilder result = new StringBuilder("Sorted Transactions:\n\n");
        for (Transaction t : transactionList) {
            result.append(t).append("\n");
        }
        return result.toString(); // Return the concatenated string
    }    
}
