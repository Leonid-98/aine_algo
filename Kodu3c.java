/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 3
 * Teema: Magasin ja järjekord
 *
 * Autor: Leonid Tšigrinski
 *****************************************************************************/

import java.util.*;

/*
 Esmalt seletan, kuidas asendada rekursiooni magasinidega:

 public static void rek(arg1, arg2) {
      if (baasjuht 1)
          ...
      if (baasjuht 2)
          ...
      rek(arg1 - N, arg2 - M);
      rek(arg1 - N, arg2 - M);
 }

 Nüüd sama meetod kasutades magasini:

 public static void rek(arg1, arg2) {
      Deque<Integer> magasinArg1 = new ArrayDeque<>(); // Iga muudetava argumendi jaoks loon magasiini
      Deque<Integer> magasinArg2 = new ArrayDeque<>();
      magasinArg1.push(arg1);                          // N.ö. esimene väljakutse
      magasinArg2.push(arg2);

      while (!magasinArg1.isEmpty()) {
          int a1 = magasinArg1.pop();                  // N.ö. meetodi keha, pärast väljakutsumist
          int a2 = magasinArg2.pop();
          if (baasjuht 1)                              // Baasjuhud
              ...
          else if (baasjuht 2)
              ...
          else {
              magasinArg1.push(a1 - N);                // N.ö. "rekursiooni" sammud
              magasinArg2.push(a2 - M);
          }
      }
 }
*/
public class Kodu3c {
    public static void main(String[] args) {
//        System.out.println(ratsuTeekonnad(3) + " " + 4);
//        System.out.println(ratsuTeekonnad(4) + " " + 12);
        System.out.println(ratsuTeekonnad(5) + " " + 22);

//        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
//        Queue<Integer> läbitudRead = new ArrayDeque<>();
//        for (int j : a)
//            läbitudRead.add(j);
//
//        System.out.println(lisaJärjekorra(läbitudRead, 7));
//        System.out.println(läbitudRead);
//        System.out.println("delete 10");
//        System.out.println(eemaldaJärjekorrast(läbitudRead, 10));
//        System.out.println(läbitudRead);
//        Queue<Integer> järjekordRead = new ArrayDeque<>();
//        lisaJärjekorra(järjekordRead, 1);
//        lisaJärjekorra(järjekordRead, 2);
//        lisaJärjekorra(järjekordRead, 3);
//        lisaJärjekorra(järjekordRead, 1);
//        System.out.println(järjekordRead);

    }

    /**
     * Antud malelaua küljepikkuse n korral leida mitu erinevat sellist ratsu teekonda leidub.
     * Samale reale ei tohi mitu korda astuda. Ratsu peab iga sammuga liikuma paremale
     * <p>
     * Printsiib on on väga sarnane sellele, kuidas rekursiooniga seda lahendada:
     * Samm:
     * Ratsul on neli võimalust, kuhu liigutada: "Г", "L", "╔══", "╚══", ehk (+2,+1), (+2,-1), (+1,+2), (+1,-2)
     * Kokku tekkib 4 harut igas sammus: hargneme nejlale.
     * Baas 1:
     * Ratsu jõudnud paremale, ning jäi malelaua sees. Soobiv tee, seega loenduri suurendame ühe võrra
     * Baas 2:
     * Ratsu astus malelaua väljast. Tee enam ei soobi, seega ei hargne.
     * <p>
     * Erijuht saab kontrollida kasutades järjekord, kuhu salvestan read, kuhu ratsu astus.
     *
     * @param n Malelaua suurus
     * @return Võimalikude teekonnade arv
     */
    public static long ratsuTeekonnad(int n) {
        long tulemus = 0;

        // Kuidas ratsu saab liigutada
        int[] dx = {2, 2, 1, 1}; // Alati paremale
        int[] dy = {1, -1, 2, -2};

        // Ratsul on N võimalust (ridade arv), kuist alustada
        for (int startY = 0; startY < n - 1; startY++) {
            // Rekursiooni asnedan magasiniga, iga parametri jaoks teen oma magasini
            Deque<Integer> magasinX = new ArrayDeque<>();
            Deque<Integer> magasinY = new ArrayDeque<>();
            Deque<String> magasinTee = new ArrayDeque<>();

            magasinX.push(0);
            magasinY.push(startY);
            magasinTee.push(startY + " ");

            List<Integer> list = new ArrayList<>();
            list.add(startY);

            while (!magasinX.isEmpty()) {
                int x = magasinX.pop();
                int y = magasinY.pop();
                String tee = magasinTee.pop();

                // Ratsul on 4 võimalusi, kuidas paremale liigutada
                for (int i = 0; i < 4; i++) {
                    int järgmineX = x + dx[i];
                    int järgmineY = y + dy[i];

                    if (kasKordub(tee, järgmineY)) {

                    } else if (järgmineX == n - 1 && järgmineY >= 0 && järgmineY <= n - 1) {
                        /* N.ö. "Baasjuht" 1. Ratsu jõudnud paremale */
                        tulemus++;
                    } else if (järgmineX > n - 1 || järgmineY < 0 || järgmineY >= n) {
                        /* N.ö. "Baasjuht" 2. Ratsu jõudnud väljaspoole */
                    } else {
                        /* N.ö. "Samm". Ratsu jõudnud väljaspoole */
                        magasinX.push(järgmineX);
                        magasinY.push(järgmineY);

                        magasinTee.push(tee + järgmineY + " ");
                    }
                }
            }
        }
        return tulemus;
    }

    public static boolean kasKordub(String tee, int y) {
        String[] aju = tee.split(" ");
        for (String s : aju) {
            int x = Integer.parseInt(s);
            if (x == y)
                return true;
        }
        return false;
    }
}
