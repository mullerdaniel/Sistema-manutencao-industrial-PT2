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


    // LISTAR REQUISIÇÕES A SER ATENDIDA COM STATUS DEFINIDO COMO PENDENTE
    public static List<Requisicao> listarRequisicoesComStatusPendente() throws SQLException {
        List<Requisicao> requisicoes = new ArrayList<>();
        String sql = "SELECT id, setor, dataSolicitacao, status FROM Requisicao WHERE status = 'PENDENTE'";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Requisicao requisicao = new Requisicao(
                        rs.getInt("id"),
                        rs.getString("setor"),
                        rs.getString("dataSolicitacao"),
                        rs.getString("status")
                );
                requisicoes.add(requisicao);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return requisicoes;
    }


    // BUSCAR ITENS DE UMA REQUISIÇÃO
    public static List<RequisicaoItem> buscarItensRequisicao(int idRequisicao) throws SQLException {
        // Busca os itens e o nome do material para exibição no View/Service
        String query = "SELECT ri.idMaterial, ri.quantidade, m.nome FROM RequisicaoItem ri JOIN Material m ON ri.idMaterial = m.id WHERE ri.idRequisicao = ?";
        List<RequisicaoItem> itens = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRequisicao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RequisicaoItem item = new RequisicaoItem(
                            rs.getInt("idMaterial"),
                            rs.getDouble("quantidade")
                    );
                    itens.add(item);
                }
            }
        }
        return itens;
    }


    // ATUALIZAR STATUS DA REQUISIÇÃO
    public static void atualizarStatusRequisicao(int idRequisicao, String novoStatus) throws SQLException {
        String query = "UPDATE Requisicao SET status = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, idRequisicao);
            stmt.executeUpdate();
        }
    }


    // NOVO METODO CANCELAR REQUISICAO
    public void cancelarRequisicao(int idRequisicao) throws Exception {

        try {
            RequisicaoDAO.atualizarStatusRequisicao(idRequisicao, "CANCELADA");

        } catch (SQLException e) {
            throw new Exception("Falha ao tentar cancelar a requisição. Detalhes: " + e.getMessage(), e);

        } catch (Exception e) {
            throw e;
        }
    }
}