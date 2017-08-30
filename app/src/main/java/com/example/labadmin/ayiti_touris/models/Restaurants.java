package com.example.labadmin.ayiti_touris.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by labadmin on 8/21/17.
 */


/**
 * Liste Restaurants
 */

public class Restaurants {
    public static final String[] RestaurantDiner = {
            "Marriott Port-au-Prince Hotel", "Magdoos", "Papaye", "La Table De Caius","La Plantation",
            "Les Jardins du Mupanah","Harry's","Kokoye Bar & Grill","Hotel NH Haiti El Rancho","Yanvalou","Portofino","Tiffany's","The View",
            "Fior Di Latte","Chicken Fiesta, Chinese American, Wings, Bar&Grill","Epi d'Or","Quartier Latin","Kay Atizan","Le Bistrot","Le Reserve","Boucan Grégoire","Restaurant la Citadelle","Restaurant chez la Rose",
            "Chateau Randolph","La Tentation","Le Latanier","Chez Wou","Fabrizio","La Coquille","La Dolce Vita","Chin-Chin","Basilic","Assiette Creole","La Taverne","La Voile","Le Beyrouth","Le Cafe des Arts","Le Coin des Artistes","Mozaik","Mc King",
            "Pizza Garden","Smookey's Bar and Grill","Jojo Restaurant","Cafe Albert","Redstone","L'Observatoire des Boutiliers","The View", "L'Estaminet", "Le Privilege", "Oceane Bar and Grill", "Le Petit Creux", "5 Coins", "Pizza Garden", "Orange Restaurant", "Exta'z", "Chez Gaelle", "Nounours", "Veritab Bar Resto",
            "Le Paradis des Consommateurs", "La Belle Etoile", "La Detente", "Patisserie Marie Beliard"
    };


    /**
     List aleatoire Restaurants
     */
    public static List<Restaurant> randomList(int count) {
        Random random = new Random();
        List<Restaurant> items = new ArrayList<>();


        count = Math.min(count, RestaurantDiner.length);

        while (items.size() < count) {
            items.add(new Restaurant(RestaurantDiner[random.nextInt(RestaurantDiner.length)]));
        }

        return new ArrayList<>(items);
    }
}


