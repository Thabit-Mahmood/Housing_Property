package main.java.services;

import java.util.List;


public class PropertySearchService {
  private static PropertySearchService instance;

  private PropertySearchService() {}

  public static synchronized PropertySearchService getInstance() {
      if (instance == null) {
          instance = new PropertySearchService();
      }
      return instance;
  }

  // Search methods
  public List<Property> searchBySize(String size) { return null; }
  public List<Property> searchByPriceRange(double minPrice, double maxPrice) { return null; }
  public List<Property> searchByProjectName(String projectName) { return null; }
}
