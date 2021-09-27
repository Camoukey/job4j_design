package ru.job4j.collection;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();
    private int sizeIn = 0;
    private int sizeOut = 0;

    public T poll() {
        if (sizeIn > 0) {
            while (sizeIn != 0) {
                out.push(in.pop());
                sizeOut++;
                sizeIn--;
            }
        }
        sizeOut--;
        return out.pop();
    }

    public void push(T value) {
        if (sizeOut > 0) {
            while (sizeOut != 0) {
                in.push(out.pop());
                sizeIn++;
                sizeOut--;
            }
        }
        in.push(value);
        sizeIn++;
    }
}
