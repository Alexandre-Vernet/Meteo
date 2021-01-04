package com.ynov.vernet.projetdevmobilemto;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Conversion extends AppCompatActivity {

    private static final String TAG = "Conversion";

    public String convertir(String prefVent, String vent) {

        switch (prefVent) {
            case "km/h":
                return vent;

            case "m/s": {
                double ventDouble = Double.parseDouble(vent);
                Log.d(TAG, "vent: " + ventDouble);
                ventDouble = ventDouble / 3.6;
                ventDouble = Math.round(ventDouble * 10) / 10.0;
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(ventDouble);
            }

            case "mph": {
                double ventDouble = Double.parseDouble(vent);
                Log.d(TAG, "vent: " + ventDouble);
                ventDouble = ventDouble / 1.609;
                ventDouble = Math.round(ventDouble * 10) / 10.0;
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(ventDouble);
            }

            case "kts": {
                double ventDouble = Double.parseDouble(vent);
                Log.d(TAG, "vent: " + ventDouble);
                ventDouble = ventDouble / 1.852;
                ventDouble = Math.round(ventDouble * 10) / 10.0;
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(ventDouble);
            }

            default:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.error))
                        .setMessage(R.string.erreur_conversion_unite)
                        .setPositiveButton("Ok", null)
                        .show();
                alertDialog.setCanceledOnTouchOutside(false);
        }

        return "erreur";
    }
}
