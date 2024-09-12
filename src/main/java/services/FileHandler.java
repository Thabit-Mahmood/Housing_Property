package main.java.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Transaction;

public class FileHandler {
    public List<Transaction> readTransactions(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String projectName = data[0].trim();
                String address = data[1].trim();
                String size = data[2].trim();
                double price = Double.parseDouble(data[3].trim());
                transactions.add(new Transaction(projectName, address, size, price));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return transactions;
    }

    public List<Transaction> getRecentTransactions(List<Transaction> transactions, String projectName) {
        List<Transaction> projectTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getProjectName().equals(projectName)) {
                projectTransactions.add(transaction);
            }
        }
        return projectTransactions.size() > 5 ? projectTransactions.subList(0, 5) : projectTransactions;
    }
}