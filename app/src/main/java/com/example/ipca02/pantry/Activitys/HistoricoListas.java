package com.example.ipca02.pantry.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ipca02.pantry.Adapters.CursorAdapterListaCompras;
import com.example.ipca02.pantry.Adapters.CursorAdapterListaComprasHistorico;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Lista;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 22-06-2017.
 */

public class HistoricoListas extends AppCompatActivity {

    private DatabaseHelper db_h;
    ListView lista_compras;
    int id_remover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_listas);

        lista_compras = (ListView) findViewById(R.id.lista_listas);


        lista_compras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView_remover_lista = (TextView) view.findViewById(R.id.textView_id_lista);
                id_remover = Integer.parseInt(textView_remover_lista.getText().toString());

                Intent intent = new Intent(getBaseContext(), HistoricoActivity.class);
                intent.putExtra("ID_LISTA", id_remover);
                startActivity(intent);
            }
        });

        /*lista_compras.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView_remover_lista = (TextView) view.findViewById(R.id.textView_id_lista);

                final String id_remover = textView_remover_lista.getText().toString();

                // custom dialog
                final Dialog dialog = new Dialog(HistoricoListas.this);
                dialog.setContentView(R.layout.dialog_remover_lista);
                dialog.setTitle("Remover Produto da Lista");

                // set the custom dialog components - text, image and button
                final Button btn_remover_lista = (Button) dialog.findViewById(R.id.btn_remover_lista);
                // if button is clicked, close the custom dialog
                btn_remover_lista.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteTitle(id_remover);
                        dialog.dismiss();
                    }
                });

                dialog.show();

                return true;
            }
        });*/
    }


    public void inserirlista() {

        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();
        String sql = "SELECT * FROM " + DatabaseContract.Listas.TB_NAME_LISTAS + " WHERE " + DatabaseContract.Listas.COL_ESTADO_LISTA + " = 1" + " ORDER BY " + DatabaseContract.Listas.COL_DATA_CONCLUSAO_DAY + " ASC " + ";";
        final Cursor todoCursor = db.rawQuery(sql, null);

        CursorAdapterListaComprasHistorico todoCursorAdapter = new CursorAdapterListaComprasHistorico(HistoricoListas.this, todoCursor);

        lista_compras.setAdapter(todoCursorAdapter);
    }


    public boolean deleteTitle(String id_remover) {
        final SQLiteDatabase db = db_h.getWritableDatabase();
        return db.delete(DatabaseContract.Listas.TB_NAME_LISTAS, DatabaseContract.Listas.COL_LISTA_ID + "=" + id_remover, null) > 0;
    }

    public void onBackPressed(){
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inserirlista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//--------------------------------------------------------
            Intent intent = new Intent(getBaseContext(), SMS_Numbers.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_refresh_produtos_adicionados) {
            inserirlista();
            return true;
        }

        if (id == R.id.action_nova_lista) {
            Intent intent = new Intent(getBaseContext(), Criar_Lista_Compras.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_novo_produto) {
            Intent intent = new Intent(getBaseContext(), Novo_Produto.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_nova_categoria) {
            Intent intent = new Intent(getBaseContext(), Nova_Categoria.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_novo_supermercado) {
            Intent intent = new Intent(getBaseContext(), Novo_Supermercado.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_historico) {
            Intent intent = new Intent(getBaseContext(), HistoricoActivity.class);
            intent.putExtra("ID_LISTA", id_remover);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}