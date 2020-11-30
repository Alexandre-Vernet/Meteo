package com.ynov.vernet.projetdevmobilemto;

import android.content.Intent;
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

    EditText editTextVille;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ville);

        // Référence
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

                // Démarrer l'activité en envoyant nom
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                finish();
            }
        });
    }
}