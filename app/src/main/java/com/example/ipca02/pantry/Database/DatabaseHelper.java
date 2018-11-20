package com.example.ipca02.pantry.Database;

/**
 * Created by anupamchugh on 19/10/15.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ipca02.pantry.Models.Lista;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Information;

    static final String DB_NAME = "Pantry.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE_DESCONTOS = "CREATE TABLE " + DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO + " (" +
            DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.CampanhasDesconto.COL_SUPERMERCADO_ID + " INTEGER NOT NULL," +
            DatabaseContract.CampanhasDesconto.COL_PRODUTO_ID + " INTEGER NOT NULL," +
            DatabaseContract.CampanhasDesconto.COL_DESCRICAO + " TEXT," +
            DatabaseContract.CampanhasDesconto.COL_DESCONTO + " INTEGER NOT NULL," +
            " FOREIGN KEY(" + DatabaseContract.CampanhasDesconto.COL_SUPERMERCADO_ID + " )REFERENCES " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " ( " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " ) " + " );";

    private static final String CREATE_TABLE_CATEGORIAS = "CREATE TABLE " + DatabaseContract.Categorias.TB_NAME_CATEGORIAS + " (" +
            DatabaseContract.Categorias.COL_CATEGORIA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Categorias.COL_CATEGORIA_NOME + " TEXT NOT NULL " + " );";

    private static final String CREATE_TABLE_LISTAS = " CREATE TABLE " + DatabaseContract.Listas.TB_NAME_LISTAS + " (" +
            DatabaseContract.Listas.COL_LISTA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Listas.COL_LISTA_NOME + " TEXT," +
            DatabaseContract.Listas.COL_DATA_CRIACAO_YEAR + " TEXT NOT NULL," +
            DatabaseContract.Listas.COL_DATA_CRIACAO_MONTH + " TEXT NOT NULL," +
            DatabaseContract.Listas.COL_DATA_CRIACAO_DAY + " TEXT NOT NULL," +
            DatabaseContract.Listas.COL_DATA_CONCLUSAO_YEAR + " TEXT," +
            DatabaseContract.Listas.COL_DATA_CONCLUSAO_MONTH + " TEXT," +
            DatabaseContract.Listas.COL_DATA_CONCLUSAO_DAY + " TEXT," +
            DatabaseContract.Listas.COL_ESTADO_LISTA + " INTEGER NOT NULL" + " );";

    private static final String CREATE_TABLE_PRODUTOS = "CREATE TABLE " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " (" +
            DatabaseContract.Produtos.COL_PRODUTO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Produtos.COL_NOME_PRODUTO + " TEXT," +
            DatabaseContract.Produtos.COL_CATEGORIA_ID + " INTEGER NOT NULL," +
            DatabaseContract.Produtos.COL_PRECO + " TEXT," +
            DatabaseContract.Produtos.COL_SUPERMERCADO_ID + " INTEGER NOT NULL," +
            " FOREIGN KEY( " + DatabaseContract.Produtos.COL_SUPERMERCADO_ID + " )REFERENCES " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " ( " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " )," +
            " FOREIGN KEY( " + DatabaseContract.Produtos.COL_CATEGORIA_ID + " )REFERENCES " + DatabaseContract.Categorias.TB_NAME_CATEGORIAS + " ( " + DatabaseContract.Categorias.COL_CATEGORIA_ID + " )" + ");";

    private static final String CREATE_TABLE_PRODUTOS_LISTA = "CREATE TABLE " + DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA + " (" +
            DatabaseContract.ProdutosLista.COL_ID_PRODUTOSLISTA + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.ProdutosLista.COL_LISTA_ID + " INTEGER NOT NULL," +
            DatabaseContract.ProdutosLista.COL_PRODUTO_ID + " INTEGER NOT NULL," +
            DatabaseContract.ProdutosLista.COL_QUANTIDADE + " TEXT," +
            DatabaseContract.ProdutosLista.COL_SUPERMERCADO_ID + " INTEGER NOT NULL," +
            DatabaseContract.ProdutosLista.COL_PRECO + " TEXT NOT NULL," +
            DatabaseContract.ProdutosLista.COL_COMPRADO + " INTEGER NOT NULL," +
            " FOREIGN KEY( " + DatabaseContract.ProdutosLista.COL_SUPERMERCADO_ID + " )REFERENCES " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " ( " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " ), " +
            " FOREIGN KEY( " + DatabaseContract.ProdutosLista.COL_LISTA_ID + " )REFERENCES " + DatabaseContract.Listas.TB_NAME_LISTAS + " ( " + DatabaseContract.Listas.COL_LISTA_ID + " ), " +
            " FOREIGN KEY( " + DatabaseContract.ProdutosLista.COL_PRODUTO_ID + " )REFERENCES " + DatabaseContract.Produtos.TB_NAME_PRODUTOS + " ( " + DatabaseContract.Produtos.COL_PRODUTO_ID + " )" + ");";

    private static final String CREATE_TABLE_SUPERMERCADO = "CREATE TABLE " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " (" +
            DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME + " TEXT NOT NULL, " + DatabaseContract.Supermercado.COL_COORDENADAS + " TEXT" +" );";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIAS);
        db.execSQL(CREATE_TABLE_LISTAS);
        db.execSQL(CREATE_TABLE_SUPERMERCADO);
        db.execSQL(CREATE_TABLE_DESCONTOS);
        db.execSQL(CREATE_TABLE_PRODUTOS);
        db.execSQL(CREATE_TABLE_PRODUTOS_LISTA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Categorias.TB_NAME_CATEGORIAS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Produtos.TB_NAME_PRODUTOS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Listas.TB_NAME_LISTAS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ProdutosLista.TB_NAME_PRODUTOS_LISTA);
        onCreate(db);
    }

    public List<Lista> getAllContacts() {
        List<Lista> comprasLista = new ArrayList<Lista>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseContract.Listas.TB_NAME_LISTAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Lista lista_compras = new Lista();
                lista_compras.setId_lista(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_LISTA_ID))));
                lista_compras.setNome_lista(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_LISTA_NOME)));
                lista_compras.setData_conclusao_year(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_DATA_CONCLUSAO_YEAR)));
                lista_compras.setData_conclusao_month(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_DATA_CONCLUSAO_MONTH)));
                lista_compras.setData_conclusao_day(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_DATA_CONCLUSAO_DAY)));
                lista_compras.setData_criacao_lista_year(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_DATA_CRIACAO_YEAR)));
                lista_compras.setData_criacao_lista_month(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_DATA_CRIACAO_MONTH)));
                lista_compras.setData_criacao_lista_day(cursor.getString(cursor.getColumnIndex(DatabaseContract.Listas.COL_DATA_CRIACAO_DAY)));
                lista_compras.setEstado(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Listas.COL_ESTADO_LISTA)));
                // Adding contact to list
                comprasLista.add(lista_compras);
            } while (cursor.moveToNext());
        }

        return comprasLista;
    }
}