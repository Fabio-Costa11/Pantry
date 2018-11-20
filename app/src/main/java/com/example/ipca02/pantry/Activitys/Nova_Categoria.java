package com.example.ipca02.pantry.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ipca02.pantry.Database.DBManagerCategorias;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.Models.Categorias;
import com.example.ipca02.pantry.R;

/**
 * Created by IPCA02 on 14-06-2017.
 */

public class Nova_Categoria extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper db_h;
    String nome_categoria;
    EditText editText_nome_categoria;
    Button btn_confirmar_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_categoria);

        editText_nome_categoria = (EditText) findViewById(R.id.editText_nome_categoria);
        btn_confirmar_categoria = (Button) findViewById(R.id.btn_confirmar_categoria);

        btn_confirmar_categoria.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmar_categoria:

                DBManagerCategorias dbManagerCategorias = new DBManagerCategorias(Nova_Categoria.this);
                dbManagerCategorias.open();

                nome_categoria = editText_nome_categoria.getText().toString();
                Categorias categorias = new Categorias(nome_categoria);
                dbManagerCategorias.insert(categorias);

                Intent main = new Intent(Nova_Categoria.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;

        }
    }
}
