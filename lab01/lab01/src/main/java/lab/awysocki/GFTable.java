package lab.awysocki;

import java.math.BigInteger;
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

        var a = Arrays.stream(multiplicativeOrderTable).flatMapToInt(Arrays::stream).boxed().toList();

        return Arrays.stream(multiplicativeOrderTable).flatMapToInt(Arrays::stream).distinct()
                .filter(
                i -> i == (p - 1)
                ).boxed().toList();
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
                            if(BigInteger.valueOf(this.table[row][col])
                                    .gcd(BigInteger.valueOf(p))
                                    .equals(BigInteger.ONE)) {
                                int i = 1;
                                while (true) {
                                    if (isMultiplicativeOrder(this.table[row][col], i, p)) {
                                        tempTable[row][col] = i;
                                        break;
                                    }
                                    i++;
                                }
                            }
                        }
                )
        );
        return tempTable;
    }

    public static boolean isMultiplicativeOrder(int a, int e, int p) {
        return Math.pow(a,e) % p == 1 % p;
    }

    public static int findMultiplicativeOrder(int a, int p) {
        int i = 1;
        while (i != p) {
            if (isMultiplicativeOrder(a, i, p)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public int findMaxPeriod(int m) {
        return (int) (Math.pow(p, m) - 1);
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
        Arrays.stream(multiplicativeOrderTable).forEach(
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
