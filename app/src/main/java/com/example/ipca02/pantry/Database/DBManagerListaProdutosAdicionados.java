package com.example.ipca02.pantry.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ipca02.pantry.Models.Lista;
import com.example.ipca02.pantry.Models.Lista_Produtos_Adicionados;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class DBManagerListaProdutosAdicionados {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerListaProdutosAdicionados(Context c) {
        context = c;
    }

    public DBManagerListaProdutosAdicionados open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Lista_Produtos_Adicionados lista) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseContract.ProdutosLista.COL_PRODUTO_ID, lista.getProduto_id());
        contentValue.put(DatabaseContract.ProdutosLista.COL_LISTA_ID, lista.getLista_id());
        contentValue.put(DatabaseContract.ProdutosLista.COL_SUPERMERCADO_ID, lista.getSupermercado_id());
        contentValue.put(DatabaseContract.ProdutosLista.COL_QUANTIDADE, lista.getQuantidade());
        contentValue.put(DatabaseContract.ProdutosLista.COL_PRECO, lista.getPreco());
        contentValue.put(DatabaseContract.ProdutosLista.COL_COMPRADO, lista.getComprado());

        database.insert(DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA, DatabaseContract.ProdutosLista.COL_PRECO, DatabaseContract.ProdutosLista.COL_PRODUTO_ID, DatabaseContract.ProdutosLista.COL_LISTA_ID, DatabaseContract.ProdutosLista.COL_QUANTIDADE, DatabaseContract.ProdutosLista.COL_SUPERMERCADO_ID, DatabaseContract.ProdutosLista.COL_COMPRADO};
        Cursor cursor = database.query(DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Lista_Produtos_Adicionados lista) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.ProdutosLista.COL_PRODUTO_ID, lista.getProduto_id());
        contentValues.put(DatabaseContract.ProdutosLista.COL_LISTA_ID, lista.getLista_id());
        contentValues.put(DatabaseContract.ProdutosLista.COL_SUPERMERCADO_ID, lista.getSupermercado_id());
        contentValues.put(DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA, lista.getLista_id());
        contentValues.put(DatabaseContract.ProdutosLista.COL_QUANTIDADE, lista.getQuantidade());
        contentValues.put(DatabaseContract.ProdutosLista.COL_PRECO, lista.getPreco());
        contentValues.put(DatabaseContract.ProdutosLista.COL_COMPRADO, lista.getComprado());
        int i = database.update(DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA, contentValues, DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA + " = " + lista.getId_produtos_lista(), null);
        return i;
    }

    public void delete(Lista_Produtos_Adicionados lista) {
        database.delete(DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA, DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA + "=" + lista.getId_produtos_lista(), null);
    }

}
