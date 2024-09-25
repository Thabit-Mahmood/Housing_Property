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
    private final String propertiesCSVPath = "src/resources/properties.csv"; // Properties CSV file path
    private final String transactionsCSVPath = "src/resources/transactions.csv"; // Transactions CSV file path
    private final String pendingPropertiesCSVPath = "src/resources/pending_properties.csv"; // New pending properties
                                                                                            // file

    public FileHandler() {
    }

    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    // Modify this method in FileHandler.java to properly load projects from CSV
    public List<String> loadProjectsFromCSV() {
        List<String> projectNames = new ArrayList<>();
        File file = new File("src/resources/projects.csv"); // Ensure the correct path to your projects file

        if (!file.exists()) {
            logger.severe("Projects CSV file does not exist.");
            return projectNames;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Read all project names from CSV
            while ((line = br.readLine()) != null) {
                String projectName = line.trim();
                if (!projectName.isEmpty()) {
                    projectNames.add(projectName); // Add the project name to the list
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading projects CSV file: " + e.getMessage());
        }

        return projectNames;
    }

    // Modify this method in ProjectService.java to use FileHandler
    public List<String> getAllProjectNames() {
        return FileHandler.getInstance().loadProjectsFromCSV(); // Fetch project names from the CSV file using
                                                                // FileHandler
    }

    // Read transactions from the CSV file
    public List<Transaction> loadTransactionsFromCSV() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(transactionsCSVPath);

        if (!file.exists()) {
            logger.severe("CSV file does not exist at " + transactionsCSVPath);
            return transactions;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line); // Properly handle quoted CSV lines
                if (data.length == 4) { // Ensure valid format
                    String projectName = data[0].trim();
                    String address = data[1].trim();
                    String size = data[2].trim();
                    double price = Double.parseDouble(data[3].trim());

                    transactions.add(new Transaction(projectName, address, size, price));
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading transactions CSV file: " + e.getMessage());
        }

        System.out.println("Total transactions loaded: " + transactions.size()); // Debugging
        return transactions;
    }

    // Save transactions to the CSV file
    public void saveTransactionsToCSV(List<Transaction> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsCSVPath, false))) {
            // Write header
            bw.write("ProjectName,Address,Size,Price");
            bw.newLine();

            for (Transaction transaction : transactions) {
                bw.write(transaction.getProjectName() + "," + transaction.getAddress() + "," +
                        transaction.getSize() + "," + transaction.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.severe("Error saving transactions: " + e.getMessage());
        }
    }

    // Save properties to the CSV file (for writing approved properties to CSV)
    public void savePropertiesToFile(List<Property> properties) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(propertiesCSVPath, false))) {
            // Write header
            bw.write(
                    "DateOfValuation,SizeSqM,SizeSqFt,Facilities,NoOfFloors,Address,ProjectName,Price,Year,PricePerSqFt,SellerUsername");
            bw.newLine();

            for (Property property : properties) {
                bw.write(property.getSize() + "," + property.getPrice() + "," + property.getFacilities() + "," +
                        property.getProjectName() + "," + property.getAddress() + "," + property.getSellerUsername());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.severe("Error saving properties: " + e.getMessage());
        }
    }

    // Save pending property to file with seller association
    public void savePendingPropertyToCSV(Property property) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pendingPropertiesCSVPath, true))) {
            bw.write(property.getSize() + "," + property.getPrice() + "," + property.getFacilities() + "," +
                    property.getProjectName() + "," + property.getAddress() + "," + property.getSellerUsername());
            bw.newLine();
        } catch (IOException e) {
            logger.severe("Error saving pending property: " + e.getMessage());
        }
    }

    // Load properties from CSV file (approved properties)
    public List<Property> loadPropertiesFromCSV() {
        List<Property> properties = new ArrayList<>();
        File file = new File(propertiesCSVPath);

        if (!file.exists()) {
            logger.severe("Properties CSV file does not exist.");
            return properties;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line);

                if (data.length >= 11) {
                    try {
                        // Parse fields based on the provided CSV format
                        @SuppressWarnings("unused")
                        String dateOfValuation = data[0].trim();
                        @SuppressWarnings("unused")
                        double sizeSqM = tryParseDouble(data[1].trim(), "Size SqM");
                        double sizeSqFt = tryParseDouble(data[2].trim(), "Size SqFt");
                        String propertyType = data[3].trim();
                        @SuppressWarnings("unused")
                        String noOfFloors = data[4].trim();
                        String address = data[5].trim();
                        String projectName = data[6].trim();
                        double price = tryParseDouble(data[7].trim(), "Price");
                        @SuppressWarnings("unused")
                        String year = data[8].trim(); // Not used directly
                        @SuppressWarnings("unused")
                        String pricePerSqFt = data[9].trim(); // Not used directly
                        String sellerUsername = data.length == 11 ? data[10].trim() : ""; // Ensure the seller is
                                                                                          // present

                        // Create Property object with the parsed data
                        Property property = new Property(sizeSqFt, price, propertyType, projectName, address);
                        property.setSellerUsername(sellerUsername);
                        properties.add(property);

                    } catch (NumberFormatException e) {
                        logger.warning(
                                "Skipping malformed numeric field in line: " + line + ". Error: " + e.getMessage());
                    }
                } else {
                    logger.warning("Skipping malformed line (incorrect number of fields): " + line);
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading properties CSV file: " + e.getMessage());
        }

        return properties;
    }

    // Helper method to safely parse double values, with detailed logging
    private double tryParseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            logger.severe("Error parsing field '" + fieldName + "': " + value);
            throw e; // Re-throw the exception after logging the issue
        }
    }

    // Parse CSV line while handling commas inside quotes properly
    private String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean insideQuote = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote; // Toggle the insideQuote flag
            } else if (c == ',' && !insideQuote) {
                // If we're not inside quotes, add the current token to the list
                tokens.add(currentToken.toString().trim());
                currentToken.setLength(0); // Clear the current token
            } else {
                // Otherwise, just append the character to the current token
                currentToken.append(c);
            }
        }

        // Add the last token
        tokens.add(currentToken.toString().trim());

        return tokens.toArray(new String[0]);
    }

}