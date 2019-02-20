package com.example.prueba.Vista.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.prueba.Modelo.Sitio;
import com.example.prueba.Modelo.Ubicacion;
import com.example.prueba.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Sitio sitio;
    private ArrayList<LatLng> markerPoints;
    private LatLng coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        markerPoints = new ArrayList<LatLng>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (savedInstanceState != null) {
            sitio = (Sitio) savedInstanceState.getSerializable("sitio");
            markerPoints = (ArrayList<LatLng>) savedInstanceState.getSerializable("markerPoints");
        } else {
            sitio = (Sitio) bundle.getSerializable("sitio");
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("sitio", sitio);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        sitio = (Sitio) savedInstanceState.getSerializable("sitio");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        ponerSitio();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(sitio.getLatitud(), sitio.getLongitud()), 15);
        mMap.animateCamera(cameraUpdate);

        mMap.setOnMapClickListener(new com.google.android.gms.maps.GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Ubicacion ubic = new Ubicacion(point.latitude, point.longitude);
                markerPoints.add(new LatLng(ubic.getLatitud(), ubic.getLongitud()));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }

    private void ponerSitio() {
        coordenadas = new LatLng(sitio.getLatitud(), sitio.getLongitud());
        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(sitio.getNombre())
                .snippet(sitio.getDescripcion())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    public void trazarPolyline(View view) {
        PolylineOptions line = new PolylineOptions();

        for (int i = 0; i < markerPoints.size(); i++) {
            line.add(markerPoints.get(i));
        }

        line.width(5).color(Color.RED);
        mMap.addPolyline(line);
    }

    public void limpiarPolyline(View view) {
        markerPoints = new ArrayList<LatLng>();
        mMap.clear();
        ponerSitio();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_estilos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_tema1) {
            mMap.setMapStyle(null);
        }
        if (id == R.id.action_tema2) {
            mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));
        }
        return super.onOptionsItemSelected(item);
    }

}
