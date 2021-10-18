package ru.job4j.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalizyTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenTwoDifferentFalls() throws IOException {
        String source = "./data/example1_log_server.txt";
        File target = folder.newFile("target.txt");
        Analizy analizy = new Analizy();
        analizy.unavailable(source, target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString(), is("10:57:01;10:59:01;11:01:02;11:02:02;"));
    }

    @Test
    public void whenSeveralFallsInARow() throws IOException {
        String source = "./data/example2_log_server.txt";
        File target = folder.newFile("target.txt");
        Analizy analizy = new Analizy();
        analizy.unavailable(source, target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString(), is("10:57:01;11:02:02;"));
    }
}
