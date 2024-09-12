package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TransactionController {
    public void displayTransactions(String filePath, String projectName) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(projectName)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
    }
}