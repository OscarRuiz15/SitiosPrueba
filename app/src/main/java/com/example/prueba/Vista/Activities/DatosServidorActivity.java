package com.example.prueba.Vista.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba.Constantes;
import com.example.prueba.Modelo.Sitio;
import com.example.prueba.R;
import com.example.prueba.Vista.Adapters.SitioAdapterRecycler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class DatosServidorActivity extends AppCompatActivity {

    //Componentes
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SitioAdapterRecycler sitioAdapterRecycler;
    private ProgressBar progressBar;
    private TextView tvNotFound;

    //Otros
    private ArrayList<Sitio> sitios;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_servidor);

        getSupportActionBar().setTitle("Sitios servidor");

        recyclerView = findViewById(R.id.recyclerSitios);
        tvNotFound = findViewById(R.id.tvNotFound);

        sitios = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        obtenerSitios();

    }

    private void obtenerSitios() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(context));
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constantes.URL + Constantes.URL_SITIOS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Sitio sitio = new Sitio(response.getJSONObject(i));
                        sitios.add(sitio);
                    } catch (JSONException e) {
                        tvNotFound.setVisibility(View.VISIBLE);
                    }
                }

                if (!sitios.isEmpty()) {
                    gridLayoutManager = new GridLayoutManager(context, 1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    sitioAdapterRecycler = new SitioAdapterRecycler(sitios, context);
                    recyclerView.setAdapter(sitioAdapterRecycler);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    tvNotFound.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvNotFound.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);

    }
}
