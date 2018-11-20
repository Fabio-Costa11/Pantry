package com.example.ipca02.pantry.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipca02.pantry.Adapters.CursorAdapter_Produtos_Adicionados;

import com.example.ipca02.pantry.Database.DBManagerListaProdutosAdicionados;
import com.example.ipca02.pantry.Database.DBManagerProducts;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Lista_Produtos_Adicionados;
import com.example.ipca02.pantry.Models.Produto;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by IPCA02 on 01-06-2017.
 */

public class Lista_Produtos_Adicionado extends AppCompatActivity {

    ListView lista_produtos_adicionados;
    Button btn_concluir_lista;
    Button btn_inserir_produtos;
    ArrayList<Lista_Produtos_Adicionados> lista_produtos_adicionados_array;
    CursorAdapter_Produtos_Adicionados adapter_lista_produtos_adicionados;
    private DatabaseHelper db_h;
    String nome_lista = "";
    int id_lista;
    String estadolista;
    int hour, minute, day, month, year;
    SQLiteDatabase db;

    //SwipeRefreshLayout mySwipeRefreshLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_produtos_comprar);

        Bundle extras = getIntent().getExtras();
        db_h = new DatabaseHelper(this);
        nome_lista = extras.getString("NOME_LISTA");
        id_lista = extras.getInt("ID_LISTA");

        lista_produtos_adicionados = (ListView) findViewById(R.id.lista_produtos_adicionados);
        btn_concluir_lista = (Button) findViewById(R.id.btn_concluir_lista);
        btn_inserir_produtos = (Button) findViewById(R.id.btn_inserir_produtos);
        //mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        lista_produtos_adicionados_array = new ArrayList<>();

        //mySwipeRefreshLayout.setRefreshing(false);

        btn_concluir_lista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // custom dialog
                final Dialog dialog = new Dialog(Lista_Produtos_Adicionado.this);
                dialog.setContentView(R.layout.dialog_confirmar);
                dialog.setTitle("Deseja Concluir a Lista?");

                // set the custom dialog components - text, image and button
                final Button btn_sim = (Button) dialog.findViewById(R.id.btn_sim);
                final Button btn_nao = (Button) dialog.findViewById(R.id.btn_nao);
                // if button is clicked, close the custom dialog
                btn_sim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Calendar c = Calendar.getInstance();
                        minute = c.get(Calendar.MINUTE);
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        day = c.get(Calendar.DAY_OF_WEEK);
                        month = c.get(Calendar.MONTH);
                        year = c.get(Calendar.YEAR);

                        String data_conclusao_year = String.valueOf(year);
                        String data_conclusao_month = String.valueOf(month);
                        String data_conclusao_day = String.valueOf(day);

                        db_h = new DatabaseHelper(Lista_Produtos_Adicionado.this);
                        final SQLiteDatabase db = db_h.getWritableDatabase();
                        String sqlw = "UPDATE " + DatabaseContract.Listas.TB_NAME_LISTAS + " SET " + DatabaseContract.Listas.COL_ESTADO_LISTA + " = '1', " + DatabaseContract.Listas.COL_DATA_CONCLUSAO_YEAR + " = " + data_conclusao_year + "," + DatabaseContract.Listas.COL_DATA_CONCLUSAO_MONTH + " = " + data_conclusao_month + "," + DatabaseContract.Listas.COL_DATA_CONCLUSAO_DAY + " = " + data_conclusao_day + " WHERE " + DatabaseContract.Listas.COL_LISTA_ID + " = " + id_lista + ";";
                        db.execSQL(sqlw);
                        Toast.makeText(Lista_Produtos_Adicionado.this, "Concluido com Sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                        dialog.dismiss();
                    }
                });

                btn_nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        dialog.dismiss();
                    }
                });
                myUpdateOperation();
                dialog.show();
                //Intent intent = new Intent(getBaseContext(), MainActivity.class);
                //startActivity(intent);
                //Perform action on click
            }
        });

        btn_inserir_produtos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProdutosActivity.class);
                intent.putExtra("ID_LISTA", id_lista);
                startActivity(intent);
                // Perform action on click
            }
        });
        myUpdateOperation();
        removerProduto();
        /*mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        myUpdateOperation();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );*/
    }

    public void removerProduto() {
        lista_produtos_adicionados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView_remover = (TextView) view.findViewById(R.id.textView_id_produto_adicionado);

                final String id_remover = textView_remover.getText().toString();

                // custom dialog
                final Dialog dialog = new Dialog(Lista_Produtos_Adicionado.this);
                dialog.setContentView(R.layout.dialog_remover_produto);
                dialog.setTitle("Remover Produto da Lista");

                // set the custom dialog components - text, image and button
                final Button btn_remover_produto = (Button) dialog.findViewById(R.id.btn_remover_produto);
                // if button is clicked, close the custom dialog
                btn_remover_produto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteprodutoadicionado(id_remover);
                        dialog.dismiss();
                        myUpdateOperation();
                    }
                });

                dialog.show();
                return true;
            }

            //ao fazer a compra adiciona o valor e no fim diz o total
        });

    }

    public boolean deleteprodutoadicionado(String id_remover) {
        final SQLiteDatabase db = db_h.getWritableDatabase();
        return db.delete(DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA, DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA + "=" + id_remover, null) > 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myUpdateOperation();
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

        adapter_lista_produtos_adicionados = new CursorAdapter_Produtos_Adicionados(Lista_Produtos_Adicionado.this, todoCursor);
        lista_produtos_adicionados.setAdapter(adapter_lista_produtos_adicionados);
        //lista_produtos_adicionados.setSelector(R.drawable.selector);
        //Toast.makeText(Lista_Produtos_Adicionado.this, nome_lista, Toast.LENGTH_SHORT).show();
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

        if (id == R.id.action_calcular_distancia) {
            Intent intent = new Intent(getBaseContext(), CalcularDistancia.class);
            intent.putExtra("ID_LISTA", id_lista);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}