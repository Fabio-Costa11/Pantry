package com.example.ipca02.pantry.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ipca02.pantry.Activitys.Lista_Produtos_Adicionado;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Lista_Produtos_Adicionados;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class CursorAdapter_Produtos_Adicionados extends CursorAdapter {
    SQLiteOpenHelper db_h;
    SQLiteDatabase db;

    public CursorAdapter_Produtos_Adicionados(Context context, Cursor cursor) {
        super(context, cursor, 0);
        db_h = new DatabaseHelper(context);
        db = db_h.getWritableDatabase();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lista_produtos_comprar_row, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView_id_produto_adicionado = (TextView) view.findViewById(R.id.textView_id_produto_adicionado);
        TextView textView_lista_produtos_adicionados = (TextView) view.findViewById(R.id.textView_produto_adicionado);
        TextView textView_quantidade = (TextView) view.findViewById(R.id.textView_quantidade);
        TextView textView_preco = (TextView) view.findViewById(R.id.textView_preco);
        TextView textView_supermercado_lista = (TextView) view.findViewById(R.id.textView_supermercado_lista);



        DatabaseHelper db_h;
        db_h = new DatabaseHelper(context);
        SQLiteDatabase db = db_h.getWritableDatabase();
        int produto_id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ProdutosLista.COL_PRODUTO_ID));

        String sql = "SELECT * FROM " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " WHERE " + DatabaseContract.Produtos.COL_PRODUTO_ID + " = " + produto_id + ";";
        Cursor produto = db.rawQuery(sql, null);
        if (produto.moveToFirst()) {
            textView_id_produto_adicionado.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA)));
            textView_lista_produtos_adicionados.setText(produto.getString(produto.getColumnIndex(DatabaseContract.Produtos.COL_NOME_PRODUTO)));
            textView_quantidade.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProdutosLista.COL_QUANTIDADE)));
            textView_preco.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProdutosLista.COL_PRECO)));

            String id_supermercado;
            id_supermercado = cursor.getString(cursor.getColumnIndex(DatabaseContract.ProdutosLista.COL_SUPERMERCADO_ID));
            String tv_nome_supermercado = selectAllSupermercados(id_supermercado);
            textView_supermercado_lista.setText(tv_nome_supermercado);
        }
        produto.close();
    }

    public String selectAllSupermercados(String id_supermecado) {

        String supermercado = null;
        Cursor cursor;
        String sql = "SELECT *FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " WHERE " +DatabaseContract.Supermercado.COL_SUPERMERCADO_ID +" = " + id_supermecado + ";";
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
}