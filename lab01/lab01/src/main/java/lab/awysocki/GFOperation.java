package lab.awysocki;

import java.util.function.BiFunction;

public enum GFOperation {
    MULTIPLICATION((a,b) -> a*b),
    ADDITION(Integer::sum);

    GFOperation(BiFunction<Integer, Integer, Integer> f) {
        this.operation = f;
    }
    private final BiFunction<Integer, Integer, Integer> operation;

    public int calculate(int row, int col) {
        return operation.apply(row, col);
    }
}
