package com.example.labadmin.ayiti_touris.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by labadmin on 8/21/17.
 */

public class Monuments {
    public static final String[] MonumentsHistoric = {
            "maron inconnu", "trois mains", "citadelle", "jean-jacques dessalines","le train à parc historique de la canne-à-sucre "
    };


    /**
     Liste aleatoire monument
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


