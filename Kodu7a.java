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

        List<Tipp> graaf = Abi.läbimänguslaidiGraaf2();
        Abi.kuvaGraafKaaludeta(graaf);

//        Set<String> töödeldud = new HashSet<>();
//        Queue<String> järgmised = new ArrayDeque<>();
//        for (Tipp tipp: graaf) {
//            for (Kaar kaar: tipp.kaared) {
//                System.out.println(kaar.alg + "->" + kaar.lõpp);
//            }
//            System.out.println();
//        }

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