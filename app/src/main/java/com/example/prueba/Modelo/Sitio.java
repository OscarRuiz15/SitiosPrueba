package com.example.prueba.Modelo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Sitio implements Serializable {

    public String nombre;
    public String descripcion;
    public Double latitud;
    public Double longitud;

    public Sitio() {

    }

    public Sitio(String nombre, String descripcion, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Sitio(JSONObject jsonObject) throws JSONException {
        this.nombre = jsonObject.getString("nombre");
        this.descripcion = jsonObject.getString("descripcion");
        this.latitud = Double.parseDouble(jsonObject.getString("latitud"));
        this.longitud = Double.parseDouble(jsonObject.getString("longitud"));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Sitio{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }

    public JSONObject getJSONSitio() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idSitio", 0);
        jsonObject.put("nombre", nombre);
        jsonObject.put("descripcion", descripcion);
        jsonObject.put("latitud", latitud);
        jsonObject.put("longitud", longitud);
        return jsonObject;
    }
}
