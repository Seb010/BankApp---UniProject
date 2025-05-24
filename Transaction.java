import java.time.LocalDate;
import java.util.*;


//Transaction attributes
class Transaction {
    private String type;
    private double amount;
    private LocalDate date;

    public Transaction(String type, double amount, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {

        return type;
    }

    public double getAmount() {

        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    // Info displayed for view transactions
    @Override
    public String toString() {

        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}

