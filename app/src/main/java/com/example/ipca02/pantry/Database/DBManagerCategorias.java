package com.example.ipca02.pantry.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ipca02.pantry.Models.Categorias;
import com.example.ipca02.pantry.Models.Lista;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class DBManagerCategorias {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerCategorias(Context c) {
        context = c;
    }

    public DBManagerCategorias open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Categorias lista) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseContract.Categorias.COL_CATEGORIA_NOME, lista.getCategorias_nome());

        database.insert(DatabaseContract.Categorias.TB_NAME_CATEGORIAS, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseContract.Categorias.COL_CATEGORIA_ID, DatabaseContract.Categorias.COL_CATEGORIA_NOME};
        Cursor cursor = database.query(DatabaseContract.Categorias.TB_NAME_CATEGORIAS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Categorias lista) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Categorias.COL_CATEGORIA_NOME, lista.getCategorias_nome());
        int i = database.update(DatabaseContract.Categorias.TB_NAME_CATEGORIAS, contentValues, DatabaseContract.Categorias.COL_CATEGORIA_ID + " = " + lista.getCategorias_id(), null);
        return i;
    }

    public void delete(Categorias lista) {
        database.delete(DatabaseContract.Categorias.TB_NAME_CATEGORIAS, DatabaseContract.Categorias.COL_CATEGORIA_ID + "=" + lista.getCategorias_id(), null);
    }

}
