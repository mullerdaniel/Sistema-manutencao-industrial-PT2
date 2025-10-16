package org.example.Dao;

import org.example.Conexao;
import org.example.Model.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    }
