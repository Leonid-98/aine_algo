package Arvutipraks_6;

import ee.ut.dendroloj.Dendrologist;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> arvud =Arrays.asList(0, 6, 7, 3, 5, 2);
        Kuhi kuhi = new Kuhi(arvud);
        Dendrologist.setUIScale(2);
        System.out.println(kuhi.vasemIndeks(1));
        System.out.println(kuhi.paremIndeks(1));
        System.out.println(kuhi.ülemIndeks(1));

        kuhi.kuva();
        kuhi.mullinaAlla(0);
        kuhi.kuva();
    }
}
