package com.ynov.vernet.projetdevmobilemto;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class VilleActivity extends AppCompatActivity {

    ImageView imageViewIcone;
    TextView textViewTemperature;
    EditText editTextVille;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ville);

        // Référence
        imageViewIcone = findViewById(R.id.imageViewIcone);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        editTextVille = findViewById(R.id.editTextVille);

        // Ouvrir le clavier
//        editTextVille.requestFocus();

        // 1ere lettre en majuscule
        editTextVille.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        // Au clic du bouton
        findViewById(R.id.btnValider).setOnClickListener(v -> {

            // Si la zone est vide
            if (editTextVille.getText().toString().isEmpty()) {
                editTextVille.setError("La zone de texte ne peut pas être vide");

                // Supprimer l'erreur au bout de 2s
                new Handler().postDelayed(() -> editTextVille.setError(null), 2000);

            } else {
                // Récupérer la ville saisie
                url = "https://www.prevision-meteo.ch/services/json/" + editTextVille.getText().toString();

                // Instancier RequestQueue
                RequestQueue queue = Volley.newRequestQueue(this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        response -> {
                            try {
                                // Récupérer les données
                                JSONObject jsonObject = new JSONObject(response);

                                // current_condition
                                JSONObject current_condition = jsonObject.getJSONObject("current_condition");
                                String icone = current_condition.getString("icon_big");
                                String tmp = current_condition.getString("tmp");

                                // Afficher les données du jour
                                Picasso.get().load(icone).into(imageViewIcone);
                                textViewTemperature.setText("Température : " + tmp);

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

                        error -> editTextVille.setError("Désolé, ça n'a pas fonctionné"));

                // Ajouter la requête à la RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}