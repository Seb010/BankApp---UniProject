import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class BankApp extends JFrame {
    private BankSystem bankSystem;

    public BankApp() {
        bankSystem = new BankSystem(); // Backend logic

        // Main frame properties
        setTitle("Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); //centre the UI

        // Main menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton createAccountButton = new JButton("Create Account");
        JButton displayAccountsButton = new JButton("Display Accounts");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton addTransactionButton = new JButton("Add Transaction");
        JButton viewTransactionsButton = new JButton("View Transactions");
        JButton exitApplicationButton = new JButton("Exit Application");

        // Add buttons to panel
        menuPanel.add(createAccountButton);
        menuPanel.add(displayAccountsButton);
        menuPanel.add(deleteAccountButton);
        menuPanel.add(addTransactionButton);
        menuPanel.add(viewTransactionsButton);
        menuPanel.add(exitApplicationButton);

        // Add menu panel to frame
        add(menuPanel, BorderLayout.CENTER); //center all buttons inside of UI

        // Button actions
        createAccountButton.addActionListener(e -> openCreateAccountForm());
        displayAccountsButton.addActionListener(e -> openDisplayAccounts());
        deleteAccountButton.addActionListener(e -> openDeleteAccountForm());
        addTransactionButton.addActionListener(e -> openAddTransactionForm());
        viewTransactionsButton.addActionListener(e -> openViewTransactionsForm());
        exitApplicationButton.addActionListener(e -> exitApplication());

        setVisible(true);
    }

    private void openCreateAccountForm() {
        new CreateAccountForm(this, bankSystem);
    }

    private void openDisplayAccounts() {
        new DisplayAccountsFrame(bankSystem);
    }

    private void openDeleteAccountForm() {
        new DeleteAccountForm(this, bankSystem);
    }

    private void openAddTransactionForm() {
        new AddTransactionForm(this, bankSystem);
    }

    private void openViewTransactionsForm() {
        new ViewTransactionsForm(this, bankSystem);
    }

    private void exitApplication() {
        int confirmationChoice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Application", JOptionPane.YES_NO_OPTION);
        if (confirmationChoice == JOptionPane.YES_OPTION) {

            //3-second Delay before closing application
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            System.out.println("Closing Application....");
            System.exit(0);

        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankApp::new);
    }
}

class CreateAccountForm extends JDialog {
    public CreateAccountForm(JFrame parent, BankSystem bankSystem) {
        super(parent, "Create Account", true);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        JTextField accountNumberField = new JTextField();

        JLabel nameLabel = new JLabel("Holder Name:");
        JTextField nameField = new JTextField();

        JLabel addressLabel = new JLabel("Holder Address:");
        JTextArea addressField = new JTextArea();

        JLabel openingDateLabel = new JLabel("Opening Date (YYYY-MM-DD):");
        JTextField openingDateField = new JTextField();

        JLabel balanceLabel = new JLabel("Initial Balance:");
        JTextField balanceField = new JTextField();

        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");

        add(accountNumberLabel);
        add(accountNumberField);
        add(nameLabel);
        add(nameField);
        add(addressLabel);
        add(addressField);
        add(openingDateLabel);
        add(openingDateField);
        add(balanceLabel);
        add(balanceField);
        add(createButton);
        add(cancelButton);

        createButton.addActionListener(e -> {
            try {
                String accountNumber = accountNumberField.getText().trim();
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                LocalDate openingDate = LocalDate.parse(openingDateField.getText().trim());
                double balance = Double.parseDouble(balanceField.getText().trim());

                //Empty text field validation
                if (accountNumber.trim().isBlank() || name.trim().isBlank() || address.trim().isBlank()){
                    throw new RuntimeException("Form has not been completed");
                }

                bankSystem.createAccount(accountNumber, name, address, openingDate, balance);
                JOptionPane.showMessageDialog(this, "Account created successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelButton.addActionListener(e -> dispose());
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}

class AddTransactionForm extends JDialog {
    public AddTransactionForm(JFrame parent, BankSystem bankSystem) {
        super(parent, "Add Transaction", true);
        setSize(400, 200);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        JTextField accountNumberField = new JTextField();

        JLabel typeLabel = new JLabel("Transaction Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"deposit", "withdrawal"});

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        add(accountNumberLabel);
        add(accountNumberField);
        add(typeLabel);
        add(typeComboBox);
        add(amountLabel);
        add(amountField);
        add(addButton);
        add(cancelButton);

        addButton.addActionListener(e -> {
            try {
                String accountNumber = accountNumberField.getText();
                String type = (String) typeComboBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());

                bankSystem.addTransaction(accountNumber, type, amount);

                if (type.equals("deposit")){
                    JOptionPane.showMessageDialog(this, "Deposit added successfully");
                    dispose();
                }
                if (type.equals("withdrawal")){
                    JOptionPane.showMessageDialog(this, "Withdraw completed successfully");
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}

class DisplayAccountsFrame extends JFrame {
    public DisplayAccountsFrame(BankSystem bankSystem) {
        setTitle("Accounts List");
        setSize(500, 400);
        setLayout(new BorderLayout());

        String[] columnNames = {"Account Number", "Holder Name", "Address", "Opening Date", "Balance"};
        Object[][] data = bankSystem.getAccountsData(); // Custom method in `BankSystem` to return account data

        JTable accountsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(accountsTable);

        add(scrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class ViewTransactionsForm extends JDialog {
    public ViewTransactionsForm(JFrame parent, BankSystem bankSystem) {
        super(parent, "View Transactions", true);
        setSize(500, 300);
        setLayout(new BorderLayout());

        JLabel accountNumberLabel = new JLabel("Account Number:");
        JTextField accountNumberField = new JTextField(15);
        JButton viewButton = new JButton("View");

        JPanel inputPanel = new JPanel();
        inputPanel.add(accountNumberLabel);
        inputPanel.add(accountNumberField);
        inputPanel.add(viewButton);

        JTextArea transactionsArea = new JTextArea(4,15);
        transactionsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionsArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            try {
                String transactions = bankSystem.displaySortedTransactions(accountNumber); // Custom method in `BankSystem`
                transactionsArea.setText(transactions);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}

class DeleteAccountForm extends JDialog {
    public DeleteAccountForm(JFrame parent, BankSystem bankSystem) {
        super(parent, "Delete Account", true);
        setSize(300, 150);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel accountNumberLabel = new JLabel("Enter Account Number:");
        JTextField accountNumberField = new JTextField();
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(accountNumberLabel, BorderLayout.NORTH);
        inputPanel.add(accountNumberField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        add(inputPanel);
        add(buttonPanel);

        // Delete button action
        deleteButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            try {
                boolean isDeleted = bankSystem.deleteAccount(accountNumber); // Custom method in BankSystem
                if (isDeleted) {
                    JOptionPane.showMessageDialog(this, "Account with Account Number: "  + accountNumber + " deleted successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Cancel button action
        cancelButton.addActionListener(e -> dispose());

        setLocationRelativeTo(parent);
        setVisible(true);
    }
}

