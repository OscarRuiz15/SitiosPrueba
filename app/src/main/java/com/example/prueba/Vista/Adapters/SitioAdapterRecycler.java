package com.example.prueba.Vista.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prueba.Controlador.GlideApp;
import com.example.prueba.Modelo.Sitio;
import com.example.prueba.R;
import com.example.prueba.Vista.Activities.MapsActivity;

import java.util.ArrayList;

public class SitioAdapterRecycler extends RecyclerView.Adapter<SitioAdapterRecycler.ProductoHolder> {

    private ArrayList<Sitio> sitios;
    private Context context;
    private Cursor cursor;

    public SitioAdapterRecycler(ArrayList<Sitio> sitios, Context context) {
        this.sitios = sitios;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_lugares, parent, false);
        return new ProductoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder holder, int position) {
        position = holder.getAdapterPosition();

        String latitud = "" + sitios.get(position).getLatitud();
        String longitud = "" + sitios.get(position).getLongitud();
        String url = "https://api.mapbox.com/v4/mapbox.emerald/pin-m-star+ff0000(" + longitud + "," + latitud + ")/" + longitud + "," + latitud + ",16/200x200@2x.png?access_token=pk.eyJ1Ijoib3NjYXJydWl6MTUiLCJhIjoiY2pzYzA3MjRvMDFrdDN5dDBsaHVyNmtpdCJ9.HVKQeMVUWUULUCH6YTCa9w";

        GlideApp.with(context)
                .load(url)
                .into(holder.imagen);

        holder.nombre.setText(sitios.get(position).getNombre());
        holder.descripcion.setText(sitios.get(position).getDescripcion());

        final int finalPosition = position;
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, sitios.get(finalPosition).getNombre(), Snackbar.LENGTH_SHORT).show();

                Intent intent = new Intent(context, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sitio", sitios.get(finalPosition));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sitios.size();
    }

    class ProductoHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre;
        TextView descripcion;

        ProductoHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenSitio);
            nombre = itemView.findViewById(R.id.nombreSitio);
            descripcion = itemView.findViewById(R.id.descripcionSitio);
        }
    }

}
