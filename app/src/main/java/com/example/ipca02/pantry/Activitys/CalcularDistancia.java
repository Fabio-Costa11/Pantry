package com.example.ipca02.pantry.Activitys;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipca02.pantry.Adapters.CursorAdapter_Produtos_Adicionados;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by IPCA02 on 27-06-2017.
 */

public class CalcularDistancia extends BaseDemoActivity implements GoogleMap.OnMarkerDragListener {
    private TextView mTextView, mTextView_deslocacao, mTextView_preco, mTextView_id_supermercado;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Polyline mPolyline;
    private PolylineOptions mPolylineoptions;
    double preco_metro = 0.00040; //40centimos por kilometro
    double preco_final;
    ListView lista_produtos_final;
    CursorAdapter_Produtos_Adicionados adapter_lista_produtos_adicionados;
    private DatabaseHelper db_h;
    String nome_lista = "";
    int id_lista;
    String estadolista;
    SQLiteDatabase db;
    float total;

    String id_supermercado, coordenadas;
    LatLng local1 = new LatLng(41.539253, -8.398266);
    LatLng local2 = new LatLng(41.541429, -8.400219);
    LatLng local3 = new LatLng(41.579462, -8.428642);

    @Override
    protected int getLayoutId() {
        Bundle extras = getIntent().getExtras();
        nome_lista = extras.getString("NOME_LISTA");
        id_lista = extras.getInt("ID_LISTA");
        return R.layout.distance_demo;
    }

