package com.example.prueba.Vista.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Alerts {

    public void Alerts() {

    }

    public void alertaInformacion(Context context, int tam) {
        final AlertDialog dialogo = new AlertDialog.Builder(context).create();
        dialogo.setTitle("Datos locales");
        dialogo.setMessage("Cantidad de datos locales " + tam);
        dialogo.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    public void alertaOperacion(Context context) {
        final AlertDialog dialogo = new AlertDialog.Builder(context).create();
        dialogo.setTitle("Correcto");
        dialogo.setMessage("La base de datos local ha sido limpiada");
        dialogo.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    public void alertaCargaDatos(Context context) {
        final AlertDialog dialogo = new AlertDialog.Builder(context).create();
        dialogo.setTitle("Correcto");
        dialogo.setMessage("Los datos locales han sido subidos al servidor");
        dialogo.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    public void alertaSincroFall(Context context) {
        final AlertDialog dialogo = new AlertDialog.Builder(context).create();
        dialogo.setTitle("Error");
        dialogo.setMessage("No hay datos para sincronizar");
        dialogo.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }
}
