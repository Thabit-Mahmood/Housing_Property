package main.java.util;

import java.util.List;
import java.util.stream.Collectors;

public class DropdownAutoSuggest {
    private List<String> projectNames;

    public DropdownAutoSuggest(List<String> projectNames) {
        this.projectNames = projectNames;
    }

    public List<String> getSuggestions(String input) {
        return projectNames.stream()
            .filter(name -> name.toLowerCase().startsWith(input.toLowerCase()))
            .collect(Collectors.toList());
    }
}