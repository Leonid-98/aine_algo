import java.util.Arrays;

public class Testid {
    public static void main(String[] args) {
        int[] x = Kodu1.massiivJuhuslikult(10, 0, 10);
        System.out.println(Arrays.toString(x));
        System.out.println();
        System.out.println(Arrays.toString(Kodu1.mullimeetod(x)));
        System.out.println(Arrays.toString(Kodu1.kiirmeetod(x)));
        System.out.println(Arrays.toString(Kodu1.susteemimeetod(x)));



    }
}
