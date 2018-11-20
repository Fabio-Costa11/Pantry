package com.example.ipca02.pantry.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.ipca02.pantry.Adapters.ExpandableListViewAdapter;

import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Categorias;
import com.example.ipca02.pantry.Models.Produto;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IPCA02 on 20-06-2017.
 */

public class Ver_Produtos extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private List<String> parentHeaderInformation;
    private Button adicionar_produto;
    SQLiteOpenHelper db_h;
    SQLiteDatabase db;

    private List<Categorias> listCategories;
    private HashMap<Categorias, List<Produto>> listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_produtos);

        Bundle extras = getIntent().getExtras();

        db_h = new DatabaseHelper(this);
        db = db_h.getWritableDatabase();

        parentHeaderInformation = new ArrayList<String>();

        buildList();

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_ver_produtos);
        adicionar_produto = (Button) findViewById(R.id.adicionar_produto);
        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(getApplicationContext(), listCategories, listProducts);

        expandableListView.setAdapter(expandableListViewAdapter);

        adicionar_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Novo_Produto.class);
                startActivity(intent);
            }
        });

            /*expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, final int childPosition, final long id) {

      /* You must make use of the View v, find the view by id and extract the text as below*/

                    /*TextView tv = (TextView) v.findViewById(R.id.child_layout);
                    TextView tv_ID = (TextView) v.findViewById(R.id.child_layout_id);

                    final String data = tv.getText().toString();
                    final String data_id = tv_ID.getText().toString();

                    // custom dialog
                    final Dialog dialog = new Dialog(Ver_Produtos.this);
                    dialog.setContentView(R.layout.dialog_adicionar_produto);
                    dialog.setTitle("Adicionar Produto a Lista");

                    // set the custom dialog components - text, image and button
                    final EditText editText_preco = (EditText) dialog.findViewById(R.id.editText_preco_produto);
                    final EditText editText_quantidade = (EditText) dialog.findViewById(R.id.editText_quantidade_produto);

                    Button dialogButton = (Button) dialog.findViewById(R.id.btn_adicionar_produto);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DBManagerProducts dbManagerProducts = new DBManagerProducts(com.example.ipca02.pantry.Activitys.ProdutosActivity.this);
                            dbManagerProducts.open();

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

                            DBManagerListaProdutosAdicionados dbManagerListaProdutosAdicionados = new DBManagerListaProdutosAdicionados(com.example.ipca02.pantry.Activitys.ProdutosActivity.this);
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

                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    Toast.makeText(com.example.ipca02.pantry.Activitys.ProdutosActivity.this, data + " " + data_id, Toast.LENGTH_SHORT).show();
                    return true;  // i missed this
                }
            });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildList();
    }

    public void onBackPressed(){
            this.finish();
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

    public void buildList() {

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
