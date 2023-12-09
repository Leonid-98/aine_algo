/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 7
 * Teema: Kaugusalgoritmid
 *
 * Autor: Leonid Tšigrinski, Jaan Janno (arvutipraktikumis)
 *
 *****************************************************************************/

import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.GraphCanvas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Abi {

    public static Tipp leiaTipp(String sisu, List<Tipp> tipud) {
        for (Tipp tipp : tipud)
            if (tipp.info.equals(sisu))
                return tipp;
        return null;
    }

    // Abimeetodid andmete lugemiseks

    public static List<Tipp> loeKülgnevusstruktuur(File fail) throws IOException {
        List<String> read = Files.readAllLines(fail.toPath());
        Map<String, Tipp> tipud = new HashMap<>();

        for (String rida : read) {
            String[] väärtused = rida.split("\t");
            Tipp tipp = new Tipp(väärtused[0]);
            tipud.put(tipp.info, tipp);
        }

        for (String rida : read) {
            String[] väärtused = rida.split("\t");
            Tipp tipp = tipud.get(väärtused[0]);
            for (int i = 2; i < väärtused.length; i++)
                tipp.kaared.add(new Kaar(tipp, tipud.get(väärtused[i]), 1));
        }

        return new ArrayList<>(tipud.values());
    }

    static NimedegaNaabrusmaatriks loeNaabrusmaatriks(File fail) throws IOException {
        List<String> read = Files.readAllLines(fail.toPath());

        // Loeme esimeselt realt nimed
        String[] nimed = read.get(0).split("\t");

        // Loeme naabrusmaatriksi
        int n = read.size() - 1;
        int[][] M = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] väärtused = read.get(i + 1).split("\t");

            for (int j = 0; j < väärtused.length; j++)
                M[i][j] = Integer.parseInt(väärtused[j]);
        }
        return new NimedegaNaabrusmaatriks(M, nimed);
    }

    // Abimeetodid graafide kuvamiseks dendroloj abil

    public static void kuvaGraafKaaludeta(List<Tipp> tipud) {
        GraphCanvas<Tipp> lõuend = new GraphCanvas<>();

        for (Tipp tipp : tipud) {
            lõuend.drawVertex(tipp, tipp.info);

            for (Kaar kaar : tipp.kaared)
                lõuend.drawDirectedEdge(kaar.alg, kaar.lõpp);
        }

        Dendrologist.drawGraph(lõuend);
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

    public static List<Tipp> läbimänguslaidiGraaf1() {
        List<Tipp> graaf = new ArrayList<>();
        for (char tähis : "ABCDEFGH".toCharArray())
            graaf.add(new Tipp("" + tähis));

        ühenda("A", "B", 1, graaf);
        ühenda("A", "C", 200, graaf);
        ühenda("A", "D", 3, graaf);
        ühenda("B", "C", 40, graaf);
        ühenda("B", "E", 1, graaf);
        ühenda("C", "E", 6, graaf);
        ühenda("D", "C", 22, graaf);
        ühenda("D", "F", 9, graaf);
        ühenda("D", "G", 11, graaf);
        ühenda("E", "H", 1, graaf);
        ühenda("F", "C", 1, graaf);
        ühenda("G", "H", 5, graaf);
        ühenda("H", "F", 1, graaf);

        return graaf;
    }

    public static List<Tipp> läbimänguslaidiGraaf2() {
        List<Tipp> graaf = new ArrayList<>();
        for (char tähis : "ABCDEFGH".toCharArray())
            graaf.add(new Tipp("" + tähis));

        ühenda("A", "G", graaf);
        ühenda("A", "H", graaf);

        ühenda("G", "A", graaf);
        ühenda("G", "F", graaf);
        ühenda("G", "H", graaf);

        ühenda("H", "A", graaf);
        ühenda("H", "F", graaf);
        ühenda("H", "G", graaf);

        ühenda("F", "E", graaf);
        ühenda("F", "G", graaf);
        ühenda("F", "H", graaf);

        ühenda("E", "B", graaf);
        ühenda("E", "C", graaf);
        ühenda("E", "D", graaf);
        ühenda("E", "F", graaf);

        ühenda("C", "B", graaf);
        ühenda("C", "E", graaf);

        ühenda("B", "C", graaf);
        ühenda("B", "D", graaf);
        ühenda("B", "E", graaf);

        ühenda("D", "B", graaf);
        ühenda("D", "E", graaf);

        return graaf;
    }

    public static List<Tipp> dijkstraGraaf1() {
        List<Tipp> graaf = new ArrayList<>();
        for (char tähis : "ABCDEFGHI".toCharArray())
            graaf.add(new Tipp("" + tähis));

        ühenda("A", "B", 3, graaf);
        ühenda("A", "C", 2, graaf);

        ühenda("B", "C", 7, graaf);
        ühenda("B", "I", 7, graaf);
        ühenda("B", "F", 2, graaf);

        ühenda("C", "D", 2, graaf);
        ühenda("C", "H", 8, graaf);
        ühenda("C", "G", 5, graaf);

        ühenda("D", "A", 7, graaf);
        ühenda("D", "E", 4, graaf);

        ühenda("E", "A", 5, graaf);
        ühenda("E", "B", 5, graaf);

        ühenda("F", "H", 6, graaf);

        ühenda("G", "I", 2, graaf);

        return graaf;
    }

    public static List<Tipp> testGraaf1() {
        List<Tipp> graaf = new ArrayList<>();
        for (char tähis : "ABCDE".toCharArray())
            graaf.add(new Tipp("" + tähis));

        ühenda("A", "E", 2, graaf);
        ühenda("A", "B", 1, graaf);
        ühenda("B", "C", 1, graaf);
        ühenda("C", "D", 1, graaf);
        ühenda("D", "E", 1, graaf);

        return graaf;
    }

    public static void ühenda(String a, String b, List<Tipp> graaf) {
        leiaTipp(a, graaf).kaared.add(new Kaar(leiaTipp(a, graaf), leiaTipp(b, graaf), 1));
    }

    public static void ühenda(String a, String b, int kaal, List<Tipp> graaf) {
        leiaTipp(a, graaf).kaared.add(new Kaar(leiaTipp(a, graaf), leiaTipp(b, graaf), kaal));
    }


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
