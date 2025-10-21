package org.example.Dao;

import org.example.Conexao;
import org.example.Model.Requisicao;
import org.example.Model.RequisicaoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequisicaoDAO {


    // LISTAR OS SETORES
    public static List<String> listarSetores() throws SQLException {
        String query = "SELECT DISTINCT setor FROM Requisicao ORDER BY setor";
        List<String> setores = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                setores.add(rs.getString("setor"));
            }
        }
        return setores;
    }


    // CADASTRAR REQUISIÇÃO
    public static void cadastrarRequisicao(Requisicao requisicao) throws SQLException {
        String query = "INSERT INTO Requisicao (setor, dataSolicitacao, status) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, requisicao.getSetor());
            stmt.setString(2, requisicao.getDataSolicitacao());
            stmt.setString(3, requisicao.getStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        requisicao.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // INSERIR ITENS DA REQUISIÇÃO
    public static void cadastrarRequisicaoItem(int idRequisicao, RequisicaoItem item) throws SQLException {
        String query = "INSERT INTO RequisicaoItem (idRequisicao, idMaterial, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRequisicao);
            stmt.setInt(2, item.getIdMaterial());
            stmt.setDouble(3, item.getQuantidade());
            stmt.executeUpdate();
        }
    }
}