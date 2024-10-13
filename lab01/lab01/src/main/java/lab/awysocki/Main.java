package lab.awysocki;

public class Main {
    public static void main(String[] args) {
        GFTable a = new GFTable(8, GFOperation.MULTIPLICATION);
        GFTable b = new GFTable(8, GFOperation.ADDITION);

        System.out.println(a);
        System.out.println(b);
    }
}