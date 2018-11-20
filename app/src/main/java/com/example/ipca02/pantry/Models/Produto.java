package com.example.ipca02.pantry.Models;

import com.example.ipca02.pantry.Database.DatabaseContract;

/**
 * Created by IPCA02 on 07-06-2017.
 */

public class Produto {
    private int id_produto;
    private String nome_produto;
    private int categoria_produto;
    private String preco;
    private int supermercado_id;

    public Produto() {

    }

    public Produto(String nome_produto, int categoria_produto, String preco, int supermercado_id) {
        this.nome_produto = nome_produto;
        this.categoria_produto = categoria_produto;
        this.preco = preco;
        this.supermercado_id = supermercado_id;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public int getCategoria_produto() {
        return categoria_produto;
    }

    public void setCategoria_produto(int categoria_produto) {
        this.categoria_produto = categoria_produto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public int getSupermercado_id() {
        return supermercado_id;
    }

    public void setSupermercado_id(int supermercado_id) {
        this.supermercado_id = supermercado_id;
    }

}
