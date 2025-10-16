package org.example.Model;

public class NotaEntrada {

    private int id;
    private int idFornecedor;
    private String dataEntrega;


    // METODO CONSTRUTOR COM ID E SEM ID
    public NotaEntrada(int id, int idFornecedor, String dataEntrega) {
        this.id = id;
        this.idFornecedor = idFornecedor;
        this.dataEntrega = dataEntrega;
    }

    public NotaEntrada(int idFornecedor, String dataEntrega) {
        this.idFornecedor = idFornecedor;
        this.dataEntrega = dataEntrega;
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

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
