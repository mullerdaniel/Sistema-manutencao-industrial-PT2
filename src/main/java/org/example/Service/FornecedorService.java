package org.example.Service;

import org.example.Dao.FornecedorDAO;
import org.example.Model.Fornecedor;
import java.sql.SQLException;
import java.util.List;

public class FornecedorService {
    static FornecedorDAO fornecedorDAO = new FornecedorDAO();


    // CADASTRAR FORNECEDOR
    public static void cadastrarFornecedor(Fornecedor fornecedor) {
        try {
            fornecedorDAO.cadastrarFornecedor(fornecedor);
            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Fornecedor cadastrado com sucesso! <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        } catch (SQLException e) {
            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Erro ao cadastrar Fornecedor <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
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


    // LISTAR TODOS FORNECEDORES
    public List<Fornecedor> listarFornecedores() throws SQLException {
        return fornecedorDAO.listarFornecedores();
    }

    public Fornecedor buscarFornecedorPorId(int idFornecedor) throws SQLException {
        return fornecedorDAO.buscarFornecedorPorId(idFornecedor);
    }
}
