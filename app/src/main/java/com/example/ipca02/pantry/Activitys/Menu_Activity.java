package com.example.ipca02.pantry.Activitys;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ipca02.pantry.R;

import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by IPCA02 on 04-07-2017.
 */

public class Menu_Activity extends AppCompatActivity {

    //private static final int REQUEST_PERMISSION = 10;
    Button imgview_listas,imgview_produtos, imgview_supermercados, imgview_historico, imgview_descontos, imgview_sair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/gill-sans-ultra-bold-595e6962900b4.ttf");

        imgview_listas = (Button) findViewById(R.id.imgview_listas);
        imgview_produtos = (Button) findViewById(R.id.imgview_produtos);
        imgview_supermercados = (Button) findViewById(R.id.imgview_supermercados);
        imgview_historico = (Button) findViewById(R.id.imgview_historico);
        imgview_descontos = (Button) findViewById(R.id.imgview_descontos);
        imgview_sair = (Button) findViewById(R.id.imgview_sair);

        imgview_listas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        imgview_listas.setTypeface(custom_font);



        imgview_produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Ver_Produtos.class);
                startActivity(intent);
            }
        });
        imgview_produtos.setTypeface(custom_font);

        imgview_supermercados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Lista_Supermercados.class);
                startActivity(intent);
            }
        });

        imgview_supermercados.setTypeface(custom_font);

        imgview_historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HistoricoListas.class);
                startActivity(intent);
            }
        });

        imgview_historico.setTypeface(custom_font);

        imgview_descontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Listas_CampanhasDescontos.class);
                startActivity(intent);
            }
        });

        imgview_descontos.setTypeface(custom_font);

        imgview_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgview_sair.setTypeface(custom_font);
        //permissoes
        /*requestAppPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.SEND_SMS,},
                R.string.msg, REQUEST_PERMISSION);*/
    }
}