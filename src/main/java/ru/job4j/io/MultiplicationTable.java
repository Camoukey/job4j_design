package ru.job4j.io;

import java.io.FileOutputStream;

public class MultiplicationTable {

    /**
     * Prints the multiplication table up to the value passed in the argument
     *
     * @param size the value to which the table will be calculated
     * @return two-dimensional array containing the calculated multiplication table
     */
    public static int[][] multiple(int size) {
        int[][] table = new int[size][size];
        for (int row = 0; row < table.length; row++) {
            for (int cell = 0; cell < table[row].length; cell++) {
                table[row][cell] = (row + 1) * (cell + 1);
            }
        }
        return table;
    }

    public static void main(String[] args) {
        int[][] result = multiple(10);

        try (FileOutputStream out = new FileOutputStream("result.txt")) {
            for (int[] ints : result) {
                for (int anInt : ints) {
                    out.write((anInt + "    ").getBytes());
                }
                out.write(System.lineSeparator().getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
