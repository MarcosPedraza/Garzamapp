package com.uaeh.garza.garzamapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.uaeh.garza.garzamapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.ViewHolder> {

    Context context;
    ArrayList<Integer> imagenes = new ArrayList<>();


    public RutasAdapter(Context context, ArrayList<Integer> imgs)
    {
        this.context = context;
        this.imagenes = imgs;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ruta,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(imagenes.get(position))
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_ruta);

        }
    }

}
