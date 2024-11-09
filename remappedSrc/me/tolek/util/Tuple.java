package me.tolek.util;

public class Tuple <K, V> implements Cloneable {

    public K value1;
    public V value2;

    public Tuple(K value1, V value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

}
