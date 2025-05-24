import javax.swing.*;
import java.util.*;
import java.time.LocalDate;

class BankSystem {
    private Map<String, BankAccount> accounts;

    public BankSystem() {
        accounts = new HashMap<>();
    }

    public void createAccount(String accountNumber, String holderName, String holderAddress, LocalDate openingDate, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account with account number: " + accountNumber + " already exists."); //Checks for accountNumber duplication
        }
        if(initialBalance<0){ //initialBalance must be >=0
            throw new IllegalArgumentException("Balance can't be negative.");
        }
        accounts.put(accountNumber, new BankAccount(accountNumber, holderName, holderAddress, openingDate, initialBalance));
    }

    public void displayAccounts() {
        for (BankAccount account : accounts.values()) {
            account.displayDetails();
            System.out.println();
        }
    }

    public Object[][] getAccountsData() {
        Object[][] data = new Object[accounts.size()][5]; // 5 columns: Account Number, Holder Name, Address, Opening Date, Balance
        int i = 0;
    
        for (BankAccount account : accounts.values()) {
            data[i][0] = account.getAccountNumber();
            data[i][1] = account.getHolderName();
            data[i][2] = account.getHolderAddress();
            data[i][3] = account.getOpeningDate().toString();
            data[i][4] = "£ " + account.getCurrentBalance();
            i++;
        }
        return data;
    }
    public boolean deleteAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            // return false;
            throw new IllegalArgumentException("Account Number: " + accountNumber + " not found.");
        }
        accounts.remove(accountNumber); //account number deleted
        System.out.println("Account: " + accountNumber + " has been deleted.");
        return true;
    }

    public void addTransaction(String accountNumber, String type, double amount) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account Number:" + accountNumber + " not found.");
        }
        if (amount < 0 ){
            throw new IllegalArgumentException("Amount entered must be a negative value");
        }
        if (amount == 0){ //transaction has to contain a positive value >0
            throw new IllegalArgumentException("Amount entered must be at least £0.01");
        }
        account.addTransaction(type, amount);
    }

    public String displaySortedTransactions(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            // return "Account Not Found!";
            throw new IllegalArgumentException("Account number:" + accountNumber +  " not found.");
        }
        return account.displaySortedTransactions(); //method called from BankAccount
    }


}
