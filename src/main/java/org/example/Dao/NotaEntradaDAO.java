package org.example.Dao;

import org.example.Conexao;
import org.example.Model.NotaEntrada;
import org.example.Model.NotaEntradaItem;

import java.sql.*;
import java.time.LocalDateTime;

public class NotaEntradaDAO {

    private final MaterialDAO materialDAO = new MaterialDAO();


    // REGISTRAR NOTA ENTRADA
    public void registrarNotaEntrada(NotaEntrada notaEntrada) throws SQLException {
        String queryNota = "INSERT INTO NotaEntrada (idFornecedor, dataEntrada) VALUES (?, ?)";
        String queryItem = "INSERT INTO NotaEntradaItem (idNotaEntrada, idMaterial, quantidade) VALUES (?, ?, ?)";
        String queryUpdateEstoque = "UPDATE Material SET estoque = estoque + ? WHERE id = ?";

        Connection conn = null;

        try {
            conn = Conexao.conectar();

            try (PreparedStatement stmt = conn.prepareStatement(queryNota, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, notaEntrada.getIdFornecedor());
                // Se dataEntrada for null, substitua por LocalDateTime.now()
                stmt.setTimestamp(2, java.sql.Timestamp.valueOf(notaEntrada.getDataEntrada() != null ? notaEntrada.getDataEntrada() : LocalDateTime.now()));
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idNotaEntrada = rs.getInt(1);
                        notaEntrada.setId(idNotaEntrada);

                        try (PreparedStatement stmtItem = conn.prepareStatement(queryItem);
                             PreparedStatement stmtUpdate = conn.prepareStatement(queryUpdateEstoque)) {

                            for (NotaEntradaItem item : notaEntrada.getItens()) {
                                stmtItem.setInt(1, idNotaEntrada);
                                stmtItem.setInt(2, item.getIdMaterial());
                                stmtItem.setDouble(3, item.getQuantidade());
                                stmtItem.addBatch();

                                stmtUpdate.setDouble(1, item.getQuantidade());
                                stmtUpdate.setInt(2, item.getIdMaterial());
                                stmtUpdate.addBatch();
                            }

                            stmtItem.executeBatch();
                            stmtUpdate.executeBatch();
                        }
                    } else {
                        throw new SQLException("Erro ao obter o ID da NotaEntrada.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}