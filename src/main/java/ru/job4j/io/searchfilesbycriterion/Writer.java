package ru.job4j.io.searchfilesbycriterion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    private Writer() {
    }

    public static void stringBuilderToFile(StringBuilder data, String file) throws IOException {
        if (data.isEmpty()) {
            data.append("No items found matching your request");
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./data/"
                + file))) {
            out.write(data.toString());
        }
    }
}
