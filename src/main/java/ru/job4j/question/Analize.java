package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int changed = 0;
        int deleted = 0;
        var currentMap = current.stream()
                .collect(Collectors.toMap(User::getId, User::getName));
        for (var user : previous) {
            int id = user.getId();
            if (!currentMap.containsKey(id)) {
                deleted++;
            } else if (!currentMap.get(id).equals(user.getName())) {
                changed++;
            }
        }
        return new Info(
                current.size() + deleted - previous.size(),
                changed,
                deleted
        );
    }
}
