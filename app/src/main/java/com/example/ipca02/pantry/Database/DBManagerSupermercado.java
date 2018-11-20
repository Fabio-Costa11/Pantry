package com.example.ipca02.pantry.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ipca02.pantry.Models.Lista;
import com.example.ipca02.pantry.Models.Supermercado;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class DBManagerSupermercado {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerSupermercado(Context c) {
        context = c;
    }

    public DBManagerSupermercado open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Supermercado lista) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME, lista.getNome_supermercado());
        contentValue.put(DatabaseContract.Supermercado.COL_COORDENADAS, lista.getCoordenadas());

        database.insert(DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseContract.Supermercado.COL_SUPERMERCADO_ID, DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME};
        Cursor cursor = database.query(DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Supermercado lista) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME, lista.getNome_supermercado());
        int i = database.update(DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO, contentValues, DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " = " + lista.getId_supermercado(), null);
        return i;
    }

    public void delete(Supermercado lista) {
        database.delete(DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO, DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + "=" + lista.getId_supermercado(), null);
    }

}
