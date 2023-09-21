package Arvutipraks_1;

import java.math.BigInteger;

class Fibo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        System.out.println(fibo(42));
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        long stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");

        start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        int tulemus = fiboIteratiivne(42);
        System.out.println(tulemus);
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");

        start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        tulemus = fiboIteratiivne(1950000000);
        System.out.println(tulemus);
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");

        start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        BigInteger tulemusB = fiboIteratiivneSuurearvuline(300000);
        System.out.println(tulemusB);
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");
    }

    private static int fiboIteratiivne(int n) {
        if (n <= 1) return n;
        int üleeelmine = 0;
        int eelmine = 1;
        for (int i = 2; i < n; i++) {
            int praegune = üleeelmine + eelmine;
            üleeelmine = eelmine;
            eelmine = praegune;
        }
        return üleeelmine + eelmine;
    }

    private static BigInteger fiboIteratiivneSuurearvuline(long n) {
        if (n <= 1) return new BigInteger(Long.toString(n));
        BigInteger üleeelmine = new BigInteger("0");
        BigInteger eelmine = new BigInteger("1");
        for (long i = 2; i < n; i++) {
            BigInteger praegune = üleeelmine.add(eelmine);
            üleeelmine = eelmine;
            eelmine = praegune;
        }
        return üleeelmine.add(eelmine);
    }


    public static int fibo(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibo(n - 1) + fibo(n - 2);
    }
}