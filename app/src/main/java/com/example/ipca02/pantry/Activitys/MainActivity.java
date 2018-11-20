package com.example.ipca02.pantry.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipca02.pantry.Adapters.CursorAdapterListaCompras;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db_h;
    ListView lista_compras;
    Button ver_produtos;
    ImageView add_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_list = (ImageView) findViewById(R.id.add_list);

        add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Criar_Lista_Compras.class);
                startActivity(intent);
            }
        });

        lista_compras = (ListView) findViewById(R.id.lista_listas);

        lista_compras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView_remover_lista = (TextView) view.findViewById(R.id.textView_id_lista);
                TextView textView_lista = (TextView) view.findViewById(R.id.textView_lista_compras);
                final int id_remover = Integer.parseInt(textView_remover_lista.getText().toString());
                final String nome_lista = textView_lista.getText().toString();

                Intent intent = new Intent(getBaseContext(), Lista_Produtos_Adicionado.class);
                intent.putExtra("NOME_LISTA", nome_lista);
                intent.putExtra("ID_LISTA", id_remover);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, lista_compras1.getNome_lista(), Toast.LENGTH_SHORT).show();
            }
        });

        lista_compras.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView_remover_lista = (TextView) view.findViewById(R.id.textView_id_lista);

                final String id_remover = textView_remover_lista.getText().toString();

                // custom dialog
                final Dialog dialog = new Dialog(MainActivity.this);
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
                        inserirlista();
                    }
                });

                dialog.show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        inserirlista();
    }

    public void onBackPressed() {
        finish();
    }

    public void inserirlista() {

        db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();
        String sql = "SELECT * FROM " + DatabaseContract.Listas.TB_NAME_LISTAS + " WHERE " + DatabaseContract.Listas.COL_ESTADO_LISTA + " = 0;";
        final Cursor todoCursor = db.rawQuery(sql, null);

        CursorAdapterListaCompras todoCursorAdapter = new CursorAdapterListaCompras(MainActivity.this, todoCursor);

        lista_compras.setAdapter(todoCursorAdapter);
    }


    public boolean deleteTitle(String id_remover) {
        final SQLiteDatabase db = db_h.getWritableDatabase();
        //String sql = "DELETE FROM " + DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA + " WHERE " + DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA + " = " + id_remover + ";";
        //db.rawQuery(sql,null);
        //Toast.makeText(Lista_Produtos_Adicionado.this, produto, Toast.LENGTH_SHORT).show();
        return db.delete(DatabaseContract.Listas.TB_NAME_LISTAS, DatabaseContract.Listas.COL_LISTA_ID + "=" + id_remover, null) > 0;
    }

    private void exportDB() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.example.ipca02.pantry" + "/databases/" + "Pantry.DB";
        String backupDBPath = "Pantry.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "Cópia de Segurança Efectuada!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Cópia de Segurança Não Efectuada!", Toast.LENGTH_LONG).show();
        }
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
            //Intent intent = new Intent(getBaseContext(), SMS_Numbers.class);
            //startActivity(intent);
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
            Intent intent = new Intent(getBaseContext(), HistoricoListas.class);
            //intent.putExtra("ID_LISTA", id_lista);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_backup) {
            exportDB();
            return true;
        }

        if (id == R.id.action_campanha_desconto) {
            Intent intent = new Intent(getBaseContext(), Criar_Campanha_Desconto.class);
            //intent.putExtra("ID_LISTA", id_lista);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}