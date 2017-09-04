package com.example.labadmin.ayiti_touris.ModelsOnline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by domin on 30-Aug-17.
 */

public class Endroit implements Serializable {

    // private String ime; //in database
    private String nomEndroit; //in database
    private String adresseEndroit; //in database
    private String telephoneEndroit;
    private String image1Endroit;
    private String image2Endroit;
    private String image3Endroit;
    private String image4Endroit;
    private String image5Endroit;
    private String emailEndroit;
    private String sitewebEndroit;
    private String informationEndroit;
    private String laltitudeEndroit;
    private String longitudeEndroit;
    private int etoile;
    private String ratingBar;

    public String getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(String ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getNomEndroit() {
        return nomEndroit;
    }

    public void setNomEndroit(String nomEndroit) {
        this.nomEndroit = nomEndroit;
    }

    public String getAdresseEndroit() {
        return adresseEndroit;
    }

    public void setAdresseEndroit(String adresseEndroit) {
        this.adresseEndroit = adresseEndroit;
    }

    public String getTelephoneEndroit() {
        return telephoneEndroit;
    }

    public void setTelephoneEndroit(String telephoneEndroit) {
        this.telephoneEndroit = telephoneEndroit;
    }

    public String getImage1Endroit() {
        return image1Endroit;
    }

    public void setImage1Endroit(String image1Endroit) {
        this.image1Endroit = image1Endroit;
    }

    public String getImage2Endroit() {
        return image2Endroit;
    }

    public void setImage2Endroit(String image2Endroit) {
        this.image2Endroit = image2Endroit;
    }

    public String getImage3Endroit() {
        return image3Endroit;
    }

    public void setImage3Endroit(String image3Endroit) {
        this.image3Endroit = image3Endroit;
    }

    public String getImage4Endroit() {
        return image4Endroit;
    }

    public void setImage4Endroit(String image4Endroit) {
        this.image4Endroit = image4Endroit;
    }

    public String getImage5Endroit() {
        return image5Endroit;
    }

    public void setImage5Endroit(String image5ndroit) {
        this.image5Endroit = image5ndroit;
    }

    public String getEmailEndroit() {
        return emailEndroit;
    }

    public void setEmailEndroit(String emailEndroit) {
        this.emailEndroit = emailEndroit;
    }

    public String getSitewebEndroit() {
        return sitewebEndroit;
    }

    public void setSitewebEndroit(String sitewebEndroit) {
        this.sitewebEndroit = sitewebEndroit;
    }

    public String getInformationEndroit() {
        return informationEndroit;
    }

    public void setInformationEndroit(String informationEndroit) {
        this.informationEndroit = informationEndroit;
    }

    public String getLaltitudeEndroit() {
        return laltitudeEndroit;
    }

    public void setLaltitudeEndroit(String laltitudeEndroit) {
        this.laltitudeEndroit = laltitudeEndroit;
    }

    public String getLongitudeEndroit() {
        return longitudeEndroit;
    }

    public void setLongitudeEndroit(String longitudeEndroit) {
        this.longitudeEndroit = longitudeEndroit;
    }

    public int getEtoile() {
        return etoile;
    }

    public void setEtoile(int etoile) {
        this.etoile = etoile;
    }

    public static ArrayList<Endroit> fromListMap(List<Map> map) {

        ArrayList<Endroit> endroits = new ArrayList();

        for(int i = 0; i < map.size(); i++) {
            Endroit endroit = new Endroit();
            endroit.setNomEndroit((String) map.get(i).get("nom"));
            endroit.setAdresseEndroit((String) map.get(i).get("adresse"));
            endroit.setImage1Endroit((String) map.get(i).get("image1"));
            endroit.setImage2Endroit((String) map.get(i).get("image2"));
            endroit.setImage3Endroit((String) map.get(i).get("image3"));
            endroit.setImage4Endroit((String) map.get(i).get("image4"));
            endroit.setImage5Endroit((String) map.get(i).get("image5"));
            endroit.setEtoile((int) map.get(i).get("etoile"));
            endroit.setTelephoneEndroit((String) map.get(i).get("telephone"));
            endroit.setLaltitudeEndroit((String) map.get(i).get("laltitude"));
            endroit.setLongitudeEndroit((String) map.get(i).get("longitude"));
            endroit.setEmailEndroit((String) map.get(i).get("email"));
            endroit.setSitewebEndroit((String) map.get(i).get("siteweb"));
            endroit.setInformationEndroit((String) map.get(i).get("information"));


            endroits.add(endroit);
        }

        return endroits;
    }
}
