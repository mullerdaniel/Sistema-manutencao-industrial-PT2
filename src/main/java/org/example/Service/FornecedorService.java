package org.example.Service;

import org.example.Dao.FornecedorDAO;
import org.example.Model.Fornecedor;
import java.sql.SQLException;

public class FornecedorService {
    static FornecedorDAO fornecedorDAO = new FornecedorDAO();


    // CADASTRAR FORNECEDOR
    public static void cadastrarFornecedor(Fornecedor fornecedor) {
        try {
            fornecedorDAO.cadastrarFornecedor(fornecedor);
            System.out.println("\nFornecedor cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("\nErro ao cadastrar Fornecedor");
            e.printStackTrace();
        }
    }


    // VERIFICADOR CASO CNPJ JA EXISTA
    public boolean cnpjJaExiste(String cnpj) {
        try {
            return fornecedorDAO.cnpjJaExiste(cnpj);

        } catch (Exception e) {
            System.err.println("Erro de serviço ao verificar CNPJ: " + e.getMessage());
            return false;
        }
    }

}
