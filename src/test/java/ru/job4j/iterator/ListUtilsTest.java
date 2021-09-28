package ru.job4j.iterator;

import org.junit.Test;

import org.hamcrest.core.Is;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);

        assertThat(input, Is.is(Arrays.asList(1, 2, 3)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 3, 2);
    }

    @Test
    public void whenAddAfterLast() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 2, 3);

        assertThat(input, Is.is(Arrays.asList(0, 1, 2, 3)));
    }

    @Test
    public void whenRemoveIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 28, 999, 123, 228));
        ListUtils.removeIf(input, p -> p == 28);
        assertThat(Arrays.asList(1, 999, 123, 228), Is.is(input));
    }

    @Test
    public void whenReplaceIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 28, 999, 123, 228));
        ListUtils.replaceIf(input, p -> p == 28, 8);
        assertThat(Arrays.asList(1, 8, 999, 123, 228), Is.is(input));
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> delElements = new ArrayList<>(Arrays.asList(1, 2));
        ListUtils.removeAll(input, delElements);
        assertThat(Arrays.asList(3, 4), Is.is(input));
    }

}
