package ru.job4j.io;

import org.hamcrest.Matchers;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConfigTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Petr Arsentev"));
        assertThat(config.value("surname"), is(Matchers.nullValue()));
    }

    @Test
    public void whenPairWithComment() {
        String path = "./data/pair_with_comments_and_blanks.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name_pupil"), is("Ivan"));
        assertThat(config.value("age"), is("50"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenKeyWithError() {
        String path = "./data/pair_with_error_in_template.properties";
        Config config = new Config(path);
        config.load();
        config.value("it's_key");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPairWithMissprint() {
        String path = "./data/pair_with_misprint.properties";
        Config config = new Config(path);
        config.load();
        config.value("key");
    }
}
