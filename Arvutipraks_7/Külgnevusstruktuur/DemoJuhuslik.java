package Arvutipraks_7.Külgnevusstruktuur;

import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.GraphCanvas;

import java.io.IOException;
import java.util.*;

public class DemoJuhuslik {

    public static void main(String[] args) throws IOException {
        List<Tipp> tipud = juhuslikGraaf(10, 0.15);
        Dendrologist.setUIScale(2);
        kuvaGraaf(tipud);
    }

    public static void kuvaGraaf(List<Tipp> tipud) {
        GraphCanvas<Tipp> lõuend = new GraphCanvas<>();

        for (Tipp tipp : tipud) {
            lõuend.drawVertex(tipp, tipp.info);

            for (Kaar kaar : tipp.kaared)
                lõuend.drawDirectedEdge(kaar.alg, kaar.lõpp, Integer.toString(kaar.kaal));
        }

        Dendrologist.drawGraph(lõuend);
    }

    // Juhusliku graafi genereerimine

    public static List<Tipp> juhuslikGraaf(int tippe, double ühendatuseTõenäosus) {
        return juhuslikGraaf(tippe, ühendatuseTõenäosus, 1, 1);
    }

    public static List<Tipp> juhuslikGraaf(int tippe, double ühendatuseTõenäosus, int min, int max) {
        List<Tipp> graaf = new ArrayList<>();
        Collections.shuffle(graaf);
        for (int i = 0; i < tippe; i++)
            graaf.add(new Tipp(Integer.toString(i + 1)));

        for (Tipp tipp1 : graaf)
            for (Tipp tipp2 : graaf)
                if (tipp1 != tipp2 && Math.random() < ühendatuseTõenäosus) {
                    Set<Tipp> läbitud = new HashSet<>();
                    Deque<Tipp> läbida = new ArrayDeque<>();
                    läbitud.add(tipp2);

                    for (Kaar kaar : tipp2.kaared)
                        läbida.push(kaar.lõpp);

                    boolean ring = false;

                    while (!läbida.isEmpty()) {
                        Tipp järgmine = läbida.remove();
                        if (järgmine == tipp1) ring = true;
                        läbitud.add(järgmine);
                        for (Kaar kaar : järgmine.kaared)
                            if (!läbitud.contains(kaar.lõpp))
                                läbida.push(kaar.lõpp);
                    }
                    if (!ring)
                        tipp1.kaared.add(new Kaar(tipp1, tipp2, min + (int) (Math.random() * (max - min + 1))));
                }

        return graaf;
    }
}
