package com.example.ipca02.pantry.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ipca02.pantry.Adapters.CursorAdapterSupermercados;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 05-07-2017.
 */

public class Lista_Supermercados extends AppCompatActivity {

    private DatabaseHelper db_h;
    ImageView adicionar_supermercado;
    ListView lista_supermercados;
    CursorAdapterSupermercados adapter_supermercados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_supermercados);
        lista_supermercados = (ListView) findViewById(R.id.listview_supermercados);

        adicionar_supermercado = (ImageView) findViewById(R.id.add_supermercado);

        adicionar_supermercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Novo_Supermercado.class);
                startActivity(intent);
            }
        });

        insertListaSupermercados();
    }

    public void insertListaSupermercados() {
        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();

        String sql = "SELECT * FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + ";";
        Cursor todoCursor = db.rawQuery(sql, null);

        adapter_supermercados = new CursorAdapterSupermercados(Lista_Supermercados.this, todoCursor);
        lista_supermercados.setAdapter(adapter_supermercados);
        //lista_produtos_adicionados.setSelector(R.drawable.selector);
        //Toast.makeText(Lista_Produtos_Adicionado.this, nome_lista, Toast.LENGTH_SHORT).show();
    }
}
