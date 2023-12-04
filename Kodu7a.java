import java.util.*;

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

public class Kodu7a {
    public static void main(String[] args) {
//        Tipp a = new Tipp("A");
//        Tipp b = new Tipp("B");
//        Tipp c = new Tipp("C");
//        Kaar ab = new Kaar(a, b, 5);
//        Kaar ac = new Kaar(a, c, 3);
//        a.kaared.add(ab);
//        a.kaared.add(ac);
//        List<Tipp> graaf =Arrays.asList(a, b, c);
//        Abi.kuvaGraaf(graaf);

        List<Tipp> graaf = Abi.läbimänguslaidiGraaf1();
        Abi.kuvaGraafKaaludeta(graaf);

        Set<String> töödeldud = new HashSet<>();
        Queue<String> järgmised = new ArrayDeque<>();
        for (Tipp tipp: graaf) {
            for (Kaar kaar: tipp.kaared) {
                System.out.println(kaar.alg + "->" + kaar.lõpp);
            }
            System.out.println();
        }

    }

    public static String[] jõuame(String lähtelinn, int x, int k, String[] linnad, int[][] M) {
        return null;
    }

}