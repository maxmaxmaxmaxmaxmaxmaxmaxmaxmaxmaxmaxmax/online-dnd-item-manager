package com.earthmelon.odim.item;

import static org.junit.jupiter.api.Assertions.*;

public class TestItem {
    @org.junit.Test
    @Deprecated
    // Make a better test than this.
    public void testConstructor() {
        for(int i=0; i<100;i++) {
            Item tested = new Item("Test Item", "Test description.", (int) Math.floor(Math.random()*10), (int) Math.floor(Math.random()*10),
                    new int[]{(int) Math.floor(Math.random()*100),(int) Math.floor(Math.random()*100),(int) Math.floor(Math.random()*100),(int) Math.floor(Math.random()*100)});
            assertNotNull(tested.size);
            assertNotEquals(-1, tested.value);
        }
    }

    @org.junit.Test
    public void testConvertToFewestCoins() {
        Item tested = new Item("Test Item", "Test description", 0,0,1000);
        int[] correct = new int[]{0,0,10,0};
        assertArrayEquals(correct, tested.convertToFewestCoins().value);
    }
}
