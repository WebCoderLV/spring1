package org.arturs.firstSpring.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameUtility {

    public static List<Integer> generateRandomNumber() {
        Set<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() < 4) {
            randomNumbers.add((int) (Math.random() * 9) + 1);
        }
        List<Integer> result = new ArrayList<>(randomNumbers);
        Collections.shuffle(result);
        return result;
    }

}
