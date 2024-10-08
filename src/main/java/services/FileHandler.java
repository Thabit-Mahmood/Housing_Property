package services;

import model.Property;
import model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    // Save transactions to the CSV file in the correct format
    public void saveTransactionsToCSV(List<Transaction> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsCSVPath, false))) {
            // Write header
            bw.write("ProjectName,Address,Size,Price");
            bw.newLine();

            for (Transaction transaction : transactions) {
                bw.write(transaction.getProjectName() + ",\"" + transaction.getAddress() + "\"," +
                        transaction.getSize() + "," + transaction.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.severe("Error saving transactions: " + e.getMessage());
        }
    }

    // Remove a property from the CSV
    public void removePropertyFromCSV(Property property) {
        List<Property> properties = loadPropertiesFromCSV();  // Load all properties

        // Remove the purchased property
        properties.removeIf(p -> p.getAddress().equals(property.getAddress()));

        // Save the properties back to the file
        savePropertiesToFile(properties);

        // Call method to remove any duplicates in the CSV twice for safety
        removeDuplicatesFromCSV();
        removeDuplicatesFromCSV(); // Second pass to ensure all duplicates are removed
    }

    // This method will remove duplicated entries in the CSV
    private void removeDuplicatesFromCSV() {
        List<Property> properties = loadPropertiesFromCSV();  // Load all properties

        // Create a list to store unique properties
        List<Property> uniqueProperties = new ArrayList<>();

        // Use a set to keep track of seen addresses (or other fields to identify uniqueness)
        Set<String> seenAddresses = new HashSet<>();

        for (Property property : properties) {
            // Check if the property address has already been processed
            if (!seenAddresses.contains(property.getAddress())) {
                // Add to the unique list and mark the address as processed
                uniqueProperties.add(property);
                seenAddresses.add(property.getAddress());
            }
        }

        // Now save the unique properties back to the CSV file
        savePropertiesToFile(uniqueProperties);
    }



    // Add a new transaction to the CSV
    public void addTransactionToCSV(Transaction transaction) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsCSVPath, true))) {
            bw.write(transaction.getProjectName() + "," + transaction.getAddress() + "," +
                     transaction.getSize() + "," + transaction.getPrice());
            bw.newLine();
        } catch (IOException e) {
            logger.severe("Error saving transaction: " + e.getMessage());
        }
    }

    public void savePropertiesToFile(List<Property> properties) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(propertiesCSVPath, false))) {
            // Write the correct header
            bw.write("DateOfValuation,Size SqM,SqFt,PropertyType,NoOfFloors,ADDRESS,SCHEME,PRICE,YEAR,pricePerSqft,Seller");
            bw.newLine();
    
            for (Property property : properties) {
                String dateOfValuation = property.getDateOfValuation() != null ? property.getDateOfValuation() : "N/A";
                String noOfFloors = property.getNoOfFloors() != null ? property.getNoOfFloors() : "N/A";
                String year = property.getYear() != null ? property.getYear() : "N/A";
                String pricePerSqFt = property.getPricePerSqFt() != null ? property.getPricePerSqFt() : "N/A";
    
                // Format each line properly based on its type
                bw.write(String.format(
                        "%s,%.2f,%.2f,%s,%s,\"%s\",%s,%.2f,%s,%s,%s",
                        dateOfValuation,                   // String for DateOfValuation
                        property.getSize() / 10.7639,      // Convert SqFt to SqM
                        property.getSize(),                // SqFt
                        property.getFacilities(),          // String for PropertyType (Facilities)
                        noOfFloors,                        // String for NoOfFloors
                        property.getAddress(),             // String for Address
                        property.getProjectName(),         // String for Scheme (Project Name)
                        property.getPrice(),               // Double for Price
                        year,                              // String for Year
                        pricePerSqFt,                      // String for pricePerSqft
                        property.getSellerUsername()       // String for SellerUsername
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            logger.severe("Error saving properties: " + e.getMessage());
        }
    }

    public void savePendingPropertyToCSV(Property property) {
        // First, load existing pending properties to check for duplicates
        List<Property> existingProperties = loadPendingPropertiesFromCSV();

        // Check for duplicates
        boolean isDuplicate = existingProperties.stream()
                .anyMatch(p -> p.getAddress().equals(property.getAddress())
                        && p.getProjectName().equals(property.getProjectName())
                        && p.getPrice() == property.getPrice()
                        && p.getSize() == property.getSize()); // Added size check

        // If it's a duplicate, do not save it
        if (isDuplicate) {
            logger.warning("Duplicate property not saved: " + property.getAddress());
            return; // Exit the method if the property is a duplicate
        }

        // Proceed to save the new property if it's not a duplicate
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pendingPropertiesCSVPath, true))) {
            bw.write(property.getSize() + "," + property.getPrice() + "," + property.getFacilities() + "," +
                    property.getProjectName() + "," + property.getAddress() + "," + property.getSellerUsername());
            bw.newLine();
        } catch (IOException e) {
            logger.severe("Error saving pending property: " + e.getMessage());
        }
    }


    // Load properties from the pending properties CSV file
    public List<Property> loadPendingPropertiesFromCSV() {
        List<Property> properties = new ArrayList<>();
        File file = new File(pendingPropertiesCSVPath);

        if (!file.exists()) {
            logger.severe("Pending properties CSV file does not exist.");
            return properties;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip the header if exists; add this only if you have a header in your pending properties CSV
            // br.readLine(); // Uncomment if there's a header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Adjust if you handle quoted data

                if (data.length >= 6) { // Ensure we have enough fields to create a Property object
                    String address = data[4].trim(); // Assuming address is the 5th field
                    String projectName = data[3].trim(); // Assuming project name is the 4th field
                    double price = Double.parseDouble(data[1].trim()); // Assuming price is the 2nd field
                    // Create a new Property object and add it to the list
                    Property property = new Property(Double.parseDouble(data[2].trim()), price, data[5].trim(), projectName, address);
                    properties.add(property);
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading pending properties CSV file: " + e.getMessage());
        }

        return properties;
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
                        property.setDateOfValuation(dateOfValuation);  // Set the DateOfValuation
                        property.setNoOfFloors(noOfFloors);            // Set the number of floors
                        property.setYear(year);                        // Set the year
                        property.setPricePerSqFt(pricePerSqFt);        // Set the price per SqFt
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