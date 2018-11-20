package com.example.ipca02.pantry.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipca02.pantry.Database.DBManagerProducts;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;

import com.example.ipca02.pantry.Models.Produto;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IPCA02 on 07-06-2017.
 */

public class Novo_Produto extends Activity implements View.OnClickListener {

    Button btn_confirmar_novo_produto, btn_nova_categoria;
    EditText editText_novo_produto, editText_preco;
    DBManagerProducts dbManagerproducts;
    Spinner spinner_categorias, spinner_supermercado;
    DatabaseHelper db_h = new DatabaseHelper(this);
    SQLiteDatabase db;
    String text_categoria = "";
    String idCategoria = "0";
    String idSupermercado = "0";
    String text_supermercado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_novo_produto_layout);

        btn_confirmar_novo_produto = (Button) findViewById(R.id.btn_confirmar_novoproduto);
        editText_novo_produto = (EditText) findViewById(R.id.editText_novoproduto);
        editText_preco = (EditText) findViewById(R.id.editText_preco_produto);
        spinner_categorias = (Spinner) findViewById(R.id.spinner_categoria);
        spinner_supermercado = (Spinner) findViewById(R.id.spinner_supermercado);

        btn_nova_categoria = (Button) findViewById(R.id.btn_nova_categoria);

        dbManagerproducts = new DBManagerProducts(this);
        dbManagerproducts.open();
        btn_confirmar_novo_produto.setOnClickListener(this);

        btn_nova_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Nova_Categoria.class);
                startActivity(intent);
            }
        });

        spinner_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {

                //selectedCategorias = (Categorias) spinner_categorias.getSelectedItem();

                text_categoria = parent.getItemAtPosition(pos).toString();

                //int s = selectedCategorias.getCategorias_id();
                //Toast.makeText(Novo_Produto.this, "You selected: " + text_categoria, Toast.LENGTH_LONG).show();
                categoriasIDGET();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner_supermercado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {

                text_supermercado = parent.getItemAtPosition(pos).toString();
                //Toast.makeText(Novo_Produto.this, "You selected: " + text_supermercado, Toast.LENGTH_LONG).show();
                supermercadosIDGET();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        criarspinnerCategorias();
        criarspinnerSupermercados();
    }

    public void categoriasIDGET() {
        // Select All Query
        String selectQuery = "SELECT " + DatabaseContract.Categorias.COL_CATEGORIA_ID + " FROM " + DatabaseContract.Categorias.TB_NAME_CATEGORIAS + " WHERE " + "'" + text_categoria + "'" + " = " + DatabaseContract.Categorias.COL_CATEGORIA_NOME + ";";

        db = db_h.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                idCategoria = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //Toast.makeText(Novo_Produto.this, "You selected: " + idCategoria, Toast.LENGTH_LONG).show();
    }

    public void supermercadosIDGET() {
        // Select All Query
        String selectQuery = "SELECT " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " WHERE " + "'" + text_supermercado + "'" + " = " + DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME + ";";

        db = db_h.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                idSupermercado = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //Toast.makeText(Novo_Produto.this, "You selected: " + idCategoria, Toast.LENGTH_LONG).show();
    }

    public void criarspinnerSupermercados() {
        List<String> supermercados = getAllSupermercados();

        ArrayAdapter<String> dataAdapter_supermercados = new ArrayAdapter<String>(this,
                R.layout.spinner_checked, supermercados);

        dataAdapter_supermercados.setDropDownViewResource(R.layout.spinner_checked);
        spinner_supermercado.setAdapter(dataAdapter_supermercados);

        //int posicao = spinner_categorias.getSelectedItemPosition();
        //String itemSelecionado = supermercados.get(posicao);
        //Toast.makeText(Novo_Produto.this, "You selected: " + itemSelecionado,Toast.LENGTH_LONG).show();
    }

    public void criarspinnerCategorias() {

        List<String> lables = getAllCategorias();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_checked, lables);

        dataAdapter.setDropDownViewResource(R.layout.spinner_checked);
        spinner_categorias.setAdapter(dataAdapter);

        //int posicao = spinner_categorias.getSelectedItemPosition();
        //String itemSelecionado = lables.get(posicao);

        //Toast.makeText(Novo_Produto.this, "You selected: " + itemSelecionado, Toast.LENGTH_LONG).show();

    }

    public List<String> getAllCategorias() {
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseContract.Categorias.TB_NAME_CATEGORIAS + ";";

        db = db_h.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return categorias;
    }

    public List<String> getAllSupermercados() {
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + ";";

        db = db_h.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return categorias;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmar_novoproduto:

                String name_product = editText_novo_produto.getText().toString();
                int categoria_ID = 0;
                int supermercado_ID = 0;

                categoria_ID = Integer.parseInt(idCategoria);
                supermercado_ID = Integer.parseInt(idSupermercado);

                String preco = editText_preco.getText().toString();

                Produto produto = new Produto(name_product, categoria_ID, preco, supermercado_ID);

                dbManagerproducts.insert(produto);

                Intent main = new Intent(Novo_Produto.this, Ver_Produtos.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}