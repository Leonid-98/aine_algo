package Arvutipraks_2;

import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.Grow;

public class Test {

    public static void main(String[] args) {
        Dendrologist.setUIScale(3);
        Dendrologist.wakeUp();
        Dendrologist.setShowMethodNames(true);
        int[] a = {1, 2, 3, 4};
        kiirmeetod(a);
        int[] b = {1, 1, 1, 1};
        kiirmeetod(b);
    }

    @Grow
    public static int[] kiirmeetod(int[] massiiv) {
        // Rekursiooni baas. Massiiv on sorteeritud, kui tal on 1 element
        if (massiiv.length <= 1)
            return massiiv;

        // Allokeerin mälu. See on väga kallis osa, seega sisendi suurus on piiratud. Vastasel juhul toimub StackOvflw
        int x = massiiv[0];
        int[] vasak = new int[massiiv.length];
        int[] kesk = new int[massiiv.length];
        int[] parem = new int[massiiv.length];
        int vasakSuurus = 0, keskSuurus = 0, paremSuurus = 0;

        // Jaotan kolmeks alam massiiviks
        for (int element : massiiv) {
            if (element < x)
                vasak[vasakSuurus++] = element;
            else if (element == x)
                kesk[keskSuurus++] = element;
            else
                parem[paremSuurus++] = element;
        }

        // Elemendid on jaotatud, tuleb massiivi suurus muuta (ehk tagastada uue vastava suurusega)
        vasak = kopeeriAlgus(vasak, vasakSuurus);
        kesk = kopeeriAlgus(kesk, keskSuurus);
        parem = kopeeriAlgus(parem, paremSuurus);

        // Rekursiooni samm. Sorteerin samamoodi osad
        vasak = kiirmeetod(vasak);
        parem = kiirmeetod(parem);

        // Sorteeeritud osade kokkupanek
        int[] uus = new int[massiiv.length];
        int i = 0;
        for (int k : vasak) uus[i++] = k;
        for (int k : kesk) uus[i++] = k;
        for (int k : parem) uus[i++] = k;

        return uus;
    }

    public static int[] kopeeriAlgus(int[] massiiv, int k) {
        int[] uus = new int[k];
        for (int i = 0; i < k; i++)
            uus[i] = massiiv[i];

        return uus;
    }
}
