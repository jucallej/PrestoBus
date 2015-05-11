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

import com.fdi.ucm.prestobus.model.Linea;
import com.fdi.ucm.prestobus.utilidades.BusApplication;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que extiende a Activity (a través de la biblioteca AppCompatActivity para compatibilidad) y se encarga de mostrar la lista con todas las lineas de autobues
 */
public class ListaLineas extends AppCompatActivity {
    public final static String EXTRA_LINE = "com.fdi.ucm.prestobus.LINE";
    public final static String EXTRA_LABEL = "com.fdi.ucm.prestobus.LABEL";
    public final static String EXTRA_NAMEA = "com.fdi.ucm.prestobus.NAMEA";
    public final static String EXTRA_NAMEB = "com.fdi.ucm.prestobus.NAMEB";

    /**
     * Se encarga de mostrar la lista con todas las lineas de autobues
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lineas);


        ListView listview = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<Linea> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1);

        listview.setAdapter(adapter);

        ((BusApplication) getApplication()).getAllLines(this, adapter);

        EditText inputSearch = (EditText) findViewById(R.id.editText);
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
                Intent intent = new Intent(activity, SelectorSentido.class);
                intent.putExtra(EXTRA_LINE, adapter.getItem(i).getLine());
                intent.putExtra(EXTRA_LABEL, adapter.getItem(i).getLabel());
                intent.putExtra(EXTRA_NAMEA, adapter.getItem(i).getNameA());
                intent.putExtra(EXTRA_NAMEB, adapter.getItem(i).getNameB());
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Lista de lineas");
    }
}
