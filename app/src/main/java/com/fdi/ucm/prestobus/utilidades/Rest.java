package com.fdi.ucm.prestobus.utilidades;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdi.ucm.prestobus.NukeSSLCerts;
import com.fdi.ucm.prestobus.R;
import com.fdi.ucm.prestobus.model.Linea;
import com.fdi.ucm.prestobus.model.Llegada;
import com.fdi.ucm.prestobus.model.Parada;
import com.fdi.ucm.prestobus.model.ParadaInfoLinea;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 */

/**
 * Clase que se encarga de consular la API Rest de la EMT
 */
public final class Rest {
    private final static String errorLog = "ErrorLog Rest";

    private final static String ulrBase = "https://openbus.emtmadrid.es:9443/emt-proxy-server/last/";
    private final static String idClient = "idClient";
    private final static String idClientValue = "Sustituir por el id que te da la EMT al regitrarte en http://opendata.emtmadrid.es/Formulario";
    private final static String passKey = "passKey";
    private final static String passKeyValue = "Sustituir por el key que te da la EMT al regitrarte en http://opendata.emtmadrid.es/Formulario";

    /**
     * Consigue todas las lineas de autobues de la EMT, las añade a la lista lineas y al adapter
     * @param activity
     * @param adapter
     * @param lineas
     */
    public static void getAllLines(Activity activity, final ArrayAdapter adapter, final List<Linea> lineas){
        NukeSSLCerts.nuke();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = ulrBase + "bus/GetListLines.php";

        // Request a string response from the provided URL.
        StringRequest jsongRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            //Construimos el objeto Json
                            JsonNode actualObj = mapper.readTree(response);
                            actualObj = actualObj.get("resultValues");

                            //Parseamos el objeto Json a una lista de lineas con ayuda de jackson (https://github.com/FasterXML/jackson-databind/)
                            List<Linea> lineasTemp = mapper.readValue(actualObj.toString(), new TypeReference<List<Linea>>() { });

                            lineas.addAll(lineasTemp);

                            adapter.addAll(lineas);
                            adapter.notifyDataSetChanged();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(errorLog, "Error al cargar los datos");
                        Log.e(errorLog, error.getMessage());
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(idClient, idClientValue);
                params.put(passKey, passKeyValue);
                params.put("SelectDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsongRequest);
    }

    /**
     * Consigue todas las pardas a partir de una linea, a partir del setntido, y las añade al adapter
     * @param activity
     * @param adapter
     * @param linea
     * @param sentidoNormal
     */
    public static void getAllStopsFromLine(Activity activity, final ArrayAdapter adapter, String linea, boolean sentidoNormal){
        final String lineaFinal = linea;
        final boolean sentidoNormalFinal = sentidoNormal;
        NukeSSLCerts.nuke();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = ulrBase + "bus/GetRouteLines.php";

        // Request a string response from the provided URL.
        StringRequest jsongRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            //Construimos el objeto Json
                            JsonNode actualObj = mapper.readTree(response);
                            actualObj = actualObj.get("resultValues");

                            //Parseamos el objeto Json a una lista de lineas con ayuda de jackson (https://github.com/FasterXML/jackson-databind/)
                            List<ParadaInfoLinea> paradas = mapper.readValue(actualObj.toString(), new TypeReference<List<ParadaInfoLinea>>() { });

                            List<ParadaInfoLinea> paradasConSentido = new ArrayList<>();

                            for(ParadaInfoLinea parada: paradas){
                                if (sentidoNormalFinal && (parada.getSecDetail().equals("10") || parada.getSecDetail().equals("19"))) paradasConSentido.add(parada);
                                else if (!sentidoNormalFinal && (parada.getSecDetail().equals("20") || parada.getSecDetail().equals("29"))) paradasConSentido.add(parada);

                            }

                            adapter.addAll(paradasConSentido);
                            adapter.notifyDataSetChanged();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(errorLog, "Error al cargar los datos");
                        Log.e(errorLog, error.getMessage());
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(idClient, idClientValue);
                params.put(passKey, passKeyValue);
                params.put("SelectDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                params.put("Lines", lineaFinal);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsongRequest);
    }

    /**
     * Consigue todas las llegadas a partir de una parada, y las guarda en la lista llegadas, y las añade al layout
     * @param activity
     * @param layout
     * @param stop
     * @param llegadas
     */
    public static void getAllArrivalsFromStop(final Context activity, final LinearLayout layout, String stop, final List<Llegada> llegadas){
        BusApplication.addTextViewToLayout(layout, activity.getString(R.string.loading), activity);

        final String stopFinal = stop;
        NukeSSLCerts.nuke();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = ulrBase + "geo/GetArriveStop.php";

        // Request a string response from the provided URL.
        StringRequest jsongRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        layout.removeAllViews();

                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            //Construimos el objeto Json
                            JsonNode actualObj = mapper.readTree(response);
                            actualObj = actualObj.get("arrives");
                            if (actualObj != null) { //Evitamos problemas si no hay buses en la parada
                                //Parseamos el objeto Json a una lista de lineas con ayuda de jackson (https://github.com/FasterXML/jackson-databind/)
                                List<Llegada> llegadasTemp = new ArrayList<>();
                                try {
                                    List<Llegada> aux = mapper.readValue(actualObj.toString(), new TypeReference<List<Llegada>>() {});
                                    llegadasTemp.addAll(aux);
                                }
                                catch (com.fasterxml.jackson.databind.JsonMappingException e){
                                    //Solo hay una parada, y no devuelve una lista con varios objetos, devuelve solo el objeto
                                    Llegada lleg = mapper.readValue(actualObj.toString(), new TypeReference<Llegada>() {});
                                    llegadasTemp.add(lleg);
                                }

                                llegadas.addAll(llegadasTemp);

                                int limite = 4;
                                if (llegadasTemp.size() < limite) limite = llegadasTemp.size();

                                for (int j = 0; j<limite; j++){
                                    BusApplication.addTextViewToLayout(layout, llegadasTemp.get(j).toString(), activity);
                                }
                            }

                            else{
                                BusApplication.addTextViewToLayout(layout, activity.getString(R.string.empty), activity);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(errorLog, "Error al cargar los datos");
                        Log.e(errorLog, error.getMessage());
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(idClient, idClientValue);
                params.put(passKey, passKeyValue);
                params.put("idStop", stopFinal);
                params.put("cultureInfo", "ES");
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsongRequest);
    }

    /**
     * Consigue todas las llegadas a partir de una parada y actualiza la vista web
     * @param activity
     * @param webView
     * @param stop
     * @param llegadas
     */
    private static void getAllArrivalsFromStopAndUpdateWebView(final Context activity, final WebView webView, String stop, final List<Llegada> llegadas){
        final String stopFinal = stop;
        NukeSSLCerts.nuke();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = ulrBase + "geo/GetArriveStop.php";

        // Request a string response from the provided URL.
        StringRequest jsongRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            //Construimos el objeto Json
                            JsonNode actualObj = mapper.readTree(response);
                            actualObj = actualObj.get("arrives");
                            if (actualObj != null) { //Evitamos problemas si no hay buses en la parada
                                List<Llegada> llegadasTemp = new ArrayList<>();
                                try {
                                    List<Llegada> aux = mapper.readValue(actualObj.toString(), new TypeReference<List<Llegada>>() {});
                                    llegadasTemp.addAll(aux);
                                }
                                catch (com.fasterxml.jackson.databind.JsonMappingException e){
                                    //Solo hay una parada, y no devuelve una lista con varios objetos, devuelve solo el objeto
                                    Llegada lleg = mapper.readValue(actualObj.toString(), new TypeReference<Llegada>() {});
                                    llegadasTemp.add(lleg);
                                }

                                llegadas.addAll(llegadasTemp);
                            }

                            webView.loadUrl("file:///android_asset/www/index.html");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(errorLog, "Error al cargar los datos");
                        Log.e(errorLog, error.getMessage());
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(idClient, idClientValue);
                params.put(passKey, passKeyValue);
                params.put("idStop", stopFinal);
                params.put("cultureInfo", "ES");
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsongRequest);
    }

    /**
     * Consigue información de una parda y actualiza la lista web, cuando ya tiene también todas las llegadas de autobues
     * @param activity
     * @param stop
     * @param webAppInterface
     * @param webView
     */
    public static void getStop(final Context activity, String stop, final WebAppInterface webAppInterface, final WebView webView){
        final String stopFinal = stop;
        NukeSSLCerts.nuke();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        String url = ulrBase + "bus/GetNodesLines.php";

        // Request a string response from the provided URL.
        StringRequest jsongRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            //Construimos el objeto Json
                            JsonNode actualObj = mapper.readTree(response);
                            actualObj = actualObj.get("resultValues");
                            //Parseamos el objeto Json a una lista de lineas con ayuda de jackson (https://github.com/FasterXML/jackson-databind/)
                            Parada paradaTemp = mapper.readValue(actualObj.toString(), new TypeReference<Parada>() {});

                            paradaTemp.setListaLLegadas(new ArrayList<Llegada>());
                            paradaTemp.formatLines();

                            webAppInterface.setParada(paradaTemp);

                            Rest.getAllArrivalsFromStopAndUpdateWebView(activity, webView, stopFinal, paradaTemp.getListaLLegadas());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(errorLog, "Error al cargar los datos");
                        Log.e(errorLog, error.getMessage());
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(idClient, idClientValue);
                params.put(passKey, passKeyValue);
                params.put("Nodes", stopFinal);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsongRequest);
    }
}
