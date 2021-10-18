package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {

    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }
            String[] tmp = text.toString().split(System.lineSeparator());
            for (String s : tmp) {
                int num = Integer.parseInt(s);
                String result = num % 2 == 0 ? " this is even" : " this is odd";
                System.out.println(num + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
