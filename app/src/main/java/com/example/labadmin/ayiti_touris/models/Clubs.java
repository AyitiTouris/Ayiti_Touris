package com.example.labadmin.ayiti_touris.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by labadmin on 8/21/17.
 */

public class Clubs {

    public static final String[] NightClubs = {

    };

    /**
     Liste aleatoire evenements
     */
    public static List<Club> randomList(int count) {
        Random random = new Random();
        List<Club> items = new ArrayList<>();


        count = Math.min(count, NightClubs.length);

        while (items.size() < count) {
            items.add(new Club(NightClubs[random.nextInt(NightClubs.length)]));
        }

        return new ArrayList<>(items);
    }
}
