package com.example.prueba.Controlador;

import android.app.Activity;
import android.widget.EditText;

import com.example.prueba.R;

public class ValidarTextos {

    public ValidarTextos() {
    }

    public boolean validateBlank(EditText editText, Activity activity) {
        String text = editText.getText().toString().trim();
        if (isBlank(text)) {
            editText.setError(activity.getResources().getString(R.string.campo_vacio));
            editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean isBlank(String text) {
        return text.isEmpty();
    }
}
