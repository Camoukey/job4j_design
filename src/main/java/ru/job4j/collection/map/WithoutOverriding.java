package ru.job4j.collection.map;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class WithoutOverriding {

    public static void main(String[] args) {
        var user1 = new User("Иван", 0,
                new GregorianCalendar(2000, Calendar.JANUARY, 28));
        var user2 = new User("Иван", 0,
                new GregorianCalendar(2000, Calendar.JANUARY, 28));
        Map<User, Object> users = new HashMap<>();
        users.put(user1, new Object());
        users.put(user2, new Object());
        users.forEach((user, object) -> System.out.println("User: " + user + " Object " + object));
    }
}
