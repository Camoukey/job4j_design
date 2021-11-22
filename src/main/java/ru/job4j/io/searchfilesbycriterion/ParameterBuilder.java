package ru.job4j.io.searchfilesbycriterion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ParameterBuilder {
    private String name;
    private final Map<String, Predicate<String>> parameterValidation = new HashMap<>();
    private String prompt;

    ParameterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    ParameterBuilder addParameterValidation(String nameValidation, Predicate<String> pattern) {
        this.parameterValidation.put(nameValidation, pattern);
        return this;
    }

    ParameterBuilder setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    ParameterDescription build() {
        return new ParameterDescription(name, parameterValidation, prompt);
    }
}
