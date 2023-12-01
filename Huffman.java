// Huffman Coding in Java

import ee.ut.dendroloj.Dendrologist;

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

        Tipp juur = null;
        while (kuhi.size() > 1) {
            Tipp vasak = kuhi.peek();
            kuhi.poll();
            Tipp parem = kuhi.peek();
            kuhi.poll();
            Tipp f = new Tipp(null);
            f.x = vasak.x + parem.x;
            f.v = vasak;
            f.p = parem;
            juur = f;
            kuhi.add(f);
        }
        Dendrologist.drawBinaryTree(juur, t -> ("[" + t.info + "], k=" + t.x), t -> t.v, t -> t.p);
        System.out.println(" Char | Huffman code ");
        System.out.println("--------------------");
        printCode(juur, "");
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