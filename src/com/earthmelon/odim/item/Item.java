package com.earthmelon.odim.item;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Item implements Serializable {
    public static int ID_COUNT = 0;

    // First entry is row count, second is column count.
    List<Integer> size;

    // Measured as an array as such: [platinum, gold, silver, bronze]
    int[] value;

    String name;

    String description;

    Image image;

    int id;

    public Item(String name, String description, int rows, int cols, int[] values) {
        this.id = ID_COUNT;
        ID_COUNT++;
        this.name = name;
        this.description = description;
        this.size = List.of(rows, cols);
        this.value = values;
    }

    public Item(String name, String description, int rows, int cols, int value) {
        this.id = ID_COUNT;
        ID_COUNT++;
        this.name = name;
        this.description = description;
        this.size = List.of(rows, cols);
        this.value = new int[]{0,0,0,value};
    }

    public Item(List<Integer> dimensions, int[] values) {
        this.id = ID_COUNT;
        ID_COUNT++;
        this.size = List.of(dimensions.get(0), dimensions.get(1));
        this.value = values;
    }

    public Item rotate() {
        return new Item(this.size.reversed(), this.value);
    }

    public Item convertToFewestCoins() {
        return new Item(this.size, this.value)
                .exchangeOneCurrency(3, 100)
                .exchangeOneCurrency(2, 100)
                .exchangeOneCurrency(1, 10);
    }
    private Item exchangeOneCurrency(int index, int exchangeRate) {
        int[] copyOfValues = Arrays.copyOf(this.value, this.value.length);
        int stored = Math.floorDiv(copyOfValues[index], exchangeRate);
        copyOfValues[index-1] = copyOfValues[index-1] + stored;
        copyOfValues[index] = copyOfValues[index] - stored * exchangeRate;
        return new Item(this.size, copyOfValues);
    }

    @Override
    public String toString() {
        return name;
    }
}
