import java.util.*;


// Custom insertionSort
// Sort based on most recent 4 Transactions
class SortUtils {
    public static void insertionSort(List<Transaction> transactions) {
        for (int i = 1; i < transactions.size(); i++) {
            Transaction key = transactions.get(i); // current transaction being compared
            int j = i - 1; // compare key with previous element in the list

            // Move elements greater than key one position ahead
            while (j >= 0 && transactions.get(j).getAmount() > key.getAmount()) {
                transactions.set(j + 1, transactions.get(j)); // checks if j is greater than key amount
                j--;
            }
            transactions.set(j + 1, key);
        }
    }
}
