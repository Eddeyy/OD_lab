package lab.awysocki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class GFTable {

    private final int[][] table;
    private final int[][] multiplicativeOrderTable;

    private final List<Integer> primeMultiplicativeOrderList;

    private final int p;
    private final GFOperation op;

    GFTable(int p, GFOperation op) {
        this.p = p;
        this.op = op;
        this.table = this.generateTable();
        this.multiplicativeOrderTable = this.generateMultiplicativeOrderTable();
        this.primeMultiplicativeOrderList = this.findPrimeMultiplicativeOrderValues();
    }

    private List<Integer> findPrimeMultiplicativeOrderValues() {
        return Arrays.stream(multiplicativeOrderTable).flatMapToInt(Arrays::stream).filter(
                i -> i == (p - 1)
        ).distinct().boxed().toList();
    }

    public int[][] getTable() {
        return table;
    }

    public int[][] getMultiplicativeOrderTable() {
        return multiplicativeOrderTable;
    }

    public int getP() {
        return p;
    }

    public GFOperation getOp() {
        return op;
    }

    private int[][] generateTable() {
        int[][] tempTable = new int[p][p];


        IntStream.range(0, p).forEach(
                row -> IntStream.range(0, p).forEach(
                        col -> tempTable[row][col] = op.calculate(row,col) % p
                )
        );

        return tempTable;
    }

    private int[][] generateMultiplicativeOrderTable() {
        int[][] tempTable = new int[p][p];

        IntStream.range(0, p).forEach(
                row -> IntStream.range(0, p).forEach(
                        col -> {
                            int i = 0;
                            while(true) {
                                if(isMultiplicativeOrder(this.table[row][col], i, p)) {
                                    tempTable[row][col] = i;
                                    break;
                                }
                                i++;
                            }
                        }
                )
        );
        return tempTable;
    }

    private boolean isMultiplicativeOrder(int a, int e, int p) {
        return Math.pow(a,e) == 1 % p;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("GFTable for operation %s and p = %d",this.op,this.table.length));
        builder.append("\n");
        Arrays.stream(table).forEach(
                row -> {
                    Arrays.stream(row).forEach(
                            col -> builder.append(String.format("%d\t", col))
                    );
                    builder.append("\n");
                }
        );
        builder.append(String.format("\nMultiplicative orders the GFTable for operation %s and p = %d", this.op,this.table.length));
        builder.append("\n");
        Arrays.stream(table).forEach(
                row -> {
                    Arrays.stream(row).forEach(
                            col -> builder.append(String.format("%d\t", col))
                    );
                    builder.append("\n");
                }
        );
        builder.append("\n");
        builder.append(this.primeMultiplicativeOrderList);
        return builder.toString();
    }
}
