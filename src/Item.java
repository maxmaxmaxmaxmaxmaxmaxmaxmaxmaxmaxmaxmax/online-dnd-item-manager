import java.util.Arrays;
import java.util.List;

public class Item {
    // First entry is row count, second is column count.
    List<Integer> size;

    // Measured as an array as such: [platinum, gold, silver, bronze]
    int[] value;

    public Item(int rows, int cols, int[] values) {
        this.size = List.of(rows, cols);
        this.value = values;
    }

    public Item(int rows, int cols, int value) {
        this.size = List.of(rows, cols);
        this.value = new int[]{0,0,0,value};
    }

    public Item(List<Integer> dimensions, int[] values) {
        this.size = List.of(dimensions.get(0), dimensions.get(1));
        this.value = values;
    }

    public Item rotate() {
        return new Item(this.size, this.value);
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
}
