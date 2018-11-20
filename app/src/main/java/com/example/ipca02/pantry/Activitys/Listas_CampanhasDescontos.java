package com.example.ipca02.pantry.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ipca02.pantry.Adapters.CursorAdapterDescontos;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 05-07-2017.
 */

public class Listas_CampanhasDescontos extends AppCompatActivity {

    private DatabaseHelper db_h;
    ImageView adicionar_campanhadesconto;
    ListView lista_campanhadescontos;
    CursorAdapterDescontos adapter_campanhadesconto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_campanhas_desconto);
        lista_campanhadescontos = (ListView) findViewById(R.id.listview_campanhasdesconto);

        adicionar_campanhadesconto = (ImageView) findViewById(R.id.add_campanha_desconto);

        adicionar_campanhadesconto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Criar_Campanha_Desconto.class);
                startActivity(intent);
            }
        });

        insertListaSupermercados();
    }

    public void insertListaSupermercados() {
        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();

        String sql = "SELECT * FROM " + DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO + ";";
        Cursor todoCursor = db.rawQuery(sql, null);

        adapter_campanhadesconto = new CursorAdapterDescontos(Listas_CampanhasDescontos.this, todoCursor);
        lista_campanhadescontos.setAdapter(adapter_campanhadesconto);
        //lista_produtos_adicionados.setSelector(R.drawable.selector);
        //Toast.makeText(Lista_Produtos_Adicionado.this, nome_lista, Toast.LENGTH_SHORT).show();
    }
}
