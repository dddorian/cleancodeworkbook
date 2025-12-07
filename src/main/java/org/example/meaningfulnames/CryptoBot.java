package org.example.meaningfulnames;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class CryptoBot {
    private static final double FEE_PERCENTAGE = 0.02;
    private double balance;  // balance
    private int transactionCount;     // transaction count
    private List<String> transactionHistory;  // history

    public void transfer(double amount, String currencyCode) {
        if (isValidTransaction(amount, currencyCode)) {
            updateBalance(amount);
            recordTransaction(currencyCode, amount);
        }
    }

    private void updateBalance(double amount) {
        balance += amount;
        transactionCount++;
    }

    private void recordTransaction(String currencyCode, double amount) {
        requireNonNull(transactionHistory);
        transactionHistory.add(currencyCode + ":" + amount);
    }

    private boolean isValidTransaction(double amount, String currencyCode) {
        return amount > 0 && currencyCode != null;
    }

    public double calculateTransactionFees() {
        return balance * FEE_PERCENTAGE;  // calculate fees
    }

    public List<Transaction> filterByDayAndStatus(int day, int status) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (isTransactionFromDay(transaction, day) &&
                    hasTransactionStatus(transaction, status)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    boolean isTransactionFromDay(Transaction transaction, int day) {
        return transaction.getDate().getDayOfYear() == day;
    }

    boolean hasTransactionStatus(Transaction transaction, int status) {
        return transaction.getStatus().getValue() == status;
    }


}
