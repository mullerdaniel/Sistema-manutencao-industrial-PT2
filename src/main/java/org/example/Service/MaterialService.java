package org.example.Service;

import org.example.Dao.MaterialDAO;
import org.example.Model.Fornecedor;
import org.example.Model.Material;

import java.sql.SQLException;
import java.util.List;

public class MaterialService {
    static MaterialDAO materialDAO = new MaterialDAO();


    // CADASTRAR MATERIAL
    public void cadastrarMaterial(Material material) throws SQLException {
        try {
            materialDAO.cadastrarMaterial(material);
            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Material Cadastrado com Sucesso! <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        }catch (SQLException e) {
            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Erro ao Cadastrar o Material <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            e.printStackTrace();
        }
    }


    // LISTAR TODOS MATERIAIS
    public List<Material> listarMateriais() throws SQLException {
        return materialDAO.listarMateriais();
    }
}
