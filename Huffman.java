// Huffman Coding in Java

import java.util.PriorityQueue;
import java.util.Comparator;

class Tipp_ {
    int item;
    char c;
    Tipp_ left;
    Tipp_ right;
}

// For comparing the nodes
class ImplementComparator implements Comparator<Tipp_> {
    public int compare(Tipp_ x, Tipp_ y) {
        return x.item - y.item;
    }
}

// IMplementing the huffman algorithm
public class Huffman {
    public static void main(String[] args) {

        int n = 4;
        char[] charArray = {'A', 'B', 'C', 'D'};
        int[] charfreq = {5, 1, 6, 3};

        PriorityQueue<Tipp_> kuhi = new PriorityQueue<Tipp_>(n, new ImplementComparator());

        for (int i = 0; i < n; i++) {
            Tipp_ tipp = new Tipp_();

            tipp.c = charArray[i];
            tipp.item = charfreq[i];

            tipp.left = null;
            tipp.right = null;

            kuhi.add(tipp);
        }

        Tipp_ root = null;

        while (kuhi.size() > 1) {

            Tipp_ x = kuhi.peek();
            kuhi.poll();

            Tipp_ y = kuhi.peek();
            kuhi.poll();

            Tipp_ f = new Tipp_();

            f.item = x.item + y.item;
            f.c = '-';
            f.left = x;
            f.right = y;
            root = f;

            kuhi.add(f);
        }
        System.out.println(" Char | Huffman code ");
        System.out.println("--------------------");
        printCode(root, "");
    }

    public static void printCode(Tipp_ root, String s) {
        if (root.left == null && root.right == null && Character.isLetter(root.c)) {

            System.out.println(root.c + "   |  " + s);

            return;
        }
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }


}