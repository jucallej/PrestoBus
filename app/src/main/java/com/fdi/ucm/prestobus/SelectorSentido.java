package com.fdi.ucm.prestobus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fdi.ucm.prestobus.utilidades.BusApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que extiende a Activity (a través de la biblioteca AppCompatActivity para compatibilidad) y se mostrar los dos sentidos de una linea, para según lo que pulsemos
 * mostrarnos las paradas de un sentido u otro
 */
public class SelectorSentido extends AppCompatActivity {
    public final static String EXTRA_LINE = "com.fdi.ucm.prestobus.LINE.SELECTOR.SENTIDO";
    public final static String EXTRA_LABEL = "com.fdi.ucm.prestobus.LABEL.SELECTOR.SENTIDO";
    public final static String EXTRA_NAME = "com.fdi.ucm.prestobus.NAME";
    public final static String EXTRA_SENTIDO_NORMAL = "com.fdi.ucm.prestobus.SENTIDO";

    /**
     * Se encarga de crear la vista para seleccionar el sentido
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_sentido);

        // Get the message from the intent
        Intent intent = getIntent();
        final String label = intent.getStringExtra(ListaLineas.EXTRA_LABEL);
        final String line = intent.getStringExtra(ListaLineas.EXTRA_LINE);
        final String nameA = intent.getStringExtra(ListaLineas.EXTRA_NAMEA);
        final String nameB = intent.getStringExtra(ListaLineas.EXTRA_NAMEB);

        getSupportActionBar().setTitle("Linea " + label);


        List <String> strList = new ArrayList<>();

        strList.add(BusApplication.NEXT_LINE + "-> Sentido" + BusApplication.NEXT_LINE + nameA + BusApplication.NEXT_LINE);
        strList.add(BusApplication.NEXT_LINE + "<- Sentido" + BusApplication.NEXT_LINE + nameB + BusApplication.NEXT_LINE);


        ListView listview = (ListView) findViewById(R.id.listViewSelector);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1);

        adapter.addAll(strList);

        listview.setAdapter(adapter);


        final Activity activity = this;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, ListaParadas.class);
                intent.putExtra(EXTRA_LINE, line);
                intent.putExtra(EXTRA_LABEL, label);

                if (i == 0) { //Sentido 1
                    intent.putExtra(EXTRA_NAME, nameA);
                    intent.putExtra(EXTRA_SENTIDO_NORMAL, "si");

                }
                else{ //Sentido 2
                    intent.putExtra(EXTRA_NAME, nameB);
                    intent.putExtra(EXTRA_SENTIDO_NORMAL, "no");

                }

                startActivity(intent);
            }
        });
    }
}
