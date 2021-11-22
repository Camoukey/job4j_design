package ru.job4j.io.searchfilesbycriterion;

import java.util.Map;
import java.util.function.Predicate;

public class ParameterDescription {
    private final String name;
    private final Map<String, Predicate<String>> patternSearch;
    private final String prompt;

    public ParameterDescription(String name, Map<String, Predicate<String>> patternSearch, String prompt) {
        this.name = name;
        this.patternSearch = patternSearch;
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }

    public String getPrompt() {
        return prompt;
    }

    public Map<String, Predicate<String>> getPatternSearch() {
        return patternSearch;
    }
}
