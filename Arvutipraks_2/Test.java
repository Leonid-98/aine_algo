package Arvutipraks_2;

import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.Grow;

public class Test {

    public static void main(String[] args) {
        Dendrologist.setUIScale(3);
        Dendrologist.wakeUp();
        Dendrologist.setShowMethodNames(true);
        f();
        g();
        h(4);
    }

    @Grow
    private static void h(int i) {
        if (i > 0) {
            h(i - 1);
            h(i - 1);
        }
    }

    @Grow
    private static void g() {
        f();
        f();
        f();
    }

    @Grow
    private static void f() {

    }
}
