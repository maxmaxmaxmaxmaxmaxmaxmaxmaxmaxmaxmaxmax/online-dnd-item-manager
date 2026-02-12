import java.util.List;

public class Item {
    // First entry is row count, second is column count.
    List<Integer> size;

    // Measured in copper pieces.
    int[] value;

    public Item(int rows, int cols, int[] value) {
        this.size = List.of(rows, cols);
        this.value = value;
    }

    public Item(int rows, int cols, int value) {
        this.size = List.of(rows, cols);
        this.value = new int[]{0,0,0,value};
    }

    public Item rotate() {
        return new Item(this.size.getLast(), this.size.getFirst(), this.value);
    }

    public Item convertToFewestCoins() {
        int stored = Math.floorDiv(this.value[3], 100);
        this.value[2] = this.value[2] + stored;
        this.value[3] = this.value[3] - stored * 100;
        stored = Math.floorDiv(this.value[2], 100);
        this.value[1] = this.value[1] + stored;
        this.value[2] = this.value[2] - stored * 100;
        stored = Math.floorDiv(this.value[1], 10);
        this.value[0] = this.value[0] + stored;
        this.value[1] = this.value[1] - stored * 10;
        return this;
    }
}
