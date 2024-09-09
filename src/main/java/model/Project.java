import java.util.List;

public class Project {
    private String projectName;
    private List<Property> properties;

    public void addProperty(Property property) {
        properties.add(property);
    }

    public List<Property> getProperties() {
        return properties;
    }

    // Method to get historical transactions
}