    @Override
    protected void startDemo() {
        mTextView_deslocacao = (TextView) findViewById(R.id.textview_deslocacao);
        mTextView = (TextView) findViewById(R.id.textView_distance);
        mTextView_id_supermercado = (TextView) findViewById(R.id.textview_id_supermercado);
        mTextView_preco = (TextView) findViewById(R.id.textview_preco_final);
        lista_produtos_final = (ListView) findViewById(R.id.lista_produtos_final);
        mPolylineoptions = new PolylineOptions();


        db_h = new DatabaseHelper(this);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(39.468034, -8.206434)).zoom(5.5f).build();
        getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(local1, 15));
        getMap().setOnMarkerDragListener(this);
        mMarkerA = getMap().addMarker(new MarkerOptions().position(local1).draggable(false));
        mMarkerB = getMap().addMarker(new MarkerOptions().position(local2).draggable(false));
        mMarkerC = getMap().addMarker(new MarkerOptions().position(local3).draggable(false));
        mPolyline = getMap().addPolyline(new PolylineOptions().geodesic(true));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(local1);
        builder.include(local2);
        LatLngBounds bounds = builder.build();
        //getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));

        //PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);

        //LatLng point = new LatLng(41.541997, -8.421065);
        //LatLng point2 = new LatLng(41.541001, -8.399071);
        //options.add(point, point2);

        mPolylineoptions.add(local1, local2, local3);
        getMap().addPolyline(mPolylineoptions);

        showDistance();

        //Toast.makeText(this, "Preço Final: " + preco_final, Toast.LENGTH_LONG).show();
        mTextView_deslocacao.setText("Preço deslocação: " + String.format("%4.2f%s", preco_final, "€"));

        myUpdateOperation();
        getSoma();

        float preco = 0;
        preco += total;
        preco += preco_final;

        mTextView_preco.setText("Despesa Final: " + String.format("%4.2f%s", preco, "€"));
        //Toast.makeText(this, "Drag the markers!" + preco_final, Toast.LENGTH_LONG).show();

        calculaDistancia(41.539253, -8.398266, 41.541429, -8.400219, 41.579462, -8.428642);
    }

    private double calculaDistancia(double lat1, double lng1, double lat2, double lng2, double lat3, double lng3) {
        //double earthRadius = 3958.75;//miles
        double earthRadius = 6371;//kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double dLat1 = Math.toRadians(lat3 - lat2);
        double dLng1 = Math.toRadians(lng3 - lng2);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double sindLat1 = Math.sin(dLat1 / 2);
        double sindLng1 = Math.sin(dLng1 / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2));

        double a1 = Math.pow(sindLat1, 2) + Math.pow(sindLng1, 2)
                * Math.cos(Math.toRadians(lat2))
                * Math.cos(Math.toRadians(lat3));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double c1 = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));

        double dist = earthRadius * c;

        double dist1 = earthRadius * c1;
        double distancia = dist1 * 1000;
        double aa = dist * 1000;
        return dist * 1000; //em metros
    }

    @Override
    protected void onResume() {
        super.onResume();
        //myUpdateOperation();
    }

    public void getestado() {
        db_h = new DatabaseHelper(this);
        db = db_h.getWritableDatabase();
        String sqlw = "SELECT * FROM " + DatabaseContract.Listas.TB_NAME_LISTAS + " WHERE " + DatabaseContract.Listas.COL_ESTADO_LISTA + " = " + "0" + ";";
        Cursor cursor = db.rawQuery(sqlw, null);
        cursor.moveToFirst();
        estadolista = cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_ESTADO_LISTA));
    }

    public void myUpdateOperation() {
        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();

        getestado();

        String sql = "SELECT * FROM " + DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA + " WHERE " + DatabaseContract.ProdutosLista.COL_LISTA_ID + " = " + id_lista + " AND " + estadolista + " = 0 " + ";";
        Cursor todoCursor = db.rawQuery(sql, null);

        adapter_lista_produtos_adicionados = new CursorAdapter_Produtos_Adicionados(CalcularDistancia.this, todoCursor);
        lista_produtos_final.setAdapter(adapter_lista_produtos_adicionados);
        //lista_produtos_adicionados.setSelector(R.drawable.selector);
        //Toast.makeText(Lista_Produtos_Adicionado.this, nome_lista, Toast.LENGTH_SHORT).show();
    }

    public void getSoma() {
        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();

        getestado();

        String sql = "SELECT SUM(Preco * Quantidade) FROM " + DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA + " WHERE " + DatabaseContract.ProdutosLista.COL_LISTA_ID + " = " + id_lista + " AND " + estadolista + " = 0 " + ";";
        Cursor todoCursor = db.rawQuery(sql, null);

        todoCursor.moveToFirst();
        total = todoCursor.getFloat(0);
        Log.d("total", String.valueOf(total));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_produtos_adicionados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //no inspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_refresh_produtos_adicionados) {
            //mySwipeRefreshLayout.setRefreshing(true);
            myUpdateOperation();
            //mySwipeRefreshLayout.setRefreshing(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDistance() {

        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();

        getestado();

        String sql = "SELECT DISTINCT (" + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + ")" + " FROM " + DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA + " WHERE " + DatabaseContract.ProdutosLista.COL_LISTA_ID + " = " + id_lista + " AND " + estadolista + " = 0 " + ";";
        Cursor todoCursor = db.rawQuery(sql, null);

        if (todoCursor.getCount() > 0) {

            todoCursor.moveToFirst();
            do {
                id_supermercado = todoCursor.getString(todoCursor.getColumnIndex(DatabaseContract.Supermercado.COL_SUPERMERCADO_ID));

                //Toast.makeText(this, "Supermercado: " + id_supermercado, Toast.LENGTH_LONG).show();
            } while (todoCursor.moveToNext());
        }
        mTextView_id_supermercado.setText(id_supermercado);


        String sql2 = "SELECT " + DatabaseContract.Supermercado.COL_COORDENADAS + " FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " WHERE " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " = " + id_supermercado + ";";
        Cursor todoCursor2 = db.rawQuery(sql2, null);

        if (todoCursor2.getCount() > 0) {

            todoCursor2.moveToFirst();
            do {
                coordenadas = todoCursor2.getString(todoCursor2.getColumnIndex(DatabaseContract.Supermercado.COL_COORDENADAS));

                //Toast.makeText(this, "Coordenadas: " + coordenadas, Toast.LENGTH_LONG).show();
            } while (todoCursor2.moveToNext());
        }

        //mTextView_id_supermercado.setText(id_supermercado);
        /*ArrayList<Integer> listaSuper = new ArrayList<>();

        do{
            listaSuper.add(todoCursor.getInt(todoCursor.getColumnIndex(DatabaseContract.ProdutosLista.COL_SUPERMERCADO_ID)));

        }while (todoCursor.moveToNext());

        for( int i = 0; i < listaSuper.size(); i++){
            if(listaSuper.get(i).equals(listaSuper.get(i+1))){
            }
        }*/

        double distance = SphericalUtil.computeDistanceBetween(mMarkerA.getPosition(), mMarkerB.getPosition());
        String distancia = "Distância " + formatNumber(distance);

        if (distance > 1000) {
            double preco_final1 = preco_metro * distance;
            preco_final = formatpreco(preco_final1);
        } else {
            preco_final = 1;
        }
        mTextView.setText(distancia);
    }

    private void updatePolyline() {
        mPolyline.setPoints(Arrays.asList(mMarkerA.getPosition(), mMarkerB.getPosition()));
    }

    private double formatpreco(double preco) {
        String unit = "€";
        return (preco);
    }

    private String formatNumber(double distance) {
        String unit = "m";
        if (distance < 1) {
            distance *= 1000;
            unit = "mm";
        } else if (distance > 1000) {
            distance /= 1000;
            unit = "km";
        }

        return String.format("%4.2f%s", distance, unit);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        showDistance();
        updatePolyline();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        showDistance();
        updatePolyline();
    }

    public String getLatitude() {
        return null;
    }
}