import java.util.Arrays;

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
class Kodu3b {
    public static void main(String[] args) {

        System.out.println(rahakott(100));
    }

    public static long rahakott(int[] mündid, int[] müntideArv, int i, int summa, String tee) {
        System.out.println(tee + " " + Arrays.toString(müntideArv));
        if (summa == 0) {
            return 1;
        }
        if (summa < 0 || i == mündid.length)
            return 0;


        long tulemus = 0;

        müntideArv[i]--;
        if (müntideArv[i] > 0)
            tulemus += rahakott(mündid, müntideArv, i, summa - mündid[i], tee + " " +mündid[i]);
        müntideArv[i]++;

        tulemus += rahakott(mündid, müntideArv, i + 1, summa, tee);

        return tulemus;
    }

    public static long rahakott(int n) {
        int[] mündid = {10, 20, 50, 100, 200};
        int[] arv = {15, 15, 15, 10, 10};
        return rahakott(mündid, arv, 0, n, "");
    }


}

