package com.example.labadmin.ayiti_touris.AdaptersOnline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.labadmin.ayiti_touris.ModelsOnline.DepartementModel;
import com.example.labadmin.ayiti_touris.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterDepartement extends ArrayAdapter<DepartementModel> {
    // View lookup cache
        RatingBar ratingBar;
    private static class ViewHolder {
        TextView nomDepartement;

        TextView descriptionDep;

        ImageView imageDep,imageCarteDep;

        TextView detailDep;

        private List<DepartementModel> list;
        ArrayList<DepartementModel> ListDepartement;
    }

    public AdapterDepartement(Context context, ArrayList<DepartementModel> users) {
        super(context, R.layout.recycler_view_item, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        final DepartementModel departement = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        AdapterDepartement.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new AdapterDepartement.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.recycler_view_item, parent, false);

            viewHolder.nomDepartement = (TextView) convertView.findViewById(R.id.textView_Nomdep);
            viewHolder.descriptionDep = (TextView) convertView.findViewById(R.id.textView_desc);
            viewHolder.imageDep = (ImageView) convertView.findViewById(R.id.imageView_Dep);
            //viewHolder.imageCarteDep = (ImageView) convertView.findViewById(R.id.ivfavorite);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (AdapterDepartement.ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        //Endroit endroit1=listgetItem(position);
        viewHolder.nomDepartement.setText(departement.getNom_dep());
        viewHolder.descriptionDep.setText(departement.getDescription_dep());
        Picasso.with(getContext()).load(departement.getImage_dep())
                //.transform(new RoundedCornersTransformation(20, 20))
                //.placeholder(R.drawable.placeholder2)
                //.resize(220, 130)
                .into(viewHolder.imageDep);

       /* Glide.with(viewHolder.imageCarteDep.getContext())
                .load(departement.getImage_carte_dep())
                //.centerCrop()
                //.resize(240,120)
                .into(viewHolder.imageCarteDep);*/

        // viewHolder.home.setText(user.hometown);
        // Return the completed view to render on screen.
        //viewHolder.textrecette.setText(recettes.fromListMap()+);
        return convertView;
    }


}
