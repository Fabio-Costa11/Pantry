package com.example.ipca02.pantry.Models;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class Supermercado {
    private int id_supermercado;
    private String nome_supermercado;
    private String coordenadas;

    public Supermercado() {

    }

    public Supermercado(String nome_supermercado, String coordenadas) {
        this.nome_supermercado = nome_supermercado;
        this.coordenadas = coordenadas;
    }

    public int getId_supermercado() {
        return id_supermercado;
    }

    public void setId_supermercado(int id_supermercado) {
        this.id_supermercado = id_supermercado;
    }

    public String getNome_supermercado() {
        return nome_supermercado;
    }

    public void setNome_supermercado(String nome_supermercado) {
        this.nome_supermercado = nome_supermercado;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
