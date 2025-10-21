package org.example.Service;

import org.example.Dao.RequisicaoDAO;
import org.example.Dao.MaterialDAO;
import org.example.Model.Requisicao;
import org.example.Model.RequisicaoItem;
import org.example.Model.Material;
import org.example.Conexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RequisicaoService {


    public List<String> listarSetores() throws SQLException {
        return RequisicaoDAO.listarSetores();
    }


    public List<Material> listarMateriaisDisponiveis() throws SQLException {
        return MaterialDAO.listarMateriaisDisponiveis();
    }

    public void cadastrarSetor(String nomeSetor) {
        try {
            String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String statusPlaceholder = "PENDENTE";
            Requisicao requisicao = new Requisicao(nomeSetor, dataAtual, statusPlaceholder);

            RequisicaoDAO.cadastrarRequisicao(requisicao);

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Setor cadastrado com sucesso! <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        } catch (SQLException e) {
            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Erro ao cadastrar Setor <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            e.printStackTrace();
        }
    }

    // METODO CENTRAL CRIA A REQUISIÇÃO E SEUS ITENS EM TRANSAÇÃO
    public void criarRequisicaoCompleta(Requisicao requisicao, List<RequisicaoItem> itens) throws SQLException {
        Connection conn = null;
        try {
            conn = Conexao.conectar();

            RequisicaoDAO.cadastrarRequisicao(requisicao);
            int idRequisicaoGerado = requisicao.getId();

            for (RequisicaoItem item : itens) {
                RequisicaoDAO.cadastrarRequisicaoItem(idRequisicaoGerado, item);
            }

        } catch (SQLException e) {


        }
    }
}