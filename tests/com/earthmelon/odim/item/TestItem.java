package com.earthmelon.odim.item;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestItem {
    @org.junit.Test
    @Deprecated
    // Make a better test than this.
    public void testConstructor() {
        Random randomInts = new Random();
        for(int i=0; i<100;i++) {
            Item tested = new Item("Test Item", "Test description.",
                    randomInts.nextInt(10),
                    randomInts.nextInt(10),
                    new int[]{randomInts.nextInt(100),
                            randomInts.nextInt(100),
                            randomInts.nextInt(100),
                            randomInts.nextInt(100)});
            assertNotNull(tested.size);
            assertEquals(tested.getId(), i);
        }
    }

    @org.junit.Test
    public void testConvertToFewestCoins() {
        Item tested = new Item("Test Item", "Test description", 0,0,1000);
        int[] correct = new int[]{0,0,10,0};
        assertArrayEquals(correct, tested.convertToFewestCoins().getValue());
    }
}
