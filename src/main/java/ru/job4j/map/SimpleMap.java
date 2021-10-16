package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        int hash = hash(key.hashCode());
        int position = indexFor(hash);
        if (table[position] != null && !table[position].key.equals(key)) {
            return false;
        }
        table[position] = new MapEntry<>(key, value);
        count++;
        modCount++;
        if ((float) count / capacity >= LOAD_FACTOR) {
            expand();
        }
        return true;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    private void expand() {
        MapEntry<K, V>[] temp = table;
        capacity *= 2;
        count = 0;
        table = new MapEntry[capacity];
        for (MapEntry<K, V> el : temp) {
            if (el != null) {
                put(el.key, el.value);
                count++;
                modCount++;
            }
        }
    }

    @Override
    public V get(K key) {
        V result = null;
        if (key != null) {
            int position = indexFor(hash(key.hashCode()));
            if (table[position] != null && table[position].key.equals(key)) {
                result = table[position].value;
            }
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        var index = indexFor(hash(key.hashCode()));
        var result = table[index] != null && Objects.equals(key, table[index].key);
        if (result) {
            table[index] = null;
            count--;
            modCount++;
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private int cursor = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                var rsl = false;
                for (; cursor < table.length; cursor++) {
                    if (table[cursor] != null) {
                        rsl = true;
                        break;
                    }
                }
                return rsl;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[cursor++].key;
            }
        };
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
