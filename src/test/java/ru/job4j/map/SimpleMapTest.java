package ru.job4j.map;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleMapTest {

    @Test
    public void whenAdd() {
        Map<String, Integer> map = new SimpleMap<>();
        assertTrue(map.put("key1", 1));
        assertTrue(map.put("key2", 2));
        assertTrue(map.put("key3", 3));
    }

    @Test
    public void whenAddDuplicate() {
        Map<String, Integer> map = new SimpleMap<>();
        assertTrue(map.put("key1", 1));
        assertTrue(map.put("key2", 2));
        assertFalse(map.put("key2", 2));
    }

    @Test
    public void whenGet() {
        Map<String, Integer> map = new SimpleMap<>();
        map.put("key1", 1);
        Integer rsl = map.get("key1");
        assertThat(rsl, is(1));
    }

    @Test
    public void whenGetNull() {
        Map<String, Integer> map = new SimpleMap<>();
        map.put("key1", 1);
        var result = map.get(null);
        assertNull(result);
    }

    @Test
    public void whenRemoveItem() {
        Map<String, Integer> map = new SimpleMap<>();
        map.put("key1", 1);
        assertTrue(map.remove("key1"));
    }

    @Test
    public void whenRemoveMissingItem() {
        Map<String, Integer> map = new SimpleMap<>();
        map.put("key1", 1);
        assertFalse(map.remove("key2"));
    }
}
