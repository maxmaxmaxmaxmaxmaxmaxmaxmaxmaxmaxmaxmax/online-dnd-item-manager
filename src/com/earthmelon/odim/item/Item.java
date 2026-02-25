package com.earthmelon.odim.item;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Item implements Serializable {
    public static int ID_COUNT = 0;

    // First entry is row count, second is column count.
    // TODO: Make getter
    List<Integer> size;

    // Measured as an array as such: [platinum, gold, silver, bronze]
    private final int[] value;

    private final String name;

    private final String description;

    private Image image;

    private final int id;

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

    public Item(String name, String description, List<Integer> dimensions, int[] values) {
        this.name = name;
        this.description = description;
        this.id = ID_COUNT;
        ID_COUNT++;
        this.size = List.of(dimensions.get(0), dimensions.get(1));
        this.value = values;
    }

    // To be used only to alter a currently existing Item, not to create a new one.
    private Item(String name, String description, List<Integer> dimensions, int[] values, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.size = List.of(dimensions.get(0), dimensions.get(1));
        this.value = values;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public int[] getValue() {
        return value;
    }

    public List<Integer> getSize() {
        return size;
    }

    public Item rotate() {
        return new Item(this.name, this.description, this.size.reversed(), this.value, this.id);
    }

    public Item convertToFewestCoins() {
        return new Item(this.name, this.description, this.size, this.value, this.id)
                .exchangeOneCurrency(3, 100)
                .exchangeOneCurrency(2, 100)
                .exchangeOneCurrency(1, 10);
    }
    private Item exchangeOneCurrency(int index, int exchangeRate) {
        int[] copyOfValues = Arrays.copyOf(this.value, this.value.length);
        int stored = Math.floorDiv(copyOfValues[index], exchangeRate);
        copyOfValues[index-1] = copyOfValues[index-1] + stored;
        copyOfValues[index] = copyOfValues[index] - stored * exchangeRate;
        return new Item(this.name, this.description, this.size, copyOfValues);
    }

    @Override
    public String toString() {
        return id + " " + name;
    }

    @Override
    public boolean equals(Object anObject) {
        return anObject instanceof Item item && this.id == item.getId();
    }
}
