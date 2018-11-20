package com.example.ipca02.pantry.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ipca02.pantry.Database.DBManagerSupermercado;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Supermercado;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 14-06-2017.
 */

public class Novo_Supermercado extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper db_h;
    String nome_supermercado, coordenadas;
    EditText editText_novo_supermercado, editText_coordenadas;
    Button btn_confirmar_novo_supermercado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_supermercado);

        editText_novo_supermercado = (EditText) findViewById(R.id.editText_novo_supermercado);
        editText_coordenadas = (EditText) findViewById(R.id.editText_coordenadas);
        btn_confirmar_novo_supermercado = (Button) findViewById(R.id.btn_confirmar_novo_supermercado);

        btn_confirmar_novo_supermercado.setOnClickListener(this);
    }

    public void onBackPressed(){
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmar_novo_supermercado:

                DBManagerSupermercado dbManagerSupermercado = new DBManagerSupermercado(Novo_Supermercado.this);
                dbManagerSupermercado.open();

                nome_supermercado = editText_novo_supermercado.getText().toString();
                coordenadas = editText_coordenadas.getText().toString();
                Supermercado supermercado = new Supermercado(nome_supermercado,coordenadas);
                dbManagerSupermercado.insert(supermercado);

                Intent main = new Intent(Novo_Supermercado.this, Lista_Supermercados.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
        finish();
    }
}
