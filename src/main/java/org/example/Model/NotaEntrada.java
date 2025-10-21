package org.example.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class NotaEntrada {

    private int id;
    private int idFornecedor;
    private LocalDateTime dataEntrada;


    // METODO CONSTRUTOR COM ID E SEM ID
    public NotaEntrada(int id, int idFornecedor, LocalDateTime dataEntrada) {
        this.id = id;
        this.idFornecedor = idFornecedor;
        this.dataEntrada = dataEntrada;
    }

    public NotaEntrada(int idFornecedor, LocalDateTime dataEntrada) {
        this.idFornecedor = idFornecedor;
        this.dataEntrada = dataEntrada;
    }

    public NotaEntrada(int idFornecedor, LocalDateTime dataEntrada, List<NotaEntradaItem> itensNota) {
        this.idFornecedor = idFornecedor;
        this.dataEntrada = dataEntrada;
    }


    // GETs e SETs
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrega) {
        this.dataEntrada = dataEntrada;
    }

    public List<NotaEntradaItem> getItens() {
        List<NotaEntradaItem> itens = List.of();
        return itens;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrada;
    }
}
