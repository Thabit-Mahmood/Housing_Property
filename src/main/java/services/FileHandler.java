package services;

import model.Property;
import model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());
    private static FileHandler instance;
    private final String propertiesCSVPath = "src/resources/properties.csv"; // CSV file path

    public FileHandler() {}

    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    public List<Transaction> getRecentTransactions(String filename, String projectName) {
        List<Transaction> allTransactions = readTransactions(filename);
        List<Transaction> projectTransactions = new ArrayList<>();
    
        for (Transaction transaction : allTransactions) {
            if (transaction.getProjectName().equalsIgnoreCase(projectName)) {
                projectTransactions.add(transaction);
            }
        }
    
        return projectTransactions.size() > 5 ? projectTransactions.subList(0, 5) : projectTransactions;
    }
    
    // Read transactions from a file
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

    // Save properties to a file (for writing back to CSV)
    public void savePropertiesToFile(List<Property> properties) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(propertiesCSVPath, false))) {
            for (Property property : properties) {
                bw.write(property.getSize() + "," + property.getPrice() + "," + property.getFacilities() + "," +
                        property.getProjectName() + "," + property.getAddress());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.severe("Error saving properties: " + e.getMessage());
        }
    }

    // Load properties from a CSV file
    public List<Property> loadPropertiesFromCSV() {
        List<Property> properties = new ArrayList<>();
        File file = new File(propertiesCSVPath);

        // Check if the file exists; if not, log error
        if (!file.exists()) {
            logger.severe("CSV file does not exist at " + propertiesCSVPath);
            return properties;
        }

        // Read properties from the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Skip the header line
            br.readLine(); // This will skip the first line, which is the header

            while ((line = br.readLine()) != null) {
                // Split by comma, assuming no commas in values (this can be enhanced with a CSV parser)
                String[] data = parseCSVLine(line);
                if (data.length == 10) { // Ensure it has the expected number of fields
                    @SuppressWarnings("unused")
                    double sizeSqM = Double.parseDouble(data[1].trim());
                    double sizeSqFt = Double.parseDouble(data[2].trim());
                    String propertyType = data[3].trim();
                    String address = data[5].trim();
                    String scheme = data[6].trim();
                    double price = Double.parseDouble(data[7].trim());
                    String projectName = scheme; // We treat scheme as project name

                    properties.add(new Property(sizeSqFt, price, propertyType, projectName, address));
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading properties CSV file: " + e.getMessage());
        }
        return properties;
    }

    // Helper method to parse CSV lines (handling commas inside fields)
    private String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // Toggle state when encountering quotes
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString().trim()); // Add the token if we're not inside quotes
                sb.setLength(0); // Clear the StringBuilder
            } else {
                sb.append(c); // Add character to the token
            }
        }

        tokens.add(sb.toString().trim()); // Add the last token
        return tokens.toArray(new String[0]); // Convert to array
    }
}
