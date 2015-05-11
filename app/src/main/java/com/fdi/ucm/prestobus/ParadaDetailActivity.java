package com.fdi.ucm.prestobus;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fdi.ucm.prestobus.utilidades.BusApplication;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que extiende a Activity (a través de la biblioteca AppCompatActivity para compatibilidad) y se encarga de mostrar la información detallada de una parada
 */

/**
 * An activity representing a single Parada detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ParadaListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ParadaDetailFragment}.
 */
public class ParadaDetailActivity extends AppCompatActivity {

    /**
     * Usado para cuando recarges, saber que id tienes
     */
    private String mItem;

    /**
     * Creador de la vista de los detalles. Se enarga de crear el fragmento
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parada_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ParadaDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ParadaDetailFragment.ARG_ITEM_ID));
            mItem = getIntent().getStringExtra(ParadaDetailFragment.ARG_ITEM_ID);
            ParadaDetailFragment fragment = new ParadaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.parada_detail_container, fragment)
                    .commit();
        }
    }

    /**
     * Se encarga de rellenar las opciones del menú superior de la actividad
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Se encarga de actuar cuando se pulsa alguna de las opciones del menú superior de la actividad
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUpTo(new Intent(this, ParadaListActivity.class));
                return true;
            case R.id.action_search:
                Intent listIntent = new Intent(this, ListaLineas.class);
                startActivity(listIntent);
                return true;
            case R.id.action_refresh:
                // Reload current fragment
                ((BusApplication) getApplication()).refresh();

                Bundle arguments = new Bundle();
                arguments.putString(ParadaDetailFragment.ARG_ITEM_ID, mItem);
                ParadaDetailFragment fragment = new ParadaDetailFragment();
                fragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.parada_detail_container, fragment)
                        .commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
