package com.example.labadmin.ayiti_touris.models;

/**
 * Created by labadmin on 8/21/17.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Events List
 */
public class Evenements {

    public static final String[]EvenementsPlan = {

    };

    /**
     Liste aleatoire evenements
     */
    public static List<Evenement> randomList(int count) {
        Random random = new Random();
        List<Evenement> items = new ArrayList<>();


        count = Math.min(count, EvenementsPlan.length);

        while (items.size() < count) {
            items.add(new Evenement(EvenementsPlan[random.nextInt(EvenementsPlan.length)]));
        }

        return new ArrayList<>(items);
    }
}