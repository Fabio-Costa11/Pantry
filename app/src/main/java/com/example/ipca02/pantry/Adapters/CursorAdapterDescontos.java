package com.example.ipca02.pantry.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ipca02.pantry.Activitys.SMS_Numbers;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 05-07-2017.
 */

public class CursorAdapterDescontos extends CursorAdapter {

    Context context;
    SQLiteOpenHelper db_h;
    SQLiteDatabase db;
    String id_desconto;

    public CursorAdapterDescontos(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
        db_h = new DatabaseHelper(context);
        db = db_h.getWritableDatabase();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lista_descontos_row, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView_supermercados = (TextView) view.findViewById(R.id.textView_supermercados);
        TextView textView_id_descontos = (TextView) view.findViewById(R.id.textView_id_descontos);
        TextView textView_descontos = (TextView) view.findViewById(R.id.textView_descontos);
        TextView textView_descontos_descricao = (TextView) view.findViewById(R.id.textView_descontos_descricao);


        DatabaseHelper db_h;
        db_h = new DatabaseHelper(context);
        SQLiteDatabase db = db_h.getWritableDatabase();

        String sql = "SELECT * FROM " + DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO + ";";
        Cursor supermercado = db.rawQuery(sql, null);

        if (supermercado.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_SUPERMERCADO_ID));
            String supermercados = selectAllSupermercados(id);
            textView_supermercados.setText(supermercados);
            String id_descontos = cursor.getString(cursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID));
            textView_descontos.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_DESCONTO)));
            textView_descontos_descricao.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_DESCRICAO)));

            textView_id_descontos.setText(id_descontos);
            id_desconto = id_descontos;
        }
        supermercado.close();

    }

    public String selectAllSupermercados(String id_supermecado) {

        String supermercado = null;
        Cursor cursor;
        String sql = "SELECT *FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " WHERE " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " = " + id_supermecado + ";";
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {

                supermercado = cursor.getString(cursor.getColumnIndex(DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME));
                //categorias.setCategorias_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Categorias.COL_CATEGORIA_ID)));
            } while (cursor.moveToNext());
        }
        return supermercado;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ListView lv = (ListView) parent;

        ImageView imageView_send = (ImageView) v.findViewById(R.id.send);
        imageView_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SMS_Numbers.class);
                intent.putExtra("ID_DESCONTO", id_desconto);
                context.startActivity(intent);
            }
        });


        return v;

    }
}
