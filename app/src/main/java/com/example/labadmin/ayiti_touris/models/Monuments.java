package com.example.labadmin.ayiti_touris.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by labadmin on 8/21/17.
 */

public class Monuments {
    public static final String[] MonumentsHistoric = {
            "neg maron", "trois mains", "citadelle", "jean-jacques dessalines", "bon zenzen Hotel",
            "Ste Philomene", "Alez Hotel", "Hotel Best", "Barbara Hotel ", "Amanda Hotel", "Anna Hotel",
            "Hotel Catherine", "Melissa Hotel", "Sandra Hotel", "Hotel Julia", "Hotel Paula", "Hotel Kimberly",
            "Diane Hotel", "Betty Hotel", "Sharon Hotel", "Ruby Hotel", "Barbara", "Ann Hotel", "Phyllis Hotel",
            "Linda Hotel", "Marie the best", "Deborah Hotel", "Sara Hotel", "Gloria Hotel", "Karen Hotel", "Patricia Hotel",

    };


    /**
     Liste aleatoire hotel
     */
    public static List<Monument> randomList(int count) {
        Random random = new Random();
        List<Monument> items = new ArrayList<>();


        count = Math.min(count, MonumentsHistoric.length);

        while (items.size() < count) {
            items.add(new Monument(MonumentsHistoric[random.nextInt(MonumentsHistoric.length)]));
        }

        return new ArrayList<>(items);
    }
}


