package Arvutipraks_5;


public class Y1 {
    public static void main(String[] args) {
        Tipp t1 = new Tipp("1");
        Tipp t2 = new Tipp("2");
        Tipp t3 = new Tipp("3");
        Tipp t4 = new Tipp("4");
        Tipp t5 = new Tipp("5");
        Tipp t6 = new Tipp("6");
        Tipp t7 = new Tipp("7");
        Tipp t8 = new Tipp("8");
        Tipp t9 = new Tipp("9");

        t1.v = t2;
        t2.v = t4;
        t4.p = t5;
        t2.p = t6;
        t1.p = t3;
        t3.p = t7;
        t7.v = t8;
        t7.p = t9;
//        Tipp.kuvaKahendpuu(t1);

//        System.out.println(tippudeArv(t1));
//        System.out.println(kõrgus(t1));
        keskjärjestus(t1);
//        System.out.println();
//        lehttipud(t1);
//        System.out.println();
    }

    public static int tippudeArv(Tipp juur) {
        if (juur == null)
            return 0;
        return 1 + tippudeArv(juur.v) + tippudeArv(juur.p);
    }

    public static int kõrgus(Tipp juur) {
        if (juur == null)
            return 0;
        return 1 + Math.max(kõrgus(juur.v), kõrgus(juur.p));
    }

    public static void keskjärjestus(Tipp juur) {
        if (juur == null)
            return;

        keskjärjestus(juur.v);

        keskjärjestus(juur.p);
        System.out.print(juur.info + " ");
    }

    public static void lehttipud(Tipp juur) {
        if (juur != null && juur.v == null && juur.p == null)
            System.out.print(juur.info + " ");

        else if (juur != null) {
            lehttipud(juur.v);
            lehttipud(juur.p);
        }
    }


}
