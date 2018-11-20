package com.example.ipca02.pantry.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 05-07-2017.
 */

public class CursorAdapterSupermercados extends CursorAdapter {

    SQLiteOpenHelper db_h;
    SQLiteDatabase db;

    public CursorAdapterSupermercados(Context context, Cursor cursor) {
        super(context, cursor, 0);
        db_h = new DatabaseHelper(context);
        db = db_h.getWritableDatabase();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lista_supermercados_row, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView_nome_supermercado = (TextView) view.findViewById(R.id.textView_nome_supermercado);


        DatabaseHelper db_h;
        db_h = new DatabaseHelper(context);
        SQLiteDatabase db = db_h.getWritableDatabase();

        String sql = "SELECT * FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + ";";
        Cursor supermercado = db.rawQuery(sql, null);

        if (supermercado.moveToFirst()) {
            textView_nome_supermercado.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME)));
        }
        supermercado.close();
    }
}
