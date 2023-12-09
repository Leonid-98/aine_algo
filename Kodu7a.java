/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 7
 * Teema: Kaugusalgoritmid
 *
 * Autor: Leonid Tšigrinski
 *
 *****************************************************************************/

import java.util.*;


public class Kodu7a {
    public static void main(String[] args) {

        List<Tipp> graaf = Abi.testGraaf1(); //Abi.dijkstraGraaf1();
        Abi.kuvaGraaf(graaf);

        Set<Tipp> töödeldud = new HashSet<>();
        Queue<Tipp> järgmised = new ArrayDeque<>();

        Tipp algtipp = Abi.leiaTipp("B", graaf);

        järgmised.add(algtipp);
        töödeldud.add(algtipp);

        while (!järgmised.isEmpty()) {
            Tipp vaadeldav = järgmised.poll();
            System.out.print(vaadeldav.info + " ");

            for (Kaar kaar : vaadeldav.kaared) {
                Tipp naaber = kaar.lõpp;
                if (!töödeldud.contains(naaber)) {
                    järgmised.add(naaber);
                    töödeldud.add(naaber);
                }
            }
        }
    }


    public static String[] jõuame(String lähtelinn, int x, int k, String[] linnad, int[][] M) {
        return null;
    }

}