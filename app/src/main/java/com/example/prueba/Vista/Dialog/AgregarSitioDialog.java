package com.example.prueba.Vista.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prueba.Constantes;
import com.example.prueba.Controlador.ValidarTextos;
import com.example.prueba.Modelo.Sitio;
import com.example.prueba.Modelo.SitioBD;
import com.example.prueba.R;
import com.example.prueba.Vista.Adapters.SitioAdapterRecycler;

import java.util.ArrayList;

public class AgregarSitioDialog {

    private Dialog dialog;
    private Context context;
    private EditText nombre;
    private EditText descripcion;
    private EditText latitud;
    private EditText longitud;
    private Button agregar;
    private RecyclerView recyclerView;
    private SitioAdapterRecycler sitioAdapterRecycler;
    private ArrayList<Sitio> sitios;
    private TextView tvNotFound;

    public AgregarSitioDialog(Context context, ArrayList<Sitio> sitios, SitioAdapterRecycler sitioAdapterRecycler, RecyclerView recyclerView, TextView tvNotFound) {
        this.context = context;
        this.sitioAdapterRecycler = sitioAdapterRecycler;
        this.recyclerView = recyclerView;
        this.sitios = sitios;
        this.tvNotFound = tvNotFound;

        crearDialogo();
    }

    public void crearDialogo() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_agregar_sitio);

        nombre = dialog.findViewById(R.id.tiNombre);
        descripcion = dialog.findViewById(R.id.tiDescripcion);
        latitud = dialog.findViewById(R.id.tiLatitud);
        longitud = dialog.findViewById(R.id.tiLongitud);
        agregar = dialog.findViewById(R.id.btnAgregarSitio);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearSitio();
            }
        });

        dialog.show();
    }

    public void crearSitio() {
        ValidarTextos validarTextos = new ValidarTextos();
        boolean nombreValido = validarTextos.validateBlank(nombre, (Activity) context);
        boolean descripcionValido = validarTextos.validateBlank(descripcion, (Activity) context);
        boolean latitudValido = validarTextos.validateBlank(latitud, (Activity) context);
        boolean longitudValido = validarTextos.validateBlank(longitud, (Activity) context);

        if (nombreValido && descripcionValido && latitudValido && longitudValido) {
            String nombreSitio = nombre.getText().toString().trim();
            String descripcionSitio = descripcion.getText().toString().trim();
            Double latitudSitio = Double.parseDouble(latitud.getText().toString().trim());
            Double longitudSitio = Double.parseDouble(longitud.getText().toString().trim());

            Sitio sitio = new Sitio(nombreSitio, descripcionSitio, latitudSitio, longitudSitio);
            sitios.add(sitio);

            SitioBD sbd = new SitioBD(context, Constantes.bdLocal, null, 1);
            sbd.insertarSitio(sitio);

            tvNotFound.setVisibility(View.GONE);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            sitioAdapterRecycler = new SitioAdapterRecycler(sitios, context);
            recyclerView.setAdapter(sitioAdapterRecycler);

            dialog.dismiss();
        }
    }
}
