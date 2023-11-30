// Huffman Coding in Java

import java.util.PriorityQueue;
import java.util.Comparator;

// For comparing the nodes
class ImplementComparator implements Comparator<Tipp> {
    public int compare(Tipp x, Tipp y) {
        return x.x - y.x;
    }
}

// IMplementing the huffman algorithm
public class Huffman {
    public static void main(String[] args) {

        int n = 4;
        char[] charArray = {'A', 'B', 'C', 'D'};
        int[] charfreq = {5, 1, 6, 3};

        PriorityQueue<Tipp> kuhi = new PriorityQueue<Tipp>(n, new ImplementComparator());

        for (int i = 0; i < n; i++) {
            Tipp tipp = new Tipp("" + charArray[i]);
            tipp.x = charfreq[i];

            tipp.v = null;
            tipp.p = null;

            kuhi.add(tipp);
        }

        Tipp root = null;

        while (kuhi.size() > 1) {

            Tipp x = kuhi.peek();
            kuhi.poll();

            Tipp y = kuhi.peek();
            kuhi.poll();

            Tipp f = new Tipp("○");

            f.x = x.x + y.x;
            f.v = x;
            f.p = y;
            root = f;

            kuhi.add(f);
        }
        System.out.println(" Char | Huffman code ");
        System.out.println("--------------------");
        printCode(root, "");
    }

    public static void printCode(Tipp root, String s) {
        if (root.v == null && root.p == null && Character.isLetter(root.info.charAt(0))) {

            System.out.println(root.info + "   |  " + s);

            return;
        }
        printCode(root.v, s + "0");
        printCode(root.p, s + "1");
    }


}