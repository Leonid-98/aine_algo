import java.util.Arrays;
import java.util.Stack;

public class Kodu2B {
    public static void main(String[] args) {
        long[] a = {10, 11};
        int p = 15;
        tükeldused(a, p);
    }

    public static void tükeldused(long[] massiiv, long pikkus) {
        Arrays.sort(massiiv);
        long minimum = massiiv[massiiv.length - 1];
        tükeldused(massiiv, pikkus, minimum, 0, new Stack<>(), 0);
    }

    public static void tükeldused(long[] massiiv, long pikkus, long minimum, int start, Stack<Long> magasin, long summa) {
        if (summa > pikkus) {
            System.out.println(magasin);
            return;
        }


        for (int i = start; i < massiiv.length; i++) {
                magasin.push(massiiv[i]);
                tükeldused(massiiv, pikkus, minimum, i, magasin, summa + massiiv[i]);
                magasin.pop();
        }
    }


}