package models;

import java.util.Objects;

public class Node<T> {

    private T value;
    private int x;
    private int y;

    public Node(T value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public T getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "N[" + value + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node<?> node)) return false;
        return Objects.equals(value, node.value)
                && x == node.x
                && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, x, y);
    }
}