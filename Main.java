import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) { //main menu displayed to user
            System.out.println("\nBank System Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Display Accounts");
            System.out.println("3. Delete Account");
            System.out.println("4. Add Transaction");
            System.out.println("5. Display Sorted Transactions");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter account number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter holder name: ");
                        String holderName = scanner.nextLine();
                        System.out.print("Enter holder address: ");
                        String holderAddress = scanner.nextLine();
                        System.out.print("Enter opening date (YYYY-MM-DD): ");
                        LocalDate openingDate = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter initial balance: ");
                        double initialBalance = scanner.nextDouble();
                        scanner.nextLine();
                        bankSystem.createAccount(accountNumber, holderName, holderAddress, openingDate, initialBalance);
                        System.out.println("Account created successfully.");
                        break;
                    case 2:
                        bankSystem.displayAccounts();
                        break;
                    case 3:
                        System.out.print("Enter account number to delete: ");
                        String accountToDelete = scanner.nextLine();
                        bankSystem.deleteAccount(accountToDelete);
                        break;
                    case 4:
                        System.out.print("Enter account number: ");
                        String accountForTransaction = scanner.nextLine();
                        System.out.print("Enter transaction type (deposit/withdrawal): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter transaction amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        bankSystem.addTransaction(accountForTransaction, type, amount);
                        System.out.println("Transaction added successfully.");
                        break;
                    case 5:
                        System.out.print("Enter account number to display sorted transactions: ");
                        String accountForDetails = scanner.nextLine();
                        System.out.println(bankSystem.displaySortedTransactions(accountForDetails));
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default: //invalid option entered
                        System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
