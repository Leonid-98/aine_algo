import ee.ut.dendroloj.Dendrologist;

import java.io.IOException;
import java.util.List;

public class DemoJuhuslik {

    public static void main(String[] args) throws IOException {
        List<Tipp> tipud = Abi.juhuslikGraaf(10, 0.15);
        Dendrologist.setUIScale(2);
        Abi.kuvaGraaf(tipud);
    }
}
