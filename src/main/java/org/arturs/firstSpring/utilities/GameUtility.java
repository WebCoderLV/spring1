package org.arturs.firstSpring.utilities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameUtility {

    public static List<String> generateRandomNumber() {
        String hashStr = "";
        Set<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() < 4) {
            randomNumbers.add((int) (Math.random() * 9) + 1);
        }
        List<Integer> result = new ArrayList<>(randomNumbers);
        Collections.shuffle(result);
        List<String> stringResult = new ArrayList<>();
        for (Integer num : result) {
            String numStr = String.valueOf(num);
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte hashBytes[] = messageDigest.digest(numStr.getBytes(StandardCharsets.UTF_8));
                BigInteger noHash = new BigInteger(1, hashBytes);
                hashStr = noHash.toString(16);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            stringResult.add(hashStr);
        }
        return stringResult;
    }

    public static String hashNumber(int number) {
        try {
            String numStr = String.valueOf(number);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte hashBytes[] = messageDigest.digest(numStr.getBytes(StandardCharsets.UTF_8));
            BigInteger noHash = new BigInteger(1, hashBytes);
            return noHash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
