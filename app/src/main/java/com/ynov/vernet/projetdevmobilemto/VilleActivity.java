package com.ynov.vernet.projetdevmobilemto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VilleActivity extends AppCompatActivity {

    ImageView imageViewIcone;
    TextView textViewTemperature;
    EditText editTextVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ville);

        // Référence
        imageViewIcone = findViewById(R.id.imageViewIcone);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        editTextVille = findViewById(R.id.editTextVille);

        // Ouvrir le clavier
        editTextVille.requestFocus();

        // 1ere lettre en majuscule
        editTextVille.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        // Au clic du bouton
        findViewById(R.id.btnValider).setOnClickListener(v -> {

            // Si la zone est vide
            if (editTextVille.getText().toString().isEmpty()) {
                editTextVille.setError(getString(R.string.zone_txt_ne_peut_pas_etre_vide));

                // Supprimer l'erreur au bout de 3s
                new Handler().postDelayed(() -> editTextVille.setError(null), 3000);

            } else {

                // Démarrer l'activité en envoyant le nom de la ville saisie
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ville", editTextVille.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}