package org.example.Model;

import java.util.Date;

public class Requisicao {

    private int id;
    private String setor;
    private String dataSolicitacao;
    private String status; // PENDENTE - ATENDIDA - CANCELADA


    // METODO CONSTRUTOR COM ID E SEM ID
    public Requisicao(int id, String setor, String dataSolicitacao, String status) {
        this.id = id;
        this.setor = setor;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public Requisicao(String setor, String dataSolicitacao, String status) {
        this.setor = setor;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }


    // GETs e SETs
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(String dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
