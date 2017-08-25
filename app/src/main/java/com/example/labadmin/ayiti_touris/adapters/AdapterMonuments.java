package com.example.labadmin.ayiti_touris.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.models.Monument;
import com.example.labadmin.ayiti_touris.DetailsMonumentActivity;

import java.util.List;

public class AdapterMonuments extends RecyclerView.Adapter<AdapterMonuments.SimpleViewHolder> implements ItemClickMonuments {
    private final Context context;
    private List<Monument> items;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // variable des elements
        public TextView nombre;
        public ImageView avatar;
        public ItemClickMonuments listener;

        public SimpleViewHolder(View v, ItemClickMonuments listener) {
            super(v);

            nombre = v.findViewById(R.id.list_item_textview);
            avatar = v.findViewById(R.id.avatar);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }

    public AdapterMonuments(Context context, List<Monument> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_endroit, viewGroup, false);
        return new SimpleViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int i) {
        Monument currentItem = items.get(i);
        viewHolder.nombre.setText(currentItem.getName());
        Glide.with(viewHolder.avatar.getContext())
                .load(currentItem.getIdDrawable())
                .centerCrop()
                .into(viewHolder.avatar);
    }


    @Override
    public void onItemClick(View view, int position) {
        DetailsMonumentActivity.createInstance(
                (Activity) context, items.get(position));
    }


}


interface ItemClickMonuments {
    void onItemClick(View view, int position);
}