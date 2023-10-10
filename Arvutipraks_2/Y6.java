package Arvutipraks_2;

import java.util.ArrayList;

public class Y6 {

    public static void main(String[] args) {
        int[] järjend = {1, 2, 3, 4, 5};
        int k = 3;
        kombinatsioonid(järjend, k);
    }

    private static void kombinatsioonid(int[] järjend, int k) {
        kombinatsioonidRek(0, järjend, k, new ArrayList<>());
    }

    private static void kombinatsioonidRek(int i, int[] järjend, int k, ArrayList<Integer> kombinatsioon) {
        if (i == järjend.length) {
            System.out.println(kombinatsioon);
        } else {
            if (k > 0) {
                kombinatsioon.add(järjend[i]);
                kombinatsioonidRek(i + 1, järjend, k - 1, kombinatsioon);
                kombinatsioon.remove(kombinatsioon.size() - 1);
            }
            if ((järjend.length - i) - k > 0) {
                kombinatsioonidRek(i + 1, järjend, k, kombinatsioon);
            }
        }
    }
}
