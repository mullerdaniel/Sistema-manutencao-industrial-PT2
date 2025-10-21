package org.example.Dao;

import org.example.Conexao;
import org.example.Model.Fornecedor;
import org.example.Model.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {


    // CADASTRAR MATERIAL
    public void cadastrarMaterial(Material material) throws SQLException {
        String query = "INSERT INTO Material (nome, unidade, estoque) VALUES (?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, material.getNome());
            stmt.setString(2, material.getUnidade());
            stmt.setDouble(3, material.getEstoque());
            stmt.execute();

        }
    }


    // LISTAR MATERIAIS
    public List<Material> listarMateriais() throws SQLException {
        List<Material> materiais = new ArrayList<>();
        String sql = "SELECT id, nome, unidade, estoque FROM Material";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("unidade"),
                        rs.getDouble("estoque")
                );
                materiais.add(material);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return materiais;
    }


    // METODO PARA LISTAR MATERIAIS COM ESTOQUE MAIOR QUE ZERO
    public static List<Material> listarMateriaisDisponiveis() throws SQLException {
        String query = "SELECT id, nome, unidade, estoque FROM Material WHERE estoque > 0 ORDER BY nome";
        List<Material> materiais = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("unidade"),
                        rs.getDouble("estoque")
                );
                materiais.add(material);
            }
        }
        return materiais;
    }
}


