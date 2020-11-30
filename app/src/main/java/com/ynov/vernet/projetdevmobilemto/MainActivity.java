package com.ynov.vernet.projetdevmobilemto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Location gps_loc = null, network_loc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Démarrer Ville Activity
        Intent intent = new Intent(getApplicationContext(), VilleActivity.class);
        startActivity(intent);
        finish();

        // Demander la permission LOCALISATION
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Si on a la permission LOCALISATION
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // Vérifier les permissions
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            try {
                assert locationManager != null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    return;
                gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Récupérer les coordonnées fournies par le GPS
            double latitude;
            double longitude;
            Location final_loc;
            if (gps_loc != null) {
                final_loc = gps_loc;
                latitude = final_loc.getLatitude();
                longitude = final_loc.getLongitude();
            } else if (network_loc != null) {
                final_loc = network_loc;
                latitude = final_loc.getLatitude();
                longitude = final_loc.getLongitude();
            } else {
                latitude = 0.0;
                longitude = 0.0;
            }

            // Déterminer la position en fonction des coordonnées du GPS
            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null) {
                    // Récupérer le nom de la ville
                    String ville = addresses.get(0).getLocality();
                    Toast.makeText(this, ville, Toast.LENGTH_SHORT).show();
                }

                // Gestion des erreurs
            } catch (Exception e) {
                e.printStackTrace();
            }


            // Si on n'a pas la permission LOCALISATION
        } else if (grantResults.length > 0) {
            Toast.makeText(this, "Vous n'avez pas la permission localisation", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quitter")
                .setMessage("Voulez-vous vraiment quitter l'application")
                .setPositiveButton("Oui", (dialogInterface, i) -> {
                    finish();
                })
                .setNegativeButton("Non", (dialogInterface, i) -> {
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}