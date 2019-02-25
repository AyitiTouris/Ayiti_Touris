package com.example.labadmin.ayiti_touris.models;

/**
 * Created by labadmin on 8/21/17.
 */

import com.example.labadmin.ayiti_touris.R;

import java.util.Random;

public class Evenement {

    private String name;
    private int idDrawable;

    public Evenement(String name, int idDrawable) {
        this.name = name;
        this.idDrawable = idDrawable;
    }

    public Evenement(String name) {
        this.name = name;
       // this.idDrawable = getRandomGirlDrawable();
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
  /*  private int getRandomGirlDrawable() {
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
    }*/
}
