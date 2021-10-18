package ru.job4j.io;

import java.io.*;

public class Analizy {

    public void unavailable(String source, String target) {
        try (
                var read = new BufferedReader(new FileReader(source));
                var out = new PrintWriter(
                        new BufferedOutputStream(
                                new FileOutputStream(target)))
        ) {
            String line;
            boolean status = true;
            while ((line = read.readLine()) != null) {
                String[] set = line.split(" ");
                int code = Integer.parseInt(set[0]);
                if (status && code <= 500 && code >= 400) {
                    status = false;
                    out.print(set[1] + ";");
                } else if (!status && code < 400) {
                    status = true;
                    out.println(set[1] + ";");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analizy analizy = new Analizy();
        analizy.unavailable("./data/example1_log_server.txt",
                "./data/example1_server_status.csv");
        analizy.unavailable("./data/example2_log_server.txt",
                "./data/example2_server_status.csv");
    }
}
