package main.java.services;

import java.util.List;

public class FileHandler {
    private static FileHandler instance;

    private FileHandler() {}

    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    public List<Transaction> readTransactions(String filename) {
        // Implement file reading and parsing
        return null; // Placeholder
    }
}