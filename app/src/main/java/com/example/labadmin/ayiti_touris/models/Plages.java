package com.example.labadmin.ayiti_touris.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by labadmin on 8/21/17.
 */

public class Plages {
    public static final String[] LocalBeach = {
            "Moulin Sur Mer", "Le Xaragua", "Musee Ogier-Fombrun", "Royal Decameron Indigo Beach Resort & Spa","Wahoo Bay Beach Hotel",
            "Kaliko Beach Club All-Inclusive Resort","Ouanga Bay","Jeffel Hotel",
    };


    /**
     List beach
     */
    public static List<Plage> randomList(int count) {
        Random random = new Random();
        List<Plage> items = new ArrayList<>();


        count = Math.min(count, LocalBeach.length);

        while (items.size() < count) {
            items.add(new Plage(LocalBeach[random.nextInt(LocalBeach.length)]));
        }

        return new ArrayList<>(items);
    }
}



