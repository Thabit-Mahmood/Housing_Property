package services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.Transaction;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());
    private static FileHandler instance;

    public FileHandler() {}

    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    public List<Transaction> readTransactions(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Reading transactions from file: " + filename);
            while ((line = br.readLine()) != null) {
                System.out.println("Processing line: " + line);
                String[] data = line.split(",");
                if (data.length == 4) {
                    String projectName = data[0].trim();
                    String address = data[1].trim();
                    String size = data[2].trim();
                    double price = Double.parseDouble(data[3].trim());
                    transactions.add(new Transaction(projectName, address, size, price));
                    System.out.println("Added transaction: " + projectName + ", " + address);
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
        }
        return transactions;
    }

    public List<Transaction> getRecentTransactions(String filename, String projectName) {
        List<Transaction> transactions = readTransactions(filename);
        List<Transaction> projectTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getProjectName().equalsIgnoreCase(projectName)) {
                projectTransactions.add(transaction);
            }
        }
        return projectTransactions.size() > 5 ? projectTransactions.subList(0, 5) : projectTransactions;
    }
}
