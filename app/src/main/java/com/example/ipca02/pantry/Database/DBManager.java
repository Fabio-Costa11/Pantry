package com.example.ipca02.pantry.Database;

/**
 * Created by anupamchugh on 19/10/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ipca02.pantry.Models.Lista;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Lista lista) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseContract.Listas.COL_LISTA_NOME, lista.getNome_lista());
        contentValue.put(DatabaseContract.Listas.COL_DATA_CRIACAO_YEAR, lista.getData_criacao_lista_year());
        contentValue.put(DatabaseContract.Listas.COL_DATA_CRIACAO_MONTH, lista.getData_criacao_lista_month());
        contentValue.put(DatabaseContract.Listas.COL_DATA_CRIACAO_DAY, lista.getData_criacao_lista_day());
        contentValue.put(DatabaseContract.Listas.COL_DATA_CONCLUSAO_YEAR, lista.getData_conclusao_year());
        contentValue.put(DatabaseContract.Listas.COL_DATA_CONCLUSAO_MONTH, lista.getData_conclusao_month());
        contentValue.put(DatabaseContract.Listas.COL_DATA_CONCLUSAO_DAY, lista.getData_conclusao_day());
        contentValue.put(DatabaseContract.Listas.COL_ESTADO_LISTA, lista.getEstado());

        database.insert(DatabaseContract.Listas.TB_NAME_LISTAS, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseContract.Listas.COL_LISTA_ID, DatabaseContract.Listas.COL_LISTA_NOME, DatabaseContract.Listas.COL_DATA_CRIACAO_YEAR, DatabaseContract.Listas.COL_DATA_CRIACAO_MONTH, DatabaseContract.Listas.COL_DATA_CRIACAO_DAY, DatabaseContract.Listas.COL_DATA_CONCLUSAO_YEAR, DatabaseContract.Listas.COL_DATA_CONCLUSAO_MONTH, DatabaseContract.Listas.COL_DATA_CONCLUSAO_DAY, DatabaseContract.Listas.COL_ESTADO_LISTA};
        Cursor cursor = database.query(DatabaseContract.Listas.TB_NAME_LISTAS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Lista lista) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Listas.COL_LISTA_ID, lista.getId_lista());
        contentValues.put(DatabaseContract.Listas.COL_LISTA_NOME, lista.getNome_lista());
        contentValues.put(DatabaseContract.Listas.COL_DATA_CRIACAO_YEAR, lista.getData_criacao_lista_year());
        contentValues.put(DatabaseContract.Listas.COL_DATA_CRIACAO_MONTH, lista.getData_criacao_lista_month());
        contentValues.put(DatabaseContract.Listas.COL_DATA_CRIACAO_DAY, lista.getData_criacao_lista_day());
        contentValues.put(DatabaseContract.Listas.COL_DATA_CONCLUSAO_YEAR, lista.getData_conclusao_year());
        contentValues.put(DatabaseContract.Listas.COL_DATA_CONCLUSAO_MONTH, lista.getData_conclusao_month());
        contentValues.put(DatabaseContract.Listas.COL_DATA_CONCLUSAO_DAY, lista.getData_conclusao_day());
        contentValues.put(DatabaseContract.Listas.COL_ESTADO_LISTA, lista.getEstado());
        int i = database.update(DatabaseContract.Listas.TB_NAME_LISTAS, contentValues, DatabaseContract.Listas.COL_LISTA_ID + " = " + lista.getId_lista(), null);
        return i;
    }

    public void delete(Lista lista) {
        database.delete(DatabaseContract.Listas.TB_NAME_LISTAS, DatabaseContract.Listas.COL_LISTA_ID + "=" + lista.getId_lista(), null);
    }

}
