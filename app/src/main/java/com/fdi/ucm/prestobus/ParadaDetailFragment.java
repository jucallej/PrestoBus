package com.fdi.ucm.prestobus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.fdi.ucm.prestobus.model.Parada;
import com.fdi.ucm.prestobus.utilidades.BusApplication;
import com.fdi.ucm.prestobus.utilidades.Rest;
import com.fdi.ucm.prestobus.utilidades.WebAppInterface;


/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que extiende a Fragment y se encarga de mostrar la información detallada de una parada
 */

/**
 * A fragment representing a single Parada detail screen.
 * This fragment is either contained in a {@link ParadaListActivity}
 * in two-pane mode (on tablets) or a {@link ParadaDetailActivity}
 * on handsets.
 */
public class ParadaDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private String mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParadaDetailFragment() {
    }

    /**
     * Consigue los argumentos que se le pasa al fragmento
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = getArguments().getString(ARG_ITEM_ID);
        }

        NukeSSLCerts.nuke();
    }

    /**
     * Crea la vista (webview) con los detalles de la parada
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parada_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            Parada parada = ((BusApplication) getActivity().getApplication()).getParada(mItem);
            if (parada != null && parada.getListaLLegadas() != null && parada.getListaLLegadas().size() > 0) {
            //Si no tiene autobuses que estan llegando, por si acaso nos aseguramos y lo cargamos nuevamente
                WebView webView = ((WebView) rootView.findViewById(R.id.webView));
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/www/index.html");
                webView.setBackgroundColor(Color.TRANSPARENT);
                webView.addJavascriptInterface(new WebAppInterface(this.getActivity(), parada), "Android");
            }

            else{ //Hay que cargar los datos antes
                WebView webView = ((WebView) rootView.findViewById(R.id.webView));
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/www/cargando.html");
                webView.setBackgroundColor(Color.TRANSPARENT);

                WebAppInterface webAppInterface = new WebAppInterface(this.getActivity());

                Rest.getStop(this.getActivity(), mItem, webAppInterface, webView);

                webView.addJavascriptInterface(webAppInterface, "Android");
            }
        }

        return rootView;
    }
}
