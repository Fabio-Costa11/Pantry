package com.example.ipca02.pantry.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ipca02.pantry.Activitys.SMS_Numbers;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 05-07-2017.
 */

public class CursorAdapterListaComprasHistorico extends CursorAdapter {
    Context context;
    String temp;

    public CursorAdapterListaComprasHistorico(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lista_compra_historico_row, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find fields to populate in inflated template

        TextView tv_id_lista = (TextView) view.findViewById(R.id.textView_id_lista);
        TextView tv_lista_compras = (TextView) view.findViewById(R.id.textView_lista_compras);


        // Extract properties from cursor
        String nome_lista = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Listas.COL_LISTA_NOME));
        String id_lista = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Listas.COL_LISTA_ID));
        // Populate fields with extracted properties
        tv_lista_compras.setText(nome_lista);
        tv_id_lista.setText(id_lista);
        temp = id_lista;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ListView lv = (ListView) parent;
        return v;

    }
}