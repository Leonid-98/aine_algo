package Arvutipraks_2;

import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.Grow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Y4 {

    public static void main(String[] args) {
        Dendrologist.setUIScale(3);
        Dendrologist.wakeUp();
        int[] hinnad = {10, 20, 30};
        int[] summad = leiaSummad(hinnad);
        for (int summa : summad) {
            System.out.println(summa);
        }
    }

    private static int[] leiaSummad(int[] hinnad) {
        List<Integer> summad = summadRek(0, hinnad, 0);
        int[] tulemus = new int[summad.size()];
        for (int i = 0; i < tulemus.length; i++) {
            tulemus[i] = summad.get(i);
        }
        return tulemus;
    }

    @Grow
    private static List<Integer> summadRek(int i, int[] hinnad, int summa) {
        if (i == hinnad.length)
            return Arrays.asList(summa);
        List<Integer> tulemus = new ArrayList<>();
        List<Integer> valides = summadRek(i + 1, hinnad, summa + hinnad[i]);
        List<Integer> valimata = summadRek(i + 1, hinnad, summa);
        tulemus.addAll(valides);
        tulemus.addAll(valimata);
        return tulemus;
    }
}
