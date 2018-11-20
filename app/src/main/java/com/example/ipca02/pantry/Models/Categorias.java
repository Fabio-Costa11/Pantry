package com.example.ipca02.pantry.Models;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class Categorias {

    private int categorias_id;
    private String categorias_nome;

    public Categorias() {

    }

    public Categorias(String categorias_nome) {
        this.categorias_nome = categorias_nome;
    }

    public int getCategorias_id() {
        return categorias_id;
    }

    public void setCategorias_id(int categorias_id) {
        this.categorias_id = categorias_id;
    }

    public String getCategorias_nome() {
        return categorias_nome;
    }

    public void setCategorias_nome(String categorias_nome) {
        this.categorias_nome = categorias_nome;
    }
}
