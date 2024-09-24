package services;

import model.User;
import model.Customer;
import model.Seller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
  private static UserService instance;
  private List<User> users;
  private final String filePath = "src/resources/users.txt";

  private UserService() {
    users = new ArrayList<>();
    loadUsersFromFile();
  }

  public static synchronized UserService getInstance() {
    if (instance == null) {
      instance = new UserService();
    }
    return instance;
  }

  // Registers a new user and saves them to the file
  public void registerUser(String username, String password, String email, String role) {
    User newUser;
    if ("Customer".equalsIgnoreCase(role)) {
        newUser = new Customer(username, password, email);
    } else if ("Seller".equalsIgnoreCase(role)) {
        newUser = new Seller(username, password, email);
    } else {
        return;  // Should never reach this point if UserController correctly filters roles
    }
    users.add(newUser);
    saveUsersToFile();  // Save the newly registered user to 'users.txt'
}

  // Authenticates a user based on username and password
  public User authenticateUser(String username, String password) {
    return users.stream()
        .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
        .findFirst()
        .orElse(null);
  }

  private void loadUsersFromFile() {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        if (data.length == 4) {
          String username = data[0];
          String password = data[1];
          String role = data[2];
          String email = data[3];
          if ("Admin".equalsIgnoreCase(role)) {
            users.add(new model.Admin(username, password, email));
          } else if ("Customer".equalsIgnoreCase(role)) {
            users.add(new Customer(username, password, email));
          } else if ("Seller".equalsIgnoreCase(role)) {
            users.add(new Seller(username, password, email));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Saves users to the file
  private void saveUsersToFile() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
      for (User user : users) {
        bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole() + "," + user.getEmail());
        bw.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
