package Arvutipraks_5;

import ee.ut.dendroloj.Dendrologist;

public class Y2 {

    public static void main(String[] args) {
        Tipp t = Abi.juhuslikAvaldis(2);
        Dendrologist.setUIScale(2);
        Abi.joonista(t);

        String avaldisSõnena = avaldisSõneks(t);
        System.out.println(avaldisSõnena);

        int tulemus = väärtusta(t);
        System.out.println("Tulemus on " + tulemus);
    }

    private static int väärtusta(Tipp t) {
        if (t.info.equals("+")) return väärtusta(t.v) + väärtusta(t.p);
        if (t.info.equals("-")) return väärtusta(t.v) - väärtusta(t.p);
        if (t.info.equals("*")) return väärtusta(t.v) * väärtusta(t.p);
        return Integer.parseInt(t.info);
    }

    private static String avaldisSõneks(Tipp t) {
        if (t.v == null && t.p == null) return t.info;
        String vasemAvaldis = avaldisSõneks(t.v);
        String paremAvaldis = avaldisSõneks(t.p);
        return "(" + vasemAvaldis + t.info + paremAvaldis + ")";
    }
}
