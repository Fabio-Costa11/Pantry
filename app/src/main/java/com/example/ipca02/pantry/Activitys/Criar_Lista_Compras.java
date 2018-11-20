package com.example.ipca02.pantry.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ipca02.pantry.Database.DBManager;
import com.example.ipca02.pantry.Models.Lista;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IPCA02 on 31-05-2017.
 */

public class Criar_Lista_Compras extends AppCompatActivity implements View.OnClickListener {

    private Button btn_confirmar_lista;
    private EditText editText_nova_lista;
    int hour, minute, day, month, year;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_lista_compras);

        btn_confirmar_lista = (Button) findViewById(R.id.btn_confirmar_nova_lista);
        editText_nova_lista = (EditText) findViewById(R.id.editText_nova_lista);

        dbManager = new DBManager(this);
        dbManager.open();
        btn_confirmar_lista.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        minute = c.get(Calendar.MINUTE);
        hour = c.get(Calendar.HOUR_OF_DAY);
        day = c.get(Calendar.DAY_OF_WEEK);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmar_nova_lista:

                final String name = editText_nova_lista.getText().toString();

                String data_criacao_year = String.valueOf(year);
                String data_criacao_month = String.valueOf(month);
                String data_criacao_day = String.valueOf(day);
                String data_conclusao_year = "";
                String data_conclusao_month = "";
                String data_conclusao_day = "";
                int estado = 0;

                Lista lista = new Lista(name, data_criacao_year, data_criacao_month, data_criacao_day, data_conclusao_year, data_criacao_month, data_conclusao_day, estado);


                dbManager.insert(lista);

                Intent mainac = new Intent(Criar_Lista_Compras.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(mainac);
                break;
        }
    }
}