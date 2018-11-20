package com.example.ipca02.pantry.Models;

import com.example.ipca02.pantry.Database.DatabaseContract;

/**
 * Created by IPCA02 on 07-06-2017.
 */

public class Lista {
    private int id_lista;
    private String nome_lista;
    private String data_criacao_lista_year;
    private String data_criacao_lista_month;
    private String data_criacao_lista_day;
    private String data_conclusao_year;
    private String data_conclusao_month;
    private String data_conclusao_day;
    private int estado;

    public Lista() {

    }

    public Lista(String nome_lista, String data_criacao_lista_year, String data_criacao_lista_month, String data_criacao_lista_day, String data_conclusao_year, String data_conclusao_month, String data_conclusao_day, int estado) {
        this.data_criacao_lista_year = data_criacao_lista_year;
        this.data_criacao_lista_month = data_criacao_lista_month;
        this.data_criacao_lista_day = data_criacao_lista_day;
        this.nome_lista = nome_lista;
        this.data_conclusao_year = data_conclusao_year;
        this.data_conclusao_month = data_conclusao_month;
        this.data_conclusao_day = data_conclusao_day;
        this.estado = estado;
    }

    public int getId_lista() {
        return id_lista;
    }

    public void setId_lista(int id_lista) {
        this.id_lista = id_lista;
    }

    public String getNome_lista() {
        return nome_lista;
    }

    public void setNome_lista(String nome_lista) {
        this.nome_lista = nome_lista;
    }

    public String getData_criacao_lista_year() {
        return data_criacao_lista_year;
    }

    public void setData_criacao_lista_year(String data_criacao_lista_year) {
        this.data_criacao_lista_year = data_criacao_lista_year;
    }

    public String getData_criacao_lista_month() {
        return data_criacao_lista_month;
    }

    public void setData_criacao_lista_month(String data_criacao_lista_month) {
        this.data_criacao_lista_month = data_criacao_lista_month;
    }

    public String getData_criacao_lista_day() {
        return data_criacao_lista_day;
    }

    public void setData_criacao_lista_day(String data_criacao_lista_day) {
        this.data_criacao_lista_day = data_criacao_lista_day;
    }

    public String getData_conclusao_year() {
        return data_conclusao_year;
    }

    public void setData_conclusao_year(String data_conclusao_year) {
        this.data_conclusao_year = data_conclusao_year;
    }

    public String getData_conclusao_month() {
        return data_conclusao_month;
    }

    public void setData_conclusao_month(String data_conclusao_month) {
        this.data_conclusao_month = data_conclusao_month;
    }

    public String getData_conclusao_day() {
        return data_conclusao_day;
    }

    public void setData_conclusao_day(String data_conclusao_day) {
        this.data_conclusao_day = data_conclusao_day;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
