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


    // METODO PARA LISTAR ORDENS MANUTENÇÃO COM STATUS DEFINIDO COMO PENDENTE
    public static List<Requisicao> listarRequisicoesComStatusPendente() throws SQLException {
        return RequisicaoDAO.listarRequisicoesComStatusPendente();
    }


    public List<Requisicao> listarRequisicoesPendentes() throws SQLException {
        return RequisicaoDAO.listarRequisicoesComStatusPendente();
    }


    // ATENDER REQUISIÇÃO
    public void atenderRequisicao(int idRequisicao) throws Exception {

        try {
            List<RequisicaoItem> itens = RequisicaoDAO.buscarItensRequisicao(idRequisicao);

            if (itens.isEmpty()) {
                throw new Exception("Requisição não contém itens ou não existe.");
            }

            for (RequisicaoItem item : itens) {
                MaterialDAO.subtrairEstoque(item.getIdMaterial(), item.getQuantidade());
            }

            RequisicaoDAO.atualizarStatusRequisicao(idRequisicao, "ATENDIDA");

        } catch (SQLException e) {
            throw new Exception("Falha no atendimento de requisição (Verifique se o estoque é suficiente). Detalhes: " + e.getMessage(), e);

        } catch (Exception e) {
            throw e;
        }
    }


    // NOVO METODO CANCELAR REQUISIÇÃO
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