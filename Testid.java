import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.Grow;

import java.util.Arrays;

public class Testid {
    public static void main(String[] args) {
        Dendrologist.wakeUp();
        bitivektorid(4, 2);

    }

    @Grow
    static void bitivektorid(int n, int k, String tee, int yhesidOnVaja) {
        if (tee.length() == n) {
            if (yhesidOnVaja == 0)
                System.out.println(tee);
            return;
        }

        int jaiRuumi = n - tee.length();
        if (jaiRuumi < yhesidOnVaja)
            return;

        if (yhesidOnVaja > 0)
            bitivektorid(n, k, tee + "1", yhesidOnVaja - 1);
        bitivektorid(n, k, tee + "0", yhesidOnVaja);
    }


    static void bitivektorid(int n, int k) {
        bitivektorid(n, k, "", k);
    }

}
