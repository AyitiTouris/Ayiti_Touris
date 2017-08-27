package com.example.labadmin.ayiti_touris.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Liste Hotels
 */
public class Hotels {

    public static final String[] HotelsName = {
            "paradis Hotel", "la providence", "le palace Hotel", "Ocorner Hotel", "bon zenzen Hotel",
            "Ste Philomene", "Alez Hotel", "Hotel Best", "Barbara Hotel ", "Amanda Hotel", "Anna Hotel",
            "Hotel Catherine", "Melissa Hotel", "Sandra Hotel", "Hotel Julia", "Hotel Paula", "Hotel Kimberly",
            "Diane Hotel", "Betty Hotel", "Sharon Hotel", "Ruby Hotel", "Barbara", "Ann Hotel", "Phyllis Hotel",
            "Linda Hotel", "Marie the best", "Deborah Hotel", "Sara Hotel", "Gloria Hotel", "Karen Hotel", "Patricia Hotel",

    };


    /**
     Liste aleatoire hotel
     */
    public static List<Hotel> randomList(int count) {
        Random random = new Random();
        List<Hotel> items = new ArrayList<>();


        count = Math.min(count, HotelsName.length);

        while (items.size() < count) {
            items.add(new Hotel(HotelsName[random.nextInt(HotelsName.length)]));
        }

        return new ArrayList<>(items);
    }
}
