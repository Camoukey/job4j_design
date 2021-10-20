package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleChat {
    private final String path;
    private final String answersPath;
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private static final String OUT = "закончить";
    private String state = CONTINUE;
    private final List<String> answers = new ArrayList<>();

    public ConsoleChat(String path, String answersPath) {
        this.path = path;
        this.answersPath = answersPath;
    }

    public void run() {
        var rand = new Random();
        var input = new Scanner(System.in);
        var dialog = new StringBuilder();
        while (!state.equals(OUT)) {
            System.out.print("Ваша фраза: ");
            var question = input.nextLine();
            dialog.append(String.format("Пользователь: %s%n", question));
            switch (question) {
                case OUT:
                    state = OUT;
                    break;
                case STOP:
                    state = STOP;
                    break;
                case CONTINUE:
                    state = CONTINUE;
                    break;
                default:
            }
            if (state.equals(CONTINUE)) {
                var answer = getBotAnswer(rand);
                System.out.printf("Бот: %s%n", answer);
                dialog.append(String.format("Бот: %s%n", answer));
            }
        }
        writeToLog(dialog.toString());
    }

    private void writeToLog(String dialog) {
        try (var out = new PrintWriter(
                new FileWriter(path, Charset.forName("WINDOWS-1251")))) {
            out.append(dialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBotAnswer(Random rand) {
        if (answers.isEmpty()) {
            try (var in = new BufferedReader(
                    new FileReader(answersPath, StandardCharsets.UTF_8))) {
                in.lines().forEach(answers::add);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return answers.get(rand.nextInt(answers.size()));
    }

    public static void main(String[] args) {
        ConsoleChat chat = new ConsoleChat("./data/log.txt", "./data/bot_answers.txt");
        chat.run();
    }
}
