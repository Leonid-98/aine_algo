import java.util.ArrayDeque;
import java.util.Deque;

public class Kodu3c {
    public static void main(String[] args) {
        System.out.println(ratsuTeekonnad(6));
//        System.out.println(ratsuTeekonnadRek(4));
    }

    public static long ratsuTeekonnad(int n) {
        long tulemus = 0;
        int startX = 0;
        for (int startY = 0; startY < n - 1; startY++) {
            Deque<Integer> magasinX = new ArrayDeque<>();
            Deque<Integer> magasinY = new ArrayDeque<>();
            Deque<String> magasinTee = new ArrayDeque<>();

            magasinX.push(startX); // TODO remove to startY, startX
            magasinY.push(startX);
            magasinTee.push("(" + (startX + 1) + ", " + (startY + 1) + ")");

            while (!magasinX.isEmpty()) {
                int x = magasinX.pop();
                int y = magasinY.pop();
                String tee = magasinTee.pop();


                if (x == n - 1 && y >= 0 && y <= n - 1) {
                    System.out.println(" : " + tee);
                    tulemus++;
                } else if (x > n - 1 || y < 0 || y >= n) {
                    System.out.println("x: " + tee);
                } else {
                    // Kuidas ratsu saab liigutada: "Г", "L", "╔══", "╚══"
                    // Liiguta (+1, +2)
                    magasinX.push(x + 1);
                    magasinY.push(y + 2);
                    magasinTee.push(tee + String.format("(%d, %d)", (x + 1 + 1), (y + 2 + 1)));

                    // Liiguta (+2, +1)
                    magasinX.push(x + 2);
                    magasinY.push(y + 1);
                    magasinTee.push(tee + String.format("(%d, %d)", (x + 2 + 1), (y + 1 + 1)));

                    // Liiguta (+2, -1)
                    magasinX.push(x + 2);
                    magasinY.push(y - 1);
                    magasinTee.push(tee + String.format("(%d, %d)", (x + 2 + 1), (y - 1 + 1)));

                    // Liiguta (+1, -2)
                    magasinX.push(x + 1);
                    magasinY.push(y - 2);
                    magasinTee.push(tee + String.format("(%d, %d)", (x + 1 + 1), (y - 2 + 1)));
                }
            }
        }
        return tulemus;
    }
}