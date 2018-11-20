package com.example.ipca02.pantry.Activitys;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ipca02.pantry.Adapters.CursorAdapter_Produtos_Adicionados;
import com.example.ipca02.pantry.Adapters.CursorAdapter_Produtos_Adicionados_Historico;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Lista;
import com.example.ipca02.pantry.Models.Lista_Produtos_Adicionados;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;

/**
 * Created by IPCA02 on 22-06-2017.
 */

public class HistoricoActivity extends AppCompatActivity {

    ListView lista_produtos_adicionados;
    ArrayList<Lista_Produtos_Adicionados> lista_produtos_adicionados_array;
    CursorAdapter_Produtos_Adicionados_Historico adapter_lista_produtos_adicionados_historico;
    private DatabaseHelper db_h;
    int id_lista = 0;
    String estadolista;

    //SwipeRefreshLayout mySwipeRefreshLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_activity);

        Bundle extras = getIntent().getExtras();

        id_lista = extras.getInt("ID_LISTA");

        lista_produtos_adicionados = (ListView) findViewById(R.id.lista_produtos_adicionados);

        lista_produtos_adicionados_array = new ArrayList<>();
        myUpdateOperation();
    }

    public void getestado() {
        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();
        String sqlw = "SELECT * FROM " + DatabaseContract.Listas.TB_NAME_LISTAS + " WHERE " + DatabaseContract.Listas.COL_ESTADO_LISTA + " = " + "1" + ";";
        Cursor cursor = db.rawQuery(sqlw, null);
        cursor.moveToFirst();
        estadolista = cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_ESTADO_LISTA));
    }

    public void myUpdateOperation() {
        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();

        getestado();

        String sql = "SELECT * FROM " + DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA + " WHERE " + DatabaseContract.ProdutosLista.COL_LISTA_ID + " = " + id_lista + " AND " + estadolista + " = 1 " + ";";
        Cursor todoCursor = db.rawQuery(sql, null);

        adapter_lista_produtos_adicionados_historico = new CursorAdapter_Produtos_Adicionados_Historico(HistoricoActivity.this, todoCursor);
        lista_produtos_adicionados.setAdapter(adapter_lista_produtos_adicionados_historico);
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

        //noinspection SimplifiableIfStatement
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
}