package com.fdi.ucm.prestobus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.fdi.ucm.prestobus.model.ParadaInfoLinea;
import com.fdi.ucm.prestobus.utilidades.Rest;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que extiende a Activity (a través de la biblioteca AppCompatActivity para compatibilidad) y se encarga de mostrar las pardas a través de la linea y el sentido que se le pasa en el intent
 */
public class ListaParadas extends AppCompatActivity {

    /**
     * Se encarga de mostrar las pardas a través de la linea y el sentido que se le pasa en el intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_paradas);

        // Get the message from the intent
        Intent intent = getIntent();
        final String label = intent.getStringExtra(SelectorSentido.EXTRA_LABEL);
        final String line = intent.getStringExtra(SelectorSentido.EXTRA_LINE);
        final String name = intent.getStringExtra(SelectorSentido.EXTRA_NAME);
        final boolean sentidoNormal = intent.getStringExtra(SelectorSentido.EXTRA_SENTIDO_NORMAL).equals("si");

        getSupportActionBar().setTitle("Sentido " + WordUtils.capitalize(name.toLowerCase()));

        ListView listview = (ListView) findViewById(R.id.listViewParadas);
        final ArrayAdapter<ParadaInfoLinea> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1);

        listview.setAdapter(adapter);

        Rest.getAllStopsFromLine(this, adapter, line, sentidoNormal);

        EditText inputSearch = (EditText) findViewById(R.id.editTextListParadas);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final Activity activity = this;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, ParadaDetailActivity.class);
                intent.putExtra(ParadaDetailFragment.ARG_ITEM_ID, adapter.getItem(i).getNode());
                startActivity(intent);
            }
        });
    }

}
