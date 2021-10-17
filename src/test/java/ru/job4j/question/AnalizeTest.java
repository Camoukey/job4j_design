package ru.job4j.question;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AnalizeTest {

    @Test
    public void whenNotChanged() {
        User user1 = new User(1, "Ivan");
        User user2 = new User(2, "Petya");
        User user3 = new User(3, "Fedor");
        Set<User> previous = Set.of(user1, user2, user3);
        Set<User> current = Set.of(user1, user2, user3);
        assertThat(
                Analize.diff(previous, current),
                is(new Info(0, 0, 0))
        );
    }

    @Test
    public void whenAllAction() {
        User user1 = new User(1, "Ivan");
        User user2 = new User(2, "Petya");
        User user3 = new User(3, "Fedor");
        Set<User> previous = Set.of(user1, user2, user3);
        Set<User> current = Set.of(
                new User(1, "Ivan"),
                new User(2, "Misha"),
                new User(4, "Dima")
        );
        assertThat(
                Analize.diff(previous, current),
                is(new Info(1, 1, 1))
        );
    }

    @Test
    public void whenChanged() {
        User user1 = new User(1, "Ivan");
        User user2 = new User(2, "Petya");
        User user3 = new User(3, "Fedor");
        Set<User> previous = Set.of(user1, user2, user3);
        Set<User> current = Set.of(user1, new User(2, "Dima"), user3);
        assertThat(
                Analize.diff(previous, current),
                is(new Info(0, 1, 0))
        );
    }

    @Test
    public void whenDeleted() {
        User user1 = new User(1, "Ivan");
        User user2 = new User(2, "Petya");
        User user3 = new User(3, "Fedor");
        Set<User> previous = Set.of(user1, user2, user3);
        Set<User> current = Set.of(user1, user3);
        assertThat(
                Analize.diff(previous, current),
                is(new Info(0, 0, 1))
        );
    }

    @Test
    public void whenAdded() {
        User user1 = new User(1, "Ivan");
        User user2 = new User(2, "Petya");
        User user3 = new User(3, "Fedor");
        Set<User> previous = Set.of(user1, user2, user3);
        Set<User> current = Set.of(user1, user2, user3, new User(4, "Dima"));
        assertThat(
                Analize.diff(previous, current),
                is(new Info(1, 0, 0))
        );
    }
}
