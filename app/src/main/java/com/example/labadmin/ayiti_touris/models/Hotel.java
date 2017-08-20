package com.example.labadmin.ayiti_touris.models;

import java.util.Random;
import com.example.labadmin.ayiti_touris.R;
/**
 * POJO de hotel
 */
public class Hotel {

    private String name;
    private int idDrawable;

    public Hotel(String name, int idDrawable) {
        this.name = name;
        this.idDrawable = idDrawable;
    }

    public Hotel(String name) {
        this.name = name;
        this.idDrawable = getRandomGirlDrawable();
    }

    public String getName() {
        return name;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    /**
     Asigner des images Aleatoire
     */
    private int getRandomGirlDrawable() {
        Random rnd = new Random();
        switch (rnd.nextInt(6)) {
            default:
            case 0:
                return R.drawable.hot1;
            case 1:
                return R.drawable.hot5;
            case 2:
                return R.drawable.hot3;
            case 3:
                return R.drawable.hot4;
            case 4:
                return R.drawable.hot6;

        }
    }
}
