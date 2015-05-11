package com.fdi.ucm.prestobus;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * Clase que extiende a Activity y se encarga de mostrar la información de las paradas favoritas y si estamos en una tablet también los detalles de la parada
 */

/**
 * An activity representing a list of Paradas. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ParadaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ParadaListFragment} and the item details
 * (if present) is a {@link ParadaDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ParadaListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ParadaListActivity extends AppCompatActivity
implements ParadaListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private ParadaListFragment paradaListFragment;

    /**
     * Se encarga de crear las vistas (distinguiendo si estamos en una tablet) y de cargar los datos iniciales de las paradas favoritas
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parada_list);

        if (findViewById(R.id.parada_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if (!mTwoPane){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.parada_list_wrap_single_mode, new ParadaListFragment())
                    .commit();
        }
        else{
            paradaListFragment = new ParadaListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.parada_list_container, paradaListFragment)
                    .commit();
        }

        //noinspection ResourceType
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        ((BusApplication) getApplication()).init(this);
    }


    /**
     * Callback method from {@link ParadaListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ParadaDetailFragment.ARG_ITEM_ID, id);
            ParadaDetailFragment fragment = new ParadaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.parada_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ParadaDetailActivity.class);
            detailIntent.putExtra(ParadaDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
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
            case R.id.action_search:
                Intent listIntent = new Intent(this, ListaLineas.class);
                startActivity(listIntent);
                return true;
            case R.id.action_refresh:
                // Reload current fragment
                ((BusApplication) getApplication()).refresh();
                if (!mTwoPane) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.parada_list_wrap_single_mode, new ParadaListFragment())
                            .commit();
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.parada_detail_container, new Fragment())
                            .commit();


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.parada_list_container, new ParadaListFragment())
                            .commit();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
