package ru.job4j.io;

import java.io.*;
import java.util.*;

public class CSVReader {
    private static final Map<String, String> ARGUMENTS = new HashMap<>();

    public static void handle(ArgsName argsName) throws IllegalArgumentException, FileNotFoundException {
        validate(argsName);
        String[] filters = ARGUMENTS.get(Params.FILTER.getCode()).split(",");
        List<Integer> index = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(ARGUMENTS.get(Params.PATH.getCode())));
             PrintStream stream = "stdout".equals(ARGUMENTS.get(Params.OUT.getCode())) ? System.out
                    : new PrintStream(new FileOutputStream(ARGUMENTS.get(Params.OUT.getCode()), true))) {
            while (scanner.hasNextLine()) {
                String string = scanner.nextLine();
                String[] array = string.split(ARGUMENTS.get(Params.DELIMITER.getCode()));
                if (index.isEmpty()) {
                    index = filterIndex(array, filters);
                }
                String resultString = filter(array, index, ARGUMENTS.get(Params.DELIMITER.getCode()));
                stream.print(resultString);
            }
        }
    }

    private static void validate(ArgsName argsName) throws IllegalArgumentException {
        for (Params parameter : Params.values()) {
            String paramName = parameter.getCode();
            String argument = argsName.get(paramName);
            if (argument.isEmpty()) {
                throw new IllegalArgumentException(paramName + " key argument is invalid");
            }
            ARGUMENTS.put(paramName, argument);
        }
    }

    private static List<Integer> filterIndex(String[] array, String[] filters) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (String filter : filters) {
                if (filter.equals(array[i])) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    private static String filter(String[] array, List<Integer> indexes, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (int index : indexes) {
            joiner.add(array[index]);
        }
        return joiner + System.lineSeparator();
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsObj = ArgsName.of(args);
        CSVReader.handle(argsObj);
    }

    private enum Params {
        PATH("path"), OUT("out"), DELIMITER("delimiter"), FILTER("filter");
        private final String code;

        Params(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
