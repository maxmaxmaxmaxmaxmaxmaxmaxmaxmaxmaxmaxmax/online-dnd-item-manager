import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestItem {
    @org.junit.Test
    public void testConstructor() {
        for(int i=0; i<100;i++) {
            Item tested = new Item((int) Math.floor(Math.random()*10), (int) Math.floor(Math.random()*10),
                    new int[]{(int) Math.floor(Math.random()*100),(int) Math.floor(Math.random()*100),(int) Math.floor(Math.random()*100),(int) Math.floor(Math.random()*100)});
            assertNotNull(tested.size);
            assertNotEquals(-1, tested.value);
        }
    }

    @org.junit.Test
    public void testConvertToFewestCoins() {
        Item tested = new Item(0,0,1000);
        int[] correct = new int[]{0,0,10,0};
        assertTrue(Arrays.equals(correct, tested.convertToFewestCoins().value));
    }
}
