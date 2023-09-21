package Arvutipraks_2;


import java.math.BigInteger;

public class Main {

    public static int fibo(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibo(n - 1) + fibo(n - 2);
    }

    public static BigInteger fiboIteratiivne(int n) {
        if (n == 0) return BigInteger.ZERO;
        BigInteger eelmine = BigInteger.ZERO;
        BigInteger praegune = BigInteger.ONE;
        for (int i = 1; i < n; i++) {
            BigInteger uusPraegune = eelmine.add(praegune);
            eelmine = praegune;
            praegune = uusPraegune;
        }
        return praegune;
    }

    static int bittGen(int n) {
        return bittGen(n, "");
    }

    static int bittGen(int n, String vektor) {
        if (vektor.length() == n) {
            //System.out.println(vektor);
            return 1;
        }
        return bittGen(n, vektor + "0")
                + bittGen(n, vektor + "1");
    }


    static void dnaMolGen(int n, String genoomiLõik) {
        char[] nukleotiidid = {'A', 'T', 'C', 'G'};
        if (genoomiLõik.length() == n) {
            System.out.println(genoomiLõik);
        } else {
            for (char c : nukleotiidid) {
                dnaMolGen(n, genoomiLõik + c);
            }
        }
    }

    public static void permutatsioon(String sone, String perm) {
        if (sone.length() == 0) {
            System.out.println(perm);
        } else {
            for (int i = 0; i < sone.length(); i++) {
                permutatsioon(
                        sone.substring(0, i) + sone.substring(i + 1),
                        perm + sone.charAt(i)
                );
            }
        }
    }

    public static int abi1(int[] a, int n) {
        if (n == 0)
            return 1;
        int summa = abi1(lisa(a, 0), n - 1);
        summa += abi1(lisa(a, 1), n - 1);
        summa += abi1(lisa(a, 2), n - 1);
        return summa;
    }

    public static int abi2(int[] a, int n) {
        if (n == 0)
            return 1;

        a[a.length - n] = 0;
        int summa = abi2(a, n - 1);
        a[a.length - n] = 1;
        summa += abi2(a, n - 1);
        a[a.length - n] = 2;
        summa += abi2(a, n - 1);

        return summa;
    }

    static void sea(int[] a, int i, int väärtus) {
        a[i] = väärtus;
    }

    public static int[] lisa(int[] a, int x) {
        int[] uus = new int[a.length + 1];
        for (int i = 0; i < a.length; i++)
            uus[i] = a[i];
        uus[uus.length - 1] = x;
        return uus;
    }


    public static void main(String[] args) {

        System.out.println(abi2(new int[5], 5));

        //Dendrologist.wakeUp(1.5, false);

        //abi1(new int[0], 3);

        //permutatsioon("abcx", "");

        // 10 4897

        /*for (int i = 9; i <= 9; i += 1) {
            long start = System.currentTimeMillis();
            permutatsioon("abcdfghijklmnopqrstuv".substring(0, i), "");
            long end = System.currentTimeMillis();

            System.err.println(i + " " + (end - start));
            if (end - start > 30000) {
                break;
            }
        }*/
    }

}
