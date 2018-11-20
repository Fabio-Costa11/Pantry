package com.example.ipca02.pantry.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ipca02.pantry.Models.Produto;

/**
 * Created by IPCA02 on 07-06-2017.
 */

public class DBManagerProducts {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerProducts(Context c) {
        context = c;
    }

    public DBManagerProducts open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Produto produto) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseContract.Produtos.COL_NOME_PRODUTO, produto.getNome_produto());
        contentValue.put(DatabaseContract.Produtos.COL_CATEGORIA_ID, produto.getCategoria_produto());
        contentValue.put(DatabaseContract.Produtos.COL_PRECO, produto.getPreco());
        contentValue.put(DatabaseContract.Produtos.COL_SUPERMERCADO_ID, produto.getSupermercado_id());


        //contentValue.put(DatabaseHelper.DESC, desc);
        database.insert(DatabaseContract.Produtos.TB_NAME_PRODUTOS, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseContract.Produtos.COL_PRODUTO_ID, DatabaseContract.Produtos.COL_NOME_PRODUTO, DatabaseContract.Produtos.COL_CATEGORIA_ID, DatabaseContract.Produtos.COL_PRECO, DatabaseContract.Produtos.COL_SUPERMERCADO_ID};
        Cursor cursor = database.query(DatabaseContract.Produtos.TB_NAME_PRODUTOS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Produto produto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Produtos.COL_PRODUTO_ID, produto.getId_produto());
        contentValues.put(DatabaseContract.Produtos.COL_NOME_PRODUTO, produto.getNome_produto());
        contentValues.put(DatabaseContract.Produtos.COL_CATEGORIA_ID, produto.getCategoria_produto());
        contentValues.put(DatabaseContract.Produtos.COL_PRECO, produto.getPreco());
        contentValues.put(DatabaseContract.Produtos.COL_SUPERMERCADO_ID, produto.getSupermercado_id());

        int i = database.update(DatabaseContract.Produtos.TB_NAME_PRODUTOS, contentValues, DatabaseContract.Produtos.COL_PRODUTO_ID + " = " + produto.getId_produto(), null);
        return i;
    }

    public void delete(Produto produto) {
        database.delete(DatabaseContract.Produtos.TB_NAME_PRODUTOS, DatabaseContract.Produtos.COL_PRODUTO_ID + "=" + produto.getId_produto(), null);
    }

}