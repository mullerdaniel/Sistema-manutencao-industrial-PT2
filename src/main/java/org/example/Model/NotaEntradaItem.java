package org.example.Model;

public class NotaEntradaItem {

    private int idNotaEntrega;
    private int idMaterial;
    private double quantidade;


    // CONSTRUTOR
    public NotaEntradaItem(int idNotaEntrega, double quantidade) {
        this.idNotaEntrega = idNotaEntrega;
        this.idMaterial = idMaterial;
        this.quantidade = quantidade;
    }


    // GETs e SETs
    public int getIdNotaEntrega() {
        return idNotaEntrega;
    }

    public void setIdNotaEntrega(int idNotaEntrega) {
        this.idNotaEntrega = idNotaEntrega;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
