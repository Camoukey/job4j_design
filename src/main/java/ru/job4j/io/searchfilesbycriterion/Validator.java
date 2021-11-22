package ru.job4j.io.searchfilesbycriterion;

import ru.job4j.io.ArgsName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {

    private Validator() {
    }

    public static Map<String, String> validationStart(ArgsName argsObj, List<ParameterDescription> parameterDescriptions) {
        Map<String, String> checkedArguments = new HashMap<>();
        boolean isInvalidArguments = true;
        for (ParameterDescription parameterDescription : parameterDescriptions) {
            for (var parameter : parameterDescription.getPatternSearch().entrySet()) {
                String key = parameterDescription.getName();
                String value = argsObj.get(key);
                if (parameter.getValue().test(value)) {
                    checkedArguments.put(key, value);
                    isInvalidArguments = false;
                    break;
                }
            }
            if (isInvalidArguments) {
                throw new IllegalArgumentException(parameterDescription.getPrompt());
            }
        }
        return checkedArguments;
    }
}
