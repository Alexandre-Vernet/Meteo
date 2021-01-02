package com.ynov.vernet.projetdevmobilemto;

import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewIcone;
    EditText editTextVille;
    TextView textViewVille, textViewJour, textViewTemperature, textViewCondition, textViewTMin, textViewTMax, textViewHumidite,
            textViewLeverSoleil, textViewCoucherSoleil, textViewVent;

    // Floating Action Button
    ExtendedFloatingActionButton fab;
    FloatingActionButton villeFab, localisationFab, parametreFab;
    Boolean isAllFabsVisible;

    // GPS
    Location gps_loc = null, network_loc = null;

    String url, ville;

    boolean recupLocalisation = false;

    // Débug
    private static final String TAG = "MainActivity";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



        // Floating Action Button
        fab = findViewById(R.id.fab);
        villeFab = findViewById(R.id.villeFab);
        localisationFab = findViewById(R.id.localisationFab);
        parametreFab = findViewById(R.id.parametreFab);


        villeFab.setVisibility(View.GONE);
        localisationFab.setVisibility(View.GONE);
        parametreFab.setVisibility(View.GONE);

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
                        parametreFab.show();
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
                        parametreFab.hide();
                        fab.shrink();
                        isAllFabsVisible = false;
                    }
                });

        // Récupérer la localisation de l'emplacement du téléphone
        localisationFab.setOnClickListener(
                view -> {
                    recupLocalisation = true;
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                    // Masquer le menu
                    ViewCompat.animate(fab)
                            .rotation(0.0F)
                            .withLayer()
                            .setDuration(300L)
                            .setInterpolator(new OvershootInterpolator(10.0F))
                            .start();
                    isAllFabsVisible = false;
                });


        // Saisir une ville manuellement
        villeFab.setOnClickListener(
                view -> {
                    startActivity(new Intent(getApplicationContext(), VilleActivity.class));
                    finish();
                });

        // Paramètres
        parametreFab.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ParametresActivity.class));
            finish();
        });

        // Demander la permission LOCALISATION
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
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

            // Récupérer les coordonnées fournies par le GPS ou réseau
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
                    ville = addresses.get(0).getLocality();
                    url = "https://www.prevision-meteo.ch/services/json/" + ville;

                    // Mettre à jour le widget
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                    views.setTextViewText(R.id.widgetTemperature, "Coucou tobby");

                    // Stocker la ville dans la mémoire
                    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("ville", ville);
                    editor.apply();
                }

                // Gestion des erreurs
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Si on n'a pas la permission LOCALISATION
        } else if (grantResults.length > 0) {

            if (ville == null) {

                // Récupération de la météo de Paris
                url = "https://www.prevision-meteo.ch/services/json/Paris";

                // Affiche une boîte de texte
                Snackbar.make(findViewById(R.id.barChart), getString(R.string.impossible_recupe_localisation), Snackbar.LENGTH_LONG)
                        .setAction(R.string.activer, v -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                        .show();

                // Si une ville a été enregistré dans la mémoire
            } else {
                // Récupérer la dernière localisation enregistrée
                SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                String ville = prefs.getString("ville", null);

                url = "https://www.prevision-meteo.ch/services/json/" + ville;
                Toast.makeText(this, "Récupération de la dernière localisation connue", Toast.LENGTH_SHORT).show();
            }
        }


        // Si une ville a été envoyée de la VilleActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            url = "https://www.prevision-meteo.ch/services/json/" + extras.getString("ville");


        // Bouton récupérer la localisation
        if (recupLocalisation)
            url = "https://www.prevision-meteo.ch/services/json/" + ville;

        RequestQueue queue = com.android.volley.toolbox.Volley.newRequestQueue(this);
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
                        String[] day_long = new String[7];
                        int[] tmax = new int[7];


                        day_long[0] = fcst_day_0.getString("day_long");
                        String tmin = fcst_day_0.getString("tmin");
                        tmax[0] = fcst_day_0.getInt("tmax");

                        // fcst_day_1
                        JSONObject fcst_day_1 = jsonObject.getJSONObject("fcst_day_1");
                        day_long[1] = fcst_day_1.getString("day_long");
                        tmax[1] = fcst_day_1.getInt("tmax");

                        // fcst_day_2
                        JSONObject fcst_day_2 = jsonObject.getJSONObject("fcst_day_2");
                        day_long[2] = fcst_day_2.getString("day_long");
                        tmax[2] = fcst_day_2.getInt("tmax");

                        // fcst_day_3
                        JSONObject fcst_day_3 = jsonObject.getJSONObject("fcst_day_3");
                        day_long[3] = fcst_day_3.getString("day_long");
                        tmax[3] = fcst_day_3.getInt("tmax");

                        // fcst_day_4
                        JSONObject fcst_day_4 = jsonObject.getJSONObject("fcst_day_4");
                        day_long[4] = fcst_day_4.getString("day_long");
                        tmax[4] = fcst_day_4.getInt("tmax");

                        // Afficher les données du jour
                        Picasso.get().load(icone).into(imageViewIcone);
                        textViewVille.setText(ville);
                        textViewJour.setText(day_long[0]);
                        textViewTemperature.setText(getString(R.string.tmp, tmp));
                        textViewCondition.setText(condition);
                        textViewTMin.setText(getString(R.string.TMin, tmin));
                        textViewTMax.setText(getString(R.string.TMax, tmax[0]));
                        textViewHumidite.setText(getString(R.string.humidite2, humidite) + " %");
                        textViewLeverSoleil.setText(leveSoleil);
                        textViewCoucherSoleil.setText(coucheSoleil);
                        textViewVent.setText(getString(R.string.vent2, vent));

                        // Afficher les prévisions dans un graphique
                        BarChart barChart = findViewById(R.id.barChart);
                        ArrayList<BarEntry> temperature = new ArrayList<>();
                        for (int i = 0; i <= 4; i++)
                            temperature.add(new BarEntry(i, tmax[i]));

                        BarDataSet barDataSet = new BarDataSet(temperature, getString(R.string.temperatures));
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(16f);

                        BarData barData = new BarData(barDataSet);
                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.getDescription().setText(getString(R.string.meteo_semaine));
                        barChart.animateY(2000);

                        // Si la ville saisie n'a pas été trouvée
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (!url.equals("https://www.prevision-meteo.ch/services/json/null")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle(R.string.error)
                                    .setMessage(R.string.ville_non_trouvee)
                                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                                        Intent intent = new Intent(getApplicationContext(), VilleActivity.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .show();
                            alertDialog.setCanceledOnTouchOutside(false);
                        }
                    }
                },

                error -> editTextVille.setError(getString(R.string.pas_fonctionne)));

        // Ajouter la requête à la RequestQueue.
        queue.add(stringRequest);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.quitter)
                .setMessage(R.string.voulez_vous_vraiment_quitter)
                .setPositiveButton(R.string.oui, (dialogInterface, i) -> {
                })
                .setNegativeButton(R.string.non, (dialogInterface, i) -> {
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}