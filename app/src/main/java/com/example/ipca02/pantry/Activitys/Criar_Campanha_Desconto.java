package com.example.ipca02.pantry.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ipca02.pantry.Database.DBManagerCampanhaDesconto;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.CampanhaDescontos;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IPCA02 on 27-06-2017.
 */

public class Criar_Campanha_Desconto extends AppCompatActivity implements View.OnClickListener {
    private Button btn_confirmar_campanha_desconto;
    private EditText editText_descricao, editText_desconto;
    private Spinner spinner_supermercado_desconto, spinner_produtos;
    private DBManagerCampanhaDesconto dbManagerCampanhaDesconto;
    String text_supermercado_desconto = "";
    String text_produto_id = " ";
    DatabaseHelper db_h = new DatabaseHelper(this);
    SQLiteDatabase db;
    String idSupermercado = "0";
    String idProduto = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campanha_desconto);

        btn_confirmar_campanha_desconto = (Button) findViewById(R.id.btn_confirmar_campanha_desconto);
        editText_descricao = (EditText) findViewById(R.id.editText_descicao_desconto);
        editText_desconto = (EditText) findViewById(R.id.editText_desconto);
        spinner_supermercado_desconto = (Spinner) findViewById(R.id.spinner_supermercado_desconto);
        spinner_produtos = (Spinner) findViewById(R.id.spinner_produtos);

        dbManagerCampanhaDesconto = new DBManagerCampanhaDesconto(this);
        dbManagerCampanhaDesconto.open();
        btn_confirmar_campanha_desconto.setOnClickListener(this);

        spinner_supermercado_desconto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {

                text_supermercado_desconto = parent.getItemAtPosition(pos).toString();
                //Toast.makeText(Novo_Produto.this, "You selected: " + text_supermercado, Toast.LENGTH_LONG).show();
                idSupermercado = supermercadosIDGET();
                criarspinnerProdutos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner_produtos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {

                text_produto_id = parent.getItemAtPosition(pos).toString();
                idProduto = produtoIDGET();
                //Toast.makeText(Novo_Produto.this, "You selected: " + text_supermercado, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        criarspinnerSupermercados();
        //criarspinnerProdutos();
    }

    public void onBackPressed() {
        this.finish();
    }

    public List<String> getAllProdutos() {
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " WHERE " + DatabaseContract.Produtos.COL_SUPERMERCADO_ID + " = " + idSupermercado + ";";

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

    public void criarspinnerSupermercados() {
        List<String> supermercados = getAllSupermercados();

        ArrayAdapter<String> dataAdapter_supermercados = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, supermercados);

        dataAdapter_supermercados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_supermercado_desconto.setAdapter(dataAdapter_supermercados);

        //String itemSelecionado = supermercados.get(posicao);
        //Toast.makeText(Novo_Produto.this, "You selected: " + itemSelecionado,Toast.LENGTH_LONG).show();
    }

    public void criarspinnerProdutos() {
        List<String> produtos = getAllProdutos();

        ArrayAdapter<String> dataAdapter_supermercados = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, produtos);

        dataAdapter_supermercados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_produtos.setAdapter(dataAdapter_supermercados);

        //String itemSelecionado = supermercados.get(posicao);
        //Toast.makeText(Novo_Produto.this, "You selected: " + itemSelecionado,Toast.LENGTH_LONG).show();
    }

    public String supermercadosIDGET() {
        // Select All Query
        String selectQuery = "SELECT " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " WHERE " + "'" + text_supermercado_desconto + "'" + " = " + DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME + ";";

        db = db_h.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String ttemp = null;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ttemp = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //Toast.makeText(Novo_Produto.this, "You selected: " + idCategoria, Toast.LENGTH_LONG).show();
        return ttemp;
    }

    public String produtoIDGET() {
        // Select All Query
        String selectQuery = "SELECT " + DatabaseContract.Produtos.COL_PRODUTO_ID + " FROM " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " WHERE " + "'" + text_produto_id + "'" + " = " + DatabaseContract.Produtos.COL_NOME_PRODUTO + " AND " + DatabaseContract.Produtos.COL_SUPERMERCADO_ID + " = " + idSupermercado + ";";

        db = db_h.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String ttemp = null;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ttemp = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        //Toast.makeText(Novo_Produto.this, "You selected: " + idCategoria, Toast.LENGTH_LONG).show();
        return ttemp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmar_campanha_desconto:

                String descricao = editText_descricao.getText().toString();
                int desconto = Integer.parseInt(editText_desconto.getText().toString());
                int supermercado_ID = 0;
                int produto_id = 0;

                supermercado_ID = Integer.parseInt(idSupermercado);
                produto_id = Integer.parseInt(idProduto);

                CampanhaDescontos campanhaDescontos = new CampanhaDescontos(supermercado_ID, descricao, desconto, produto_id);

                dbManagerCampanhaDesconto.insert(campanhaDescontos);

                Intent main = new Intent(Criar_Campanha_Desconto.this, Listas_CampanhasDescontos.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
        finish();
    }
}