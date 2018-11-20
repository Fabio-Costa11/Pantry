package com.example.ipca02.pantry.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipca02.pantry.Adapters.ExpandableListViewAdapter;
import com.example.ipca02.pantry.Database.DBManagerListaProdutosAdicionados;
import com.example.ipca02.pantry.Database.DBManagerProducts;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Categorias;
import com.example.ipca02.pantry.Models.Lista_Produtos_Adicionados;
import com.example.ipca02.pantry.Models.Produto;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IPCA02 on 31-05-2017.
 */

public class ProdutosActivity extends ActionBarActivity {

    private ExpandableListView expandableListView;
    //private List<String> parentHeaderInformation;
    SQLiteOpenHelper db_h;
    SQLiteDatabase db;

    private List<Categorias> listCategories;
    private HashMap<Categorias, List<Produto>> listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produtos_activity);

        Bundle extras = getIntent().getExtras();

        final int id_lista = extras.getInt("ID_LISTA");


        db_h = new DatabaseHelper(this);
        db = db_h.getWritableDatabase();

        //parentHeaderInformation = new ArrayList<String>();
        criarListaExpandable();

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(getApplicationContext(), listCategories, listProducts);

        expandableListView.setAdapter(expandableListViewAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, final int childPosition, final long id) {

      /* You must make use of the View v, find the view by id and extract the text as below*/

                TextView tv = (TextView) v.findViewById(R.id.child_layout);
                TextView tv_ID = (TextView) v.findViewById(R.id.child_layout_id);

                final String data = tv.getText().toString();
                final String data_id = tv_ID.getText().toString();

                // custom dialog
                final Dialog dialog = new Dialog(ProdutosActivity.this);
                dialog.setContentView(R.layout.dialog_adicionar_produto);
                dialog.setTitle("Adicionar Produto a Lista");

                // set the custom dialog components - text, image and button
                final EditText editText_preco = (EditText) dialog.findViewById(R.id.editText_preco_produto);
                final EditText editText_quantidade = (EditText) dialog.findViewById(R.id.editText_quantidade_produto);

                Produto produto = getProduto(data_id);

                editText_preco.setText(produto.getPreco());

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_adicionar_produto);
                // if button is clicked, close the custom dialog

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBManagerProducts dbManagerProducts = new DBManagerProducts(ProdutosActivity.this);
                        dbManagerProducts.open();

                        String temp = editText_quantidade.getText().toString();

                        if (temp.equals("0") || temp.equals("")){
                            Toast.makeText(ProdutosActivity.this, " Quantidade tem de ser maior que 0.", Toast.LENGTH_SHORT).show();

                        } else {
                            Produto produto = new Produto();
                            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " WHERE " + DatabaseContract.Produtos.COL_PRODUTO_ID + " = " + data_id, null);
                            cursor.moveToFirst();

                            produto.setId_produto(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_PRODUTO_ID)));
                            produto.setNome_produto(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_NOME_PRODUTO)));
                            produto.setCategoria_produto(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_CATEGORIA_ID)));
                            produto.setPreco(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_PRECO)));
                            produto.setSupermercado_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_SUPERMERCADO_ID)));
                            produto.setNome_produto(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_NOME_PRODUTO)));

                            cursor.close();

                            DBManagerListaProdutosAdicionados dbManagerListaProdutosAdicionados = new DBManagerListaProdutosAdicionados(ProdutosActivity.this);
                            dbManagerListaProdutosAdicionados.open();

                            String preco = editText_preco.getText().toString();
                            String quantidade = editText_quantidade.getText().toString();

                            Lista_Produtos_Adicionados produtos_adicionados = new Lista_Produtos_Adicionados();
                            produtos_adicionados.setLista_id(id_lista);
                            produtos_adicionados.setNome_produto(produto.getNome_produto());
                            produtos_adicionados.setPreco(preco);
                            produtos_adicionados.setQuantidade(quantidade);
                            produtos_adicionados.setSupermercado_id(String.valueOf(produto.getSupermercado_id()));
                            produtos_adicionados.setComprado(0);
                            produtos_adicionados.setProduto_id(Integer.parseInt(data_id));
                            dbManagerListaProdutosAdicionados.insert(produtos_adicionados);
                            dbManagerListaProdutosAdicionados.close();
                        }



                        dialog.dismiss();
                    }
                });

                dialog.show();

                Toast.makeText(ProdutosActivity.this, data + " " + data_id, Toast.LENGTH_SHORT).show();
                return true;  // i missed this
            }
        });

    }

    public Produto getProduto(String id){

        Produto produto = new Produto();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " WHERE " + DatabaseContract.Produtos.COL_PRODUTO_ID + " = " + id, null);
        cursor.moveToFirst();

        produto.setId_produto(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_PRODUTO_ID)));
        produto.setNome_produto(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_NOME_PRODUTO)));
        produto.setCategoria_produto(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_CATEGORIA_ID)));
        produto.setPreco(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_PRECO)));
        produto.setSupermercado_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_SUPERMERCADO_ID)));
        produto.setNome_produto(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_NOME_PRODUTO)));

        cursor.close();


        return produto;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.action_nova_lista) {
            Intent intent = new Intent(getBaseContext(), Criar_Lista_Compras.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_novo_produto) {
            Intent intent = new Intent(getBaseContext(), Novo_Produto.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_nova_categoria) {
            Intent intent = new Intent(getBaseContext(), Nova_Categoria.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_novo_supermercado) {
            Intent intent = new Intent(getBaseContext(), Novo_Supermercado.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Categorias> selectAllCategorias() {

        ArrayList<Categorias> allcategorias;
        allcategorias = new ArrayList<Categorias>();
        Categorias categorias;
        Cursor cursor;
        String sql = "SELECT *FROM " + DatabaseContract.Categorias.TB_NAME_CATEGORIAS + ";";
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {
                categorias = new Categorias();

                categorias.setCategorias_nome(cursor.getString(cursor.getColumnIndex(DatabaseContract.Categorias.COL_CATEGORIA_NOME)));
                categorias.setCategorias_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Categorias.COL_CATEGORIA_ID)));
                allcategorias.add(categorias);
            } while (cursor.moveToNext());
        }
        return allcategorias;
    }

    public ArrayList<Produto> selectProdutoCategoria(Categorias categorias) {

        ArrayList<Produto> produtos;
        produtos = new ArrayList<Produto>();
        Cursor cursor;

        String sql = " SELECT * FROM " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " WHERE " + DatabaseContract.Produtos.COL_CATEGORIA_ID + " = " + categorias.getCategorias_id();
        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {

            Produto produto;
            cursor.moveToFirst();

            do {
                produto = new Produto();
                produto.setSupermercado_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_SUPERMERCADO_ID)));
                produto.setPreco(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_PRECO)));
                produto.setNome_produto(cursor.getString(cursor.getColumnIndex(DatabaseContract.Produtos.COL_NOME_PRODUTO)));
                produto.setCategoria_produto(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_CATEGORIA_ID)));
                produto.setId_produto(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_PRODUTO_ID)));


                Categorias mainGenre = new Categorias();
                mainGenre.setCategorias_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Produtos.COL_CATEGORIA_ID)));

                produtos.add(produto);

            } while (cursor.moveToNext());
        }

        return produtos;
    }

    public void criarListaExpandable() {

        listCategories = new ArrayList<Categorias>();
        listProducts = new HashMap<Categorias, List<Produto>>();

        listCategories = selectAllCategorias();
        //----------------------------------------------------
        for (int i = 0; i < listCategories.size(); i++) {
            List<Produto> auxList = selectProdutoCategoria(listCategories.get(i));
            listProducts.put(listCategories.get(i), auxList);
        }
    }

}
