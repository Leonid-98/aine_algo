import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        int n = 3;
        test(n);
        System.out.println("=".repeat(n));
        testRek(n);
    }

    public static void test(int n) {
        Deque<Integer> loeMag = new ArrayDeque<>();
        Deque<String> teeMag = new ArrayDeque<>();

        loeMag.push(n);
        teeMag.push("");

        while (!loeMag.isEmpty()) {
            int loe = loeMag.pop();
            String tee = teeMag.pop();

            if (loe == 0){
                System.out.println(tee);
            }
            else {
                // 2
                loeMag.push(loe - 1);
                teeMag.push(tee + "2");

                // 1
                loeMag.push(loe - 1);
                teeMag.push(tee + "1");

                // 0
                loeMag.push(loe - 1);
                teeMag.push(tee + "0");
            }
        }

    }

    public static void testRek(int n) {
        testRek(n, "");
    }

    public static void testRek(int n, String tee) {
        if (n == 0) {
            System.out.println(tee);
            return;
        }

        testRek(n - 1, tee + "0");
        testRek(n - 1, tee + "1");
        testRek(n - 1, tee + "2");
    }


}