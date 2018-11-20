package com.example.ipca02.pantry.Models;

import com.example.ipca02.pantry.Database.DatabaseContract;

/**
 * Created by IPCA02 on 01-06-2017.
 */

public class Lista_Produtos_Adicionados {

    private int id_produtos_lista;
    private int lista_id;
    private int produto_id;
    private String nome_produto;
    private String quantidade;
    private String supermercado_id;
    private String preco;
    private int comprado;

    public Lista_Produtos_Adicionados(int lista_id, int produto_id, String nome_produto, String quantidade, String supermercado_id, String preco, int comprado) {
        this.lista_id = lista_id;
        this.produto_id = produto_id;
        this.nome_produto = nome_produto;
        this.quantidade = quantidade;
        this.supermercado_id = supermercado_id;
        this.preco = preco;
        this.comprado = comprado;
    }

    public Lista_Produtos_Adicionados() {
    }

    public int getId_produtos_lista() {
        return id_produtos_lista;
    }

    public void setId_produtos_lista(int id_produtos_lista) {
        this.id_produtos_lista = id_produtos_lista;
    }

    public int getLista_id() {
        return lista_id;
    }

    public void setLista_id(int lista_id) {
        this.lista_id = lista_id;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getSupermercado_id() {
        return supermercado_id;
    }

    public void setSupermercado_id(String supermercado_id) {
        this.supermercado_id = supermercado_id;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public int getComprado() {
        return comprado;
    }

    public void setComprado(int comprado) {
        this.comprado = comprado;
    }
}
