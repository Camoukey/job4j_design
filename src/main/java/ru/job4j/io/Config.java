package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<>();
    private final static Pattern CORRECT_LINE = Pattern.compile("^(\\S+)=(\\S+)$");

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines()
                    .filter(line -> !line.isEmpty()
                                && !line.startsWith("#")
                                && CORRECT_LINE.matcher(line).matches())
                    .map(line -> line.split("="))
                    .forEach(words -> values.put(words[0], words[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("Неверный ключ, или значение.");
        }
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
    }

}

/*
* forEach(line -> {
                String[] tmp = line.split("=");
                values.put(tmp[0], tmp[1]);
            });
* */
