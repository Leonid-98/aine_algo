package Arvutipraks_2;

import java.util.Arrays;

public class Y5 {

    public static void main(String[] args) {
        int[] kaalud = juhukaalud(10);
        System.out.println(Arrays.toString(kaalud));
        jaotus(kaalud);
    }

    private static void jaotus(int[] kaalud) {
        int[][] tulemus = jaotusRek(0, kaalud, 0, 0, new int[0], new int[0]);
        System.out.println("Osa1: " + Arrays.toString(tulemus[0]));
        System.out.println("Osa1 kaalude summa: " + tulemus[2][0]);
        System.out.println("Osa2: " + Arrays.toString(tulemus[1]));
        System.out.println("Osa2 kaalude summa: " + tulemus[2][1]);
    }

    /**
     * @param i      mitmenda kaalu kohta otsust teeme.
     * @param kaalud kõik kaalud
     * @param s1     kaalude summa 1.
     * @param s2     kaalude summa 2.
     * @param o1     kaalude osajaotis 1
     * @param o2     kaalude osajaotis 2
     * @return
     */
    private static int[][] jaotusRek(int i, int[] kaalud, int s1, int s2, int[] o1, int[] o2) {
        if (i == kaalud.length) {
            return new int[][]{o1, o2, {s1, s2, Math.abs(s1 - s2)}};
        }
        int kaal = kaalud[i];
        int[][] lisadesEsimesse = jaotusRek(i + 1, kaalud, s1 + kaal, s2, lisa(o1, kaal), o2);
        int[][] lisadesTeise = jaotusRek(i + 1, kaalud, s1, s2 + kaal, o1, lisa(o2, kaal));
        if (lisadesEsimesse[2][2] < lisadesTeise[2][2]) return lisadesEsimesse;
        else return lisadesTeise;
    }

    public static int[] lisa(int[] a, int x) {
        int[] uus = new int[a.length + 1];
        int i = 0;
        for (; i < a.length; i++)
            uus[i] = a[i];
        uus[i] = x;
        return uus;
    }

    private static int[] juhukaalud(int n) {
        int[] tulemus = new int[n];
        for (int i = 0; i < n; i++) {
            tulemus[i] = 50 + (int) (Math.random() * 50);
        }
        return tulemus;
    }
}
