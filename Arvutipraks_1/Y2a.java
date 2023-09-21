package Arvutipraks_1;

public class Y2a {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        System.out.println(bitt_gen(31, ""));
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        long stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");
    }

    private static int bitt_gen(int n, String vektor) {
        if (vektor.length() == n) return 1;
        return bitt_gen(n, vektor + "0") + bitt_gen(n, vektor + "1");
    }
}