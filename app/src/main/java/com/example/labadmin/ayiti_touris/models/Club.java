package com.example.labadmin.ayiti_touris.models;

import com.example.labadmin.ayiti_touris.R;

import java.util.Random;

/**
 * Created by AHTT - V P-FEMININ on 8/25/2017.
 */

public class Club {
    private String name;
    private int idDrawable;

    public Club(String name, int idDrawable) {
        this.name = name;
        this.idDrawable = idDrawable;
    }

    public Club(String name) {
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
                return R.drawable.club1;
            case 1:
                return R.drawable.club2;
            case 2:
                return R.drawable.club3;
            case 3:
                return R.drawable.club4;
            case 4:
                return R.drawable.club5;

        }
    }
}
