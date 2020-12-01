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
import android.provider.Settings;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewIcone;
    EditText editTextVille;
    TextView textViewVille, textViewJour, textViewTemperature, textViewCondition, textViewTMin, textViewTMax, textViewHumidite,
            textViewLeverSoleil, textViewCoucherSoleil, textViewVent;

    // Floating Action Button
    FloatingActionButton villeFab, localisationFab;
    ExtendedFloatingActionButton fab;
    TextView villeActionText, localisationActionText;
    Boolean isAllFabsVisible;

    // GPS
    Location gps_loc = null, network_loc = null;

    // URL de l'API météo
    String url, villeGPS;

    private boolean recupLocalisation = false;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Floating Action Button
        fab = findViewById(R.id.fab);
        villeFab = findViewById(R.id.villeFab);
        localisationFab = findViewById(R.id.localisationFab);

        villeActionText = findViewById(R.id.ville_action_text);
        localisationActionText = findViewById(R.id.localisation_action_text);

        villeFab.setVisibility(View.GONE);
        localisationFab.setVisibility(View.GONE);
        villeActionText.setVisibility(View.GONE);
        localisationActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        fab.shrink();

        fab.setOnClickListener(
                view -> {
                    if (!isAllFabsVisible) {
                        ViewCompat.animate(fab)
                                .rotation(135.0F)
                                .withLayer()
                                .setDuration(300L)
                                .setInterpolator(new OvershootInterpolator(10.0F))
                                .start();
                        villeFab.show();
                        localisationFab.show();
                        villeActionText.setVisibility(View.VISIBLE);
                        localisationActionText.setVisibility(View.VISIBLE);
                        fab.extend();
                        isAllFabsVisible = true;
                    } else {
                        ViewCompat.animate(fab)
                                .rotation(0.0F)
                                .withLayer()
                                .setDuration(300L)
                                .setInterpolator(new OvershootInterpolator(10.0F))
                                .start();
                        villeFab.hide();
                        localisationFab.hide();
                        villeActionText.setVisibility(View.GONE);
                        localisationActionText.setVisibility(View.GONE);
                        fab.shrink();
                        isAllFabsVisible = false;
                    }
                });

        // Récupérer la localisation de l'emplacement du téléphone
        localisationFab.setOnClickListener(
                view -> {
                    recupLocalisation = true;
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                    // Masquer le menu
                    ViewCompat.animate(fab)
                            .rotation(0.0F)
                            .withLayer()
                            .setDuration(300L)
                            .setInterpolator(new OvershootInterpolator(10.0F))
                            .start();
                    villeFab.hide();
                    localisationFab.hide();
                    villeActionText.setVisibility(View.GONE);
                    localisationActionText.setVisibility(View.GONE);
                    fab.shrink();
                    isAllFabsVisible = false;
                });


        // Saisir une ville manuellement
        villeFab.setOnClickListener(
                view -> {
                    startActivity(new Intent(getApplicationContext(), VilleActivity.class));
                    finish();
                });

        // Demander la permission LOCALISATION
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        // Références
        imageViewIcone = findViewById(R.id.imageViewIcone);
        editTextVille = findViewById(R.id.editTextVille);
        textViewVille = findViewById(R.id.textViewVille);
        textViewCondition = findViewById(R.id.textViewCondition);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewTMin = findViewById(R.id.textViewTMin);
        textViewTMax = findViewById(R.id.textViewTMax);
        textViewHumidite = findViewById(R.id.textViewHumidite);
        textViewLeverSoleil = findViewById(R.id.textViewLeverSoleil);
        textViewCoucherSoleil = findViewById(R.id.textViewCoucherSoleil);
        textViewVent = findViewById(R.id.textViewVent);
        textViewJour = findViewById(R.id.textViewJour);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Si on a la permission LOCALISATION
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // Vérifier les permissions réseaux et GPS plus précis
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
                    villeGPS = addresses.get(0).getLocality();
                    url = "https://www.prevision-meteo.ch/services/json/" + villeGPS;
                }

                // Gestion des erreurs
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Récupérer la ville saisie dans la VilleActivity
            Bundle extras = getIntent().getExtras();
            if (extras != null)
                url = "https://www.prevision-meteo.ch/services/json/" + extras.getString("ville");

            // Bouton récupérer la localisation
            if (recupLocalisation)
                url = "https://www.prevision-meteo.ch/services/json/" + villeGPS;


            // Si on n'a pas la permission LOCALISATION
        } else if (grantResults.length > 0) {
            // Récupérer la météo de Paris
            url = "https://www.prevision-meteo.ch/services/json/Paris";

            // Affiche une boîte de texte
            Snackbar.make(findViewById(R.id.constraintLayout), "Impossible de récupérer votre localisation", Snackbar.LENGTH_LONG)
                    .setAction("Activer", v -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .show();
        }


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Récupérer les données
                        JSONObject jsonObject = new JSONObject(response);

                        // city_info
                        JSONObject city_info = jsonObject.getJSONObject("city_info");
                        String ville = city_info.getString("name");
                        String leveSoleil = city_info.getString("sunrise");
                        String coucheSoleil = city_info.getString("sunset");

                        // current_condition
                        JSONObject current_condition = jsonObject.getJSONObject("current_condition");
                        String icone = current_condition.getString("icon_big");
                        String tmp = current_condition.getString("tmp");
                        String condition = current_condition.getString("condition");
                        String humidite = current_condition.getString("humidity");
                        String vent = current_condition.getString("wnd_gust");

                        // fcst_day_0
                        JSONObject fcst_day_0 = jsonObject.getJSONObject("fcst_day_0");
                        String day_long = fcst_day_0.getString("day_long");
                        String tmin = fcst_day_0.getString("tmin");
                        String tmax = fcst_day_0.getString("tmax");

                        // Afficher les données du jour
                        Picasso.get().load(icone).into(imageViewIcone);
                        textViewVille.setText(ville);
                        textViewJour.setText(day_long);
                        textViewTemperature.setText(tmp + " °C");
                        textViewCondition.setText(condition);
                        textViewTMin.setText(tmin + " °C");
                        textViewTMax.setText(tmax + " °C");
                        textViewHumidite.setText(humidite + " %");
                        textViewLeverSoleil.setText(leveSoleil);
                        textViewCoucherSoleil.setText(coucheSoleil);
                        textViewVent.setText(vent + " / km");

                        // Si la ville saisie n'a pas été trouvée
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog alertDialog = new AlertDialog.Builder(this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Erreur")
                                .setMessage("La ville saisie n'a pas été trouvé")
                                .setPositiveButton("OK", (dialogInterface, i) -> {
                                })
                                .show();
                        alertDialog.setCanceledOnTouchOutside(false);
                    }
                },

                error -> Toast.makeText(this, "Désolé, ça n'a pas fonctionné", Toast.LENGTH_SHORT).show());

        // Ajouter la requête à la RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quitter")
                .setMessage("Voulez-vous vraiment quitter l'application")
                .setPositiveButton("Oui", (dialogInterface, i) -> {
                })
                .setNegativeButton("Non", (dialogInterface, i) -> {
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}