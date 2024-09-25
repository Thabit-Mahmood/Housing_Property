# Housing Property Management System

## Team Members
--------------------------------------------------------------------------------------------------
| Name                         | Student ID |              Email            | Lecture | Tutorial |
|------------------------------|------------|-------------------------------|---------|----------|
| SALMA IBRAHIM MOHMED         | 1221302021 | 1221302021@student.mmu.edu.my |  TC1L   |   TT1L   |
| ABDULKAFI WALEED             | 1201102665 | 1201102665@student.mmu.edu.my |  TC1L   |   TT1L   |
| BOUKHARI, ABD ERRAHMANE      | 1221301202 | 1221301202@student.mmu.edu.my |  TC1L   |   TT1L   |
--------------------------------------------------------------------------------------------------


## Introduction

Welcome to the **Housing Property Management System**, a comprehensive application designed to manage properties, transactions, and projects. Users can search for properties based on criteria like size, price, and facilities, view detailed information, and execute property transactions seamlessly. Sellers can submit properties for approval, and admins can manage property listings efficiently.

---

## Features

### 1. **Property Search**
   - Users can filter properties by:
     - **Size** (SqFt)
     - **Price** (min-max range)
     - **Location**
     - **Facilities**
     - **Project Name**
   - Results are displayed with property details like address, price, and facilities.

### 2. **Property Details**
   - For each property, detailed information such as size, facilities, and address is displayed.
   - Users can buy properties, which will automatically be removed from the listings and recorded in the transactions.

### 3. **Transaction Management**
   - View transaction history for each project, displaying the last five historical transactions.
   - All completed property purchases are logged into the transactions CSV file.

### 4. **Project Management**
   - Admin users can manage projects, view all projects, and associate properties with sellers.
   - Sellers can submit new properties for admin approval, which get stored in a pending state until approved.

### 5. **User Roles**
   - **Customer**: Search and purchase properties.
   - **Seller**: Add properties for approval.
   - **Admin**: Approve/reject property submissions and manage projects.

---

## Installation

### Prerequisites

1. **Java**: Ensure Java JDK 8 or higher is installed.
2. **JavaFX**: Make sure JavaFX is installed and configured correctly.
3. **CSV Files**: Ensure the required CSV files (`properties.csv`, `transactions.csv`, `projects.csv`) are available in the `resources` directory.

---

## Execution

### Running the Project on **Visual Studio Code**:


1. **Open the Project in VSCode**:
   - Open VSCode and select `File > Open Folder` and navigate to the cloned repository.

2. **Set Up Extensions**:
   - Install the "Java Extension Pack" if not already installed.
   - Ensure the project structure is recognized by VSCode.

3. **Configure JavaFX**:
   - Go to `settings.json` and add the following configuration:
     ```json
     "java.project.referencedLibraries": [
       "path/to/javafx-sdk-11/lib/**/*.jar"
     ],
     "javaFX.runConfigurations": {
       "mainClass": "view.MainView",
       "vmArgs": "--module-path path/to/javafx-sdk-11/lib --add-modules javafx.controls,javafx.fxml"
     }
     ```

4. **Run the Project**:
   - Right-click on `MainView.java` and select `Run Java`.
   - Alternatively, run the project by navigating to `Run > Run Without Debugging`.

### Running the Project on **BlueJ**:

1. **Open BlueJ** and **Open Project**:
   - Download and unzip the project files.
   - Open BlueJ, and use `Project > Open Project` to navigate to the project folder.

2. **Set Up JavaFX in BlueJ**:
   - Add JavaFX JARs to the `BlueJ Libraries`.
   - Go to `Preferences > Libraries` and add the following:
     ```
     path/to/javafx-sdk-11/lib/*.jar
     ```

3. **Run the Main Class**:
   - Right-click `MainView` in the `view` package and select `void main(String[] args)` to run the application.
   - Make sure JavaFX modules are properly loaded.

---

### Key Classes

- **PropertyController.java**: Manages property search, submission, approval, and removal.
- **TransactionController.java**: Manages transactions and transaction history.
- **FileHandler.java**: Handles loading and saving of CSV data for properties, transactions, and projects.
- **MainView.java**: Main entry point for the application, manages user interactions.
- **CustomerDashboardView.java**: Displays the customer dashboard, property search, and transaction history.
- **SellerDashboardView.java**: Displays the seller dashboard for property submission.
- **AdminDashboardView.java**: Displays the admin dashboard for managing property approvals.

---

## Usage

### For Customers:
- **Search** for properties using the filter options for size, price, facilities, and project name.
- **View Details** of selected properties.
- **Buy Properties**, which will be logged into the transaction history and removed from the listings.

### For Sellers:
- **Submit Properties** for admin approval with necessary details such as size, price, facilities, and project name.
- **View Submitted Properties** and check approval status.

### For Admins:
- **Approve or Reject** properties submitted by sellers.
- **Manage Projects** and view all properties associated with projects.

---

## Troubleshooting

### Common Issues:

1. **Application Not Launching**:
   - Ensure JavaFX is correctly installed and configured in the `PATH`.
   - Make sure the CSV files (`properties.csv`, `transactions.csv`, `projects.csv`) are in the `resources` folder.

2. **Property Not Found in Search**:
   - Ensure the properties CSV file is correctly formatted.
   - Verify that the correct filters are applied.

3. **Transactions Not Displaying**:
   - Check the `transactions.csv` file to ensure transactions are being logged correctly.

---




