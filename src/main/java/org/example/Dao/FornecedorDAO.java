package org.example.Dao;

import org.example.Conexao;
import org.example.Model.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class FornecedorDAO {


    // CADASTRAR FORNECEDOR
    public void cadastrarFornecedor(Fornecedor fornecedor) throws SQLException {
        String query = "INSERT INTO Fornecedor (nome, cnpj) VALUES (?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.execute();
        }
    }


    // VERIFICADOR CASO CNPJ JA EXISTA
    public boolean cnpjJaExiste(String cnpj) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Fornecedor WHERE cnpj = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


}

