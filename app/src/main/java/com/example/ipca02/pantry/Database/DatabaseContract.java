package com.example.ipca02.pantry.Database;

import android.provider.BaseColumns;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class DatabaseContract {

    public static abstract class Produtos implements BaseColumns {

        public static final String TB_NAME_PRODUTOS = Produtos.class.getSimpleName();
        public static final String COL_PRODUTO_ID = "_id";
        public static final String COL_NOME_PRODUTO = "Produto_Nome";
        public static final String COL_CATEGORIA_ID = "Categoria_ID";
        public static final String COL_PRECO = "Preco";
        public static final String COL_SUPERMERCADO_ID = "Supermercado_ID";
    }

    public static abstract class Listas implements BaseColumns {

        public static final String TB_NAME_LISTAS = Listas.class.getSimpleName();
        public static final String COL_LISTA_ID = "_id";
        public static final String COL_LISTA_NOME = "Lista_Nome";
        public static final String COL_DATA_CRIACAO_YEAR = "Data_Criacao_Year";
        public static final String COL_DATA_CRIACAO_MONTH = "Data_Criacao_Month";
        public static final String COL_DATA_CRIACAO_DAY = "Data_Criacao_Day";
        public static final String COL_DATA_CONCLUSAO_YEAR = "Data_Conclusao_Year";
        public static final String COL_DATA_CONCLUSAO_MONTH = "Data_Conclusao_Month";
        public static final String COL_DATA_CONCLUSAO_DAY = "Data_Conclusao_Day";
        public static final String COL_ESTADO_LISTA = "Estado_Lista";
    }

    public static abstract class Categorias implements BaseColumns {

        public static final String TB_NAME_CATEGORIAS = Categorias.class.getSimpleName();
        public static final String COL_CATEGORIA_ID = "_id";
        public static final String COL_CATEGORIA_NOME = "Categoria_Nome";
    }

    public static abstract class CampanhasDesconto implements BaseColumns {

        public static final String TB_NAME_CAMPANHASDESCONTO = CampanhasDesconto.class.getSimpleName();
        public static final String COL_CAMPANHAS_DESCONTO_ID = "_id";
        public static final String COL_PRODUTO_ID = "Produto_ID";
        public static final String COL_DESCRICAO = "Descricao";
        public static final String COL_DESCONTO = "Desconto";
        public static final String COL_SUPERMERCADO_ID = "Supermercado_ID";
    }

    public static abstract class ProdutosLista implements BaseColumns {

        public static final String TB_NAME_PRODUTOS_LISTA = ProdutosLista.class.getSimpleName();
        public static final String COL_ID_PRODUTOSLISTA = "_id";
        public static final String COL_LISTA_ID = "Lista_ID";
        public static final String COL_PRODUTO_ID = "Produto_ID";
        public static final String COL_QUANTIDADE = "Quantidade";
        public static final String COL_PRECO = "Preco";
        public static final String COL_SUPERMERCADO_ID = "Supermercado_ID";
        public static final String COL_COMPRADO = "Comprado";

    }

    public static abstract class Supermercado implements BaseColumns {

        public static final String TB_NAME_SUPERMERCADO = Supermercado.class.getSimpleName();
        public static final String COL_SUPERMERCADO_ID = "_id";
        public static final String COL_SUPERMERCADO_NOME = "Supermercado_Nome";
        public static final String COL_COORDENADAS = "Coordenadas";
    }
}
