package org.example.Service;

import org.example.Dao.MaterialDAO;
import org.example.Model.Material;

import java.sql.SQLException;

public class MaterialService {
    static MaterialDAO materialDAO = new MaterialDAO();


    // CADASTRAR MATERIAL
    public void cadastrarMaterial(Material material) throws SQLException {
        try {
            materialDAO.cadastrarMaterial(material);
            System.out.println("\nMaterial Cadastrado com Sucesso!");

        }catch (SQLException e) {
            System.out.println("\nErro ao Cadastrar o Material");
            e.printStackTrace();
        }
    }
}
