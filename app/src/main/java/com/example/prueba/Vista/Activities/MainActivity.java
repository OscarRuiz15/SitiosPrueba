package com.example.prueba.Vista.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba.Constantes;
import com.example.prueba.Modelo.ConexionBD;
import com.example.prueba.Modelo.Sitio;
import com.example.prueba.Modelo.SitioBD;
import com.example.prueba.R;
import com.example.prueba.Vista.Adapters.SitioAdapterRecycler;
import com.example.prueba.Vista.Dialog.AgregarSitioDialog;
import com.example.prueba.Vista.Dialog.Alerts;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    //Componentes
    private RecyclerView recyclerView;
    private SitioAdapterRecycler sitioAdapterRecycler;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar progressBar;
    private TextView tvNotFound;
    private View coordinatorLayout;

    //Otros
    private Context context = this;
    private ArrayList<Sitio> sitios = new ArrayList();
    private SitioBD sbd;
    private Alerts alerts = new Alerts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sitios local");

        recyclerView = findViewById(R.id.recyclerSitios);
        tvNotFound = findViewById(R.id.tvNotFound);
        coordinatorLayout = findViewById(R.id.coordinatorMain);

        ConexionBD conexion = new ConexionBD(this, Constantes.bdLocal, null, 1);
        SQLiteDatabase db;

        sitios = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        try {
            db = SQLiteDatabase.openDatabase(Constantes.DB_PATH, null, SQLiteDatabase.OPEN_READONLY);

            sbd = new SitioBD(context, Constantes.bdLocal, null, 1);
            sitios = sbd.consultarSitios();
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            db.close();
        } catch (SQLiteException e) {
            db = conexion.getWritableDatabase();
            if (conexion != null) {
                sbd = new SitioBD(this, Constantes.bdLocal, null, 1);
                sitios = sbd.consultarSitios();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Snackbar.make(coordinatorLayout, "Base de Datos local creada", LENGTH_SHORT).show();
            }
        }

        if (sitios.size() == 0) {
            tvNotFound.setVisibility(View.VISIBLE);
        } else {
            gridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            sitioAdapterRecycler = new SitioAdapterRecycler(sitios, context);
            recyclerView.setAdapter(sitioAdapterRecycler);
            tvNotFound.setVisibility(View.GONE);
        }
    }

    public void agregarSitio(View view) {
        new AgregarSitioDialog(context, sitios, sitioAdapterRecycler, recyclerView, tvNotFound);
    }

    private void postDatosLocales() throws JSONException {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = null;
        String request = "";

        if (sitios.size() == 0) {
            alerts.alertaSincroFall(context);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < sitios.size(); i++) {
                jsonBody = sitios.get(i).getJSONSitio();

                final String mRequestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL + Constantes.URL_SITIOS + "/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() {
                        return mRequestBody == null ? null : mRequestBody.getBytes(StandardCharsets.UTF_8);
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                requestQueue.add(stringRequest);
            }

            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            limpiarBD();
            alerts.alertaCargaDatos(context);
        }
    }

    public void limpiarBD() {
        sbd.vaciarTabla();
        sitios = sbd.consultarSitios();
        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        sitioAdapterRecycler = new SitioAdapterRecycler(sitios, context);
        recyclerView.setAdapter(sitioAdapterRecycler);
        tvNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            sitios = sbd.consultarSitios();
            alerts.alertaInformacion(context, sitios.size());
        }

        if (id == R.id.action_limpiar_bd) {
            limpiarBD();
            alerts.alertaOperacion(context);
        }

        if (id == R.id.action_sincronizar) {
            try {
                postDatosLocales();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        if (id == R.id.action_servidor) {
            Intent intent = new Intent(MainActivity.this, DatosServidorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
