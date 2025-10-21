package org.example.Service;

import org.example.Dao.NotaEntradaDAO;
import org.example.Model.NotaEntrada;

import java.sql.SQLException;

public class NotaEntradaService {

    // Apenas NotaEntradaDAO é necessário para a operação
    private final NotaEntradaDAO notaEntradaDAO = new NotaEntradaDAO();

    public void registrarNotaEntrada(NotaEntrada notaEntrada) throws Exception {

        try {
            notaEntradaDAO.registrarNotaEntrada(notaEntrada);


        } catch (SQLException e) {
            System.err.println("\nErro ao registrar nota de entrada. Tente novamente.");
            e.printStackTrace();
            throw new Exception("Erro ao registrar nota de entrada!");
        }
    }
}