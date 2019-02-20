package com.example.prueba.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prueba.Constantes;

import java.util.ArrayList;

public class SitioBD extends ConexionBD {
    private SQLiteDatabase db;

    public SitioBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = super.getWritableDatabase();
    }

    public boolean insertarSitio(Sitio sitio) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", sitio.getNombre());
        registro.put("descripcion", sitio.getDescripcion());
        registro.put("latitud", sitio.getLatitud());
        registro.put("longitud", sitio.getLongitud());

        db.insert(Constantes.tabla_local, null, registro);
        db.close();

        return true;
    }

    public ArrayList<Sitio> consultarSitios() {
        ArrayList<Sitio> sitios = new ArrayList<>();
        Sitio sitio = null;
        String query = "select * from sitio";
        Cursor fila = db.rawQuery(query, null);
        if (fila.moveToFirst()) {
            do {
                String nombre = fila.getString(0);
                String descripcion = fila.getString(1);
                Double latitud = fila.getDouble(2);
                Double longitud = fila.getDouble(3);
                sitio = new Sitio(nombre, descripcion, latitud, longitud);
                sitios.add(sitio);
            } while (fila.moveToNext());
        }
        fila.close();

        return sitios;
    }

    public void vaciarTabla() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from sitio");
    }
}