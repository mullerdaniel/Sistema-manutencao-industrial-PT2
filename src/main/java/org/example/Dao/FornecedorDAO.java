package org.example.Dao;

import org.example.Conexao;
import org.example.Model.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


    // LISTAR FORNECEDORES
    public List<Fornecedor> listarFornecedores() throws SQLException {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT id, nome, cnpj FROM Fornecedor";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Fornecedor fornecedor = new Fornecedor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cnpj")
                        );
                fornecedores.add(fornecedor);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fornecedores;
    }


    // BUSCAR FORNECEDOR POR ID
    public Fornecedor buscarFornecedorPorId(int fornecedor) throws SQLException {
        String query = "SELECT * FROM Fornecedor WHERE id = ?";
        Fornecedor fornecedorEncontrado = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, fornecedor);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    fornecedorEncontrado = new Fornecedor();

                    fornecedorEncontrado.setId(rs.getInt("id"));
                    fornecedorEncontrado.setNome(rs.getString("nome"));
                    fornecedorEncontrado.setCnpj(rs.getString("cnpj"));
                }
            }
        }
        return fornecedorEncontrado;
    }


}

