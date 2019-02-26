package com.example.labadmin.ayiti_touris.AdaptersOnline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by domin on 30-Aug-17.
 */

public class AdapterEndroit extends ArrayAdapter<Endroit> {
    // View lookup cache
    RatingBar ratingBar;
    private static class ViewHolder {
        TextView nomEndroit;

        TextView adresseEndroit;

        TextView telephoneEndroit;
        ImageView image1Endroit,favoriteImg;

        TextView etoile;
        RatingBar ratingBar;
        private List<Endroit> list;
        ArrayList<Endroit> ListEndroit;
    }

    public AdapterEndroit(Context context, ArrayList<Endroit> users) {
        super(context, R.layout.item_liste_endroit_online, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

       final Endroit endroit = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_liste_endroit_online, parent, false);

            viewHolder.nomEndroit = (TextView) convertView.findViewById(R.id.tvnomEndroit);
            viewHolder.adresseEndroit = (TextView) convertView.findViewById(R.id.tvadresseEndroit);
            viewHolder.telephoneEndroit = (TextView) convertView.findViewById(R.id.tvtelephoneEndroit);
            viewHolder.etoile = (TextView) convertView.findViewById(R.id.tvetoile);
           viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
            viewHolder.image1Endroit = (ImageView) convertView.findViewById(R.id.ivimage1Endroit);
            viewHolder.favoriteImg = (ImageView) convertView.findViewById(R.id.ivfavorite);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
       //Endroit endroit1=listgetItem(position);
        viewHolder.nomEndroit.setText(endroit.getNomEndroit());
        viewHolder.adresseEndroit.setText(endroit.getVille());
        viewHolder.telephoneEndroit.setText(endroit.getTelephoneEndroit());
       // viewHolder.image1Endroit.setText(endroit.getImage1Endroit());
            viewHolder.favoriteImg.setVisibility(View.GONE);
        if(endroit.getEtoile()!=null) {

            viewHolder.ratingBar.setRating(Float.parseFloat(endroit.getEtoile()));
            viewHolder.etoile.setVisibility(View.GONE);
        }else if(endroit.getEtoile()==null) {

            viewHolder.ratingBar.setVisibility(View.GONE);
            viewHolder.etoile.setText(endroit.getEtoile());
        }




       // ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        //ratingBar.setRating(endroit.getEtoile());

        Picasso.with(getContext())
                .load(endroit.getImage1Endroit())
                //.transform(new RoundedCornersTransformation(20, 20))
               // .placeholder(R.drawable.placeholder2)
                .resize(220, 130).into(viewHolder.image1Endroit);

      /*  Glide.with(viewHolder.image1Hotel.getContext())
                .load(hotel.getImage1Hotel())
                //.centerCrop()
                .resize(240,120)
                .into(viewHolder.image1Hotel);*/

        // viewHolder.home.setText(user.hometown);
        // Return the completed view to render on screen.
        //viewHolder.textrecette.setText(recettes.fromListMap()+);
        return convertView;
    }

  
}
