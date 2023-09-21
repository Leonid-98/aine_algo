package Random;

public class Main {
    public static void main(String[] args) {
//        System.out.println(summa(4));
//        System.out.println(fun(27));
        int[] a = {5, 2, 6, 0, 1};
        System.out.println(otsi(a, 0, a.length, 3));
    }

    public static void h(int n) {
        System.out.println("Call " + n);
        if (n >= 1) {
            h(n - 1);
            h(n - 1);
            System.out.println(n);
        }
    }

    public static int summa(int n) {
        if (n == 1) {
            return 5;
        } else {
            return 5 * n + summa(n - 1);
        }
    }

    public static String arvud(int n) {
        if (n == 0) {
            return "";
        } else {
            return arvud(n - 1) + n + arvud(n - 1);
        }
    }

    public static int fun(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        if (n % 3 != 0) {
            return 0;
        }
        return fun(n / 3);
    }

    public static boolean otsi(int[] a, int i, int j, int x) {
        if (i == j) {
            return false;
        }
        if (a[i] == x) {
            return true;
        }
        return otsi(a, i + 1, j, x);

    }
}
