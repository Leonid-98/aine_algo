import ee.ut.dendroloj.Dendrologist;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> arvud =Arrays.asList(7, 3, 5, 2);
        Kuhi kuhi = new Kuhi(arvud);
        Dendrologist.setUIScale(2);
        System.out.println(kuhi.vasemIndeks(1));
        System.out.println(kuhi.paremIndeks(1));
        System.out.println(kuhi.ülemIndeks(1));

        kuhi.kuva();
        kuhi.lisa(0);
        kuhi.lisa(6);
        kuhi.kuva();
    }
}
