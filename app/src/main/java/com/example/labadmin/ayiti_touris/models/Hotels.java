package com.example.labadmin.ayiti_touris.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Liste Hotels
 */
public class Hotels {

    public static final String[] HotelsName = {
            "Best Western Premier", "Royal Oasis Hotel", "Marriott Hotel", "Hotel NH Haiti El Rancho", "Karibe Hotel",
            "Kinam Hotel", "La Reserve Hotel", "Hotel Visa Lodge", "Hotel Oloffson", "Le Plaza Hotel", "Hotel Prince",
            "Hotel Villa Therese", "Seven Stars Hotel", "Servotel", "Habitation Hatt Hotel", "Corail Suites", "La Maison Hotel",
            "Pacot Breeze Hotel", "La Lorraine", "Ideal Villa Hotel", "Universal Hotel", "Pavillon des Receptions & Hotel", "La Pepiniere Hotel", "Palm Residence Port-au-Prince",
            "Ranch Le Montcel", "Palm Inn Hotel", "Le Monte Cristo Hotel and Suites", "Elite Hotel Haiti", "Kayanm Hotel", "Hotel Luxe Confort", "Hotel Montana ","Le Jardin Hotel","Le Perroquet",
            "Renaissance Hotel","Kingdom Hotel", "Hotel Montana"

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
