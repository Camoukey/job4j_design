package ru.job4j.io;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();
    private final static Pattern ARGS_KEY = Pattern.compile("^-([^=\\s]+)=([^=\\s]+)$");

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("Invalid key");
        }
        return values.get(key);
    }

    private void parse(String[] args) {
         Arrays.stream(args)
                .map(ARGS_KEY::matcher)
                 .filter(Matcher::matches)
                 .forEach(matcher -> values.put(matcher.group(1), matcher.group(2)));
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}
