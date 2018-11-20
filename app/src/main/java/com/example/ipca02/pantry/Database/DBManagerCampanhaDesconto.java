package com.example.ipca02.pantry.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ipca02.pantry.Models.CampanhaDescontos;

/**
 * Created by IPCA02 on 27-06-2017.
 */

public class DBManagerCampanhaDesconto {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerCampanhaDesconto(Context c) {
        context = c;
    }

    public DBManagerCampanhaDesconto open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(CampanhaDescontos campanhaDescontos) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseContract.CampanhasDesconto.COL_SUPERMERCADO_ID, campanhaDescontos.getSupermercado_id());
        contentValue.put(DatabaseContract.CampanhasDesconto.COL_DESCRICAO, campanhaDescontos.getDescricao());
        contentValue.put(DatabaseContract.CampanhasDesconto.COL_DESCONTO, campanhaDescontos.getDesconto());
        contentValue.put(DatabaseContract.CampanhasDesconto.COL_PRODUTO_ID, campanhaDescontos.getProduto_id());
        database.insert(DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID, DatabaseContract.CampanhasDesconto.COL_DESCRICAO};
        Cursor cursor = database.query(DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(CampanhaDescontos campanhaDescontos) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME, campanhaDescontos.getDescricao());
        int i = database.update(DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO, contentValues, DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID + " = " + campanhaDescontos.getId_campanha_descontos(), null);
        return i;
    }

    public void delete(CampanhaDescontos campanhaDescontos) {
        database.delete(DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO, DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID + "=" + campanhaDescontos.getId_campanha_descontos(), null);
    }
}
