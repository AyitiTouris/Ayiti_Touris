package com.example.labadmin.ayiti_touris.ModelsOnline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartementModel implements Serializable {
    public String nom_dep;
    public String description_dep ;
    public String details_dep;
    public String image_dep;
    public String image_carte_dep;


    public String getNom_dep() {
        return nom_dep;
    }

    public void setNom_dep(String nom_dep) {
        this.nom_dep = nom_dep;
    }

    public String getDescription_dep() {
        return description_dep;
    }

    public void setDescription_dep(String description_dep) {
        this.description_dep = description_dep;
    }

    public String getDetails_dep() {
        return details_dep;
    }

    public void setDetails_dep(String details_dep) {
        this.details_dep = details_dep;
    }

    public String getImage_dep() {
        return image_dep;
    }

    public void setImage_dep(String image_dep) {
        this.image_dep = image_dep;
    }

    public String getImage_carte_dep() {
        return image_carte_dep;
    }

    public void setImage_carte_dep(String image_carte_dep) {
        this.image_carte_dep = image_carte_dep;
    }

    public static ArrayList<DepartementModel> fromListMap(List<Map> map) {

        ArrayList<DepartementModel> departements = new ArrayList();

        for(int i = 0; i < map.size(); i++) {
            DepartementModel departement = new DepartementModel();
            departement.setDescription_dep((String)map.get(i).get("description_dep"));
            departement.setNom_dep((String)map.get(i).get("nom_departement"));
            departement.setDetails_dep((String)map.get(i).get("details"));
            departement.setImage_dep((String)map.get(i).get("image_dep"));
            departement.setImage_carte_dep((String)map.get(i).get("image_carte_dep"));
            departements.add(departement);
        }

        return departements;
    }
}
