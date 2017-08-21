package com.example.labadmin.ayiti_touris.models;

/**
 * Created by labadmin on 8/21/17.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Liste Hotels
 */
public class Evenements {

    public static final String[]EvenementsPlan = {

    };

    /**
     Liste aleatoire evenements
     */
    public static List<Hotel> randomList(int count) {
        Random random = new Random();
        List<Hotel> items = new ArrayList<>();


        count = Math.min(count, Hotels.EvenementsPlan.length);

        while (items.size() < count) {
            items.add(new Hotel(Hotels.EvenementsPlan[random.nextInt(Hotels.EvenementsPlan.length)]));
        }

        return new ArrayList<>(items);
    }
}