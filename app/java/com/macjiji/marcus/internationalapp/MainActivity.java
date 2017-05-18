package com.macjiji.marcus.internationalapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Marcus
 * @version 1.0
 *
 * Activité d'accueil
 *
 */
public class MainActivity extends AppCompatActivity {


    protected TextView title, languageSelected, subtitleShortDate, subtitleLongDate, contentShortDate, contentLongDate;
    protected Spinner selectLanguage;

    protected Configuration configuration;
    protected Locale savedLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeTextViews();
        initializeSpinner();
    }

    /**
     * Méthode d'initialisation des TextView
     */
    private void initializeTextViews(){
        // Etape 1 : On récupère les références via la classe R
        title = (TextView)findViewById(R.id.activity_title);
        languageSelected = (TextView)findViewById(R.id.language_selected);
        subtitleShortDate = (TextView)findViewById(R.id.subtitle_date_short);
        subtitleLongDate = (TextView)findViewById(R.id.subtitle_date_long);
        contentShortDate = (TextView)findViewById(R.id.date_string_short);
        contentLongDate = (TextView)findViewById(R.id.date_string_long);

        // Etape 2 : On place le texte adéquat dans les titres et sous-titres
        title.setText(getResources().getString(R.string.app_name));
        subtitleShortDate.setText(getResources().getString(R.string.date_short_title));
        subtitleLongDate.setText(getResources().getString(R.string.date_long_title));

        // Etape 3 : On récupère la date du jour
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        Date currentDate = calendar.getTime();

        // Etape 4 : On place le texte des dates à partir de la variable Locale
        contentShortDate.setText(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(currentDate));
        contentLongDate.setText(DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(currentDate));

    }

    /**
     * Méthode d'initialisation du Spinner
     */
    private void initializeSpinner(){
        // Etape 1 : On récupère les références via la classe R
        selectLanguage = (Spinner)findViewById(R.id.spinner_country);

        // Etape 2 : On crée un adaptateurs simple contenant les valeurs de notre liste de langages disponibles
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.countries));

        // Etape 3 ; On attache l'adaptateur à notre Spinner
        selectLanguage.setAdapter(arrayAdapter);

        // Etape 4 : on préselecte le Spinner en fonction de la variable Locale sauvegardée
        if(Locale.getDefault() == Locale.FRENCH){
            selectLanguage.setSelection(0, false);
        }
        else if(Locale.getDefault() == Locale.ENGLISH){
            selectLanguage.setSelection(1, false);
        }

        // Etape 5 : On crée le listener sur le Spinner
        selectLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                configuration = getResources().getConfiguration();
                savedLocale = configuration.locale;
                switch (i){
                    case 0 :
                        Locale.setDefault(Locale.FRENCH); // On attribue à la valeur Locale par défaut le français
                        configuration.locale = Locale.getDefault(); // On attribue à la configuration la valeur précédemment créée
                        getResources().updateConfiguration(configuration, null); // On met à jour la configuration
                        startActivity(new Intent(MainActivity.this, MainActivity.class)); // On relance l'activité
                        break;
                    case  1:
                        Locale.setDefault(Locale.ENGLISH); // On attribue à la valeur Locale par défaut l'anglais
                        configuration.locale = Locale.getDefault(); // On attribue à la configuration la valeur précédemment créée
                        getResources().updateConfiguration(configuration, null); // On met à jour la configuration
                        startActivity(new Intent(MainActivity.this, MainActivity.class)); // On relance l'activité
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO NOTHING
            }
        });

    }


}
