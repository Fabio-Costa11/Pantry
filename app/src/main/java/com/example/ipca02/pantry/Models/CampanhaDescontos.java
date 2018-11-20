package com.example.ipca02.pantry.Models;

import com.example.ipca02.pantry.Database.DatabaseContract;

/**
 * Created by IPCA02 on 09-06-2017.
 */

public class CampanhaDescontos {

    private int id_campanha_descontos;
    private int supermercado_id;
    private String descricao;
    private int desconto;
    private int produto_id;

    public CampanhaDescontos() {
    }

    public CampanhaDescontos(int supermercado_id, String descricao, int desconto, int produto_id) {
        this.supermercado_id = supermercado_id;
        this.descricao = descricao;
        this.desconto = desconto;
        this.produto_id = produto_id;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public int getId_campanha_descontos() {
        return id_campanha_descontos;
    }

    public void setId_campanha_descontos(int id_campanha_descontos) {
        this.id_campanha_descontos = id_campanha_descontos;
    }

    public int getSupermercado_id() {
        return supermercado_id;
    }

    public void setSupermercado_id(int supermercado_id) {
        this.supermercado_id = supermercado_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }
}
