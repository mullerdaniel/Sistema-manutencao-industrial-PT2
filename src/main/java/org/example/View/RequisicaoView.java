package org.example.View;

import org.example.Dao.MaterialDAO;
import org.example.Dao.RequisicaoDAO;
import org.example.Model.Requisicao;
import org.example.Model.RequisicaoItem;
import org.example.Model.Material;
import org.example.Service.RequisicaoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequisicaoView {
    Scanner input = new Scanner(System.in);

    MenuView menuView = new MenuView();
    RequisicaoService requisicaoService = new RequisicaoService();

    public void CriarRequisicaoMaterial() throws SQLException {

        menuView.MenuCriarRequisicaoMaterial();
        int opcao = input.nextInt();
        input.nextLine();

        switch (opcao) {

            case 1: {
                System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃> Cadastro de Novo Setor <┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.print("Digite o nome do novo Setor: ");
                String nomeSetor = input.nextLine();

                try {
                    requisicaoService.cadastrarSetor(nomeSetor);

                } catch (Exception e) {
                    System.out.println("\nErro ao tentar cadastrar o Setor: " + e.getMessage());
                }
                break;
            }

            case 2: {
                criarRequisicaoCompleta();
                break;
            }

            case 0: {
                break;
            }
        }
    }

    public void criarRequisicaoCompleta() {
        System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃> Nova Requisição de Material <┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        String setorRequisitante = "";
        try {
            List<String> setores = RequisicaoDAO.listarSetores();
            if (setores.isEmpty()) {
                System.out.println("\nNão há setores cadastrados. Cadastre um setor primeiro (Opção 1).");
                return;
            }

            System.out.println("\nSetores disponíveis:");
            for (int i = 0; i < setores.size(); i++) {
                System.out.printf("   %d - %s\n", (i + 1), setores.get(i));
            }

            int indiceSetor = -1;
            while (indiceSetor < 1 || indiceSetor > setores.size()) {
                System.out.print("\nDigite o número do Setor Requisitante: ");
                if (input.hasNextInt()) {
                    indiceSetor = input.nextInt();
                } else {
                    System.out.println("\nEntrada inválida. Digite um número.");
                    input.next();
                }
            }
            input.nextLine();
            setorRequisitante = setores.get(indiceSetor - 1);
            System.out.println("\nSetor selecionado: " + setorRequisitante);


            List<RequisicaoItem> itensRequisicao = new ArrayList<>();
            List<Material> materiaisDisponiveis = MaterialDAO.listarMateriaisDisponiveis();

            if (materiaisDisponiveis.isEmpty()) {
                System.out.println("\nNão há materiais disponíveis para requisição.");
                return;
            }

            boolean continuarAdicionando = true;
            while (continuarAdicionando) {

                if (materiaisDisponiveis.isEmpty()) {
                    System.out.println("\nTodos os materiais disponíveis foram adicionados à requisição.");
                    break;
                }

                System.out.println("\nMateriais disponíveis (Estoque > 0):");
                for (int i = 0; i < materiaisDisponiveis.size(); i++) {
                    Material material = materiaisDisponiveis.get(i);
                    System.out.printf("   %d - %s (Estoque: %.2f %s)\n", (i + 1), material.getNome(), material.getEstoque(), material.getUnidade());
                }

                System.out.print("\nDigite o número do Material (ou 0 para finalizar a requisição): ");
                int indiceMaterial = input.nextInt();
                input.nextLine();

                if (indiceMaterial == 0) {
                    continuarAdicionando = false;
                    break;
                }

                if (indiceMaterial < 1 || indiceMaterial > materiaisDisponiveis.size()) {
                    System.out.println("\nOpção inválida. Tente novamente.");
                    continue;
                }

                Material materialSelecionado = materiaisDisponiveis.get(indiceMaterial - 1);

                double quantidadeSolicitada = -1;
                while (quantidadeSolicitada <= 0) {
                    System.out.printf("\nDigite a quantidade de %s que deseja requisitar (Max: %.2f): ", materialSelecionado.getNome(), materialSelecionado.getEstoque());
                    String linha = input.nextLine();

                    try {
                        quantidadeSolicitada = Double.parseDouble(linha.replace(",", "."));

                        if (quantidadeSolicitada <= 0) {
                            System.out.println("A quantidade deve ser positiva e maior que zero.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Digite um valor numérico.");
                        quantidadeSolicitada = -1;
                    }
                }

                if (quantidadeSolicitada > materialSelecionado.getEstoque()) {
                    System.out.println("\nErro A quantidade solicitada (%.2f) ultrapassa o estoque disponível (%.2f). Tente novamente.");
                    continue;
                }

                RequisicaoItem item = new RequisicaoItem(materialSelecionado.getId(), quantidadeSolicitada);
                itensRequisicao.add(item);

                materiaisDisponiveis.remove(materialSelecionado);

                System.out.printf("\nItem '%s' (%.2f) adicionado à requisição.\n", materialSelecionado.getNome(), quantidadeSolicitada);
            }

            if (itensRequisicao.isEmpty()) {
                System.out.println("\nErro A requisição deve conter pelo menos um item. Cancelando operação.");
                return;
            }

            String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Requisicao novaRequisicao = new Requisicao(setorRequisitante, dataAtual, "PENDENTE");

            requisicaoService.criarRequisicaoCompleta(novaRequisicao, itensRequisicao);

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃Requisição de material criado com sucesso!    ┃");
            System.out.printf ("┃   ID Requisição: %d\n", novaRequisicao.getId());
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        } catch (SQLException e) {
            System.out.println("\nErro ao criar requisição! Detalhes: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("\nErro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // ATENDER REQUISIÇÕES
    public void atenderRequisicao() {
        try {
            List<Requisicao> requisicoesPendentes = RequisicaoDAO.listarRequisicoesComStatusPendente();

            if (requisicoesPendentes.isEmpty()) {
                System.out.println("\nNão há requisições com status PENDENTE para atender.");
                return;
            }

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Requisições Pendentes Disponíveis     <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            for (int i = 0; i < requisicoesPendentes.size(); i++) {
                Requisicao requisicao = requisicoesPendentes.get(i);
                System.out.printf("   %d) ID: %d | Setor: %s | Data: %s\n",
                        (i + 1),
                        requisicao.getId(),
                        requisicao.getSetor(),
                        requisicao.getDataSolicitacao()
                );
            }

            System.out.print("\nDigite o número da Requisição a ser Atendida (ou 0 para cancelar): ");
            int indiceRequisicao = input.nextInt();
            input.nextLine();

            if (indiceRequisicao == 0) {
                System.out.println("Operação de atendimento cancelada.");
                return;
            }

            if (indiceRequisicao < 1 || indiceRequisicao > requisicoesPendentes.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Requisicao requisicaoSelecionada = requisicoesPendentes.get(indiceRequisicao - 1);
            int idRequisicao = requisicaoSelecionada.getId();

            System.out.printf("\nConfirma o atendimento da Requisição ID: %d, Setor: %s? (S/N): ", idRequisicao, requisicaoSelecionada.getSetor());
            String confirmacao = input.nextLine().trim().toUpperCase();

            if (!confirmacao.equals("S")) {
                System.out.println("Atendimento cancelado pelo usuário.");
                return;
            }

            requisicaoService.atenderRequisicao(idRequisicao);

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.printf("┃Requisição ID %d atendida com sucesso!    ┃\n", idRequisicao);
            System.out.println("┃Estoque dos materiais atualizado.           ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        } catch (Exception e) {
            System.out.println("\nErro Não foi possível atender a requisição.");
            System.out.println("Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void cancelarRequisicao() {
        try {
            List<Requisicao> requisicoesPendentes = requisicaoService.listarRequisicoesPendentes();

            if (requisicoesPendentes.isEmpty()) {
                System.out.println("\n⚠Não há requisições com status PENDENTE para cancelar.");
                return;
            }

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Requisições Pendentes Disponíveis     <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            for (int i = 0; i < requisicoesPendentes.size(); i++) {
                Requisicao requisicao = requisicoesPendentes.get(i);
                System.out.printf("   %d) ID: %d | Setor: %s | Data: %s\n",
                        (i + 1),
                        requisicao.getId(),
                        requisicao.getSetor(),
                        requisicao.getDataSolicitacao()
                );
            }

            System.out.print("\nDigite o número da Requisição a ser Cancelada (ou 0 para sair): ");
            int indiceRequisicao = input.nextInt();
            input.nextLine();

            if (indiceRequisicao == 0) {
                System.out.println("Operação de cancelamento finalizada.");
                return;
            }

            if (indiceRequisicao < 1 || indiceRequisicao > requisicoesPendentes.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Requisicao requisicaoSelecionada = requisicoesPendentes.get(indiceRequisicao - 1);
            int idRequisicao = requisicaoSelecionada.getId();

            System.out.printf("\nConfirma o CANCELAMENTO da Requisição ID: %d, Setor: %s? (S/N): ", idRequisicao, requisicaoSelecionada.getSetor());
            String confirmacao = input.nextLine().trim().toUpperCase();

            if (!confirmacao.equals("S")) {
                System.out.println("Cancelamento abortado pelo usuário.");
                return;
            }

            requisicaoService.cancelarRequisicao(idRequisicao);

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.printf("┃Requisição ID %d cancelada com sucesso!      ┃\n", idRequisicao);
            System.out.println("┃O status da requisição foi alterado.        ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        } catch (Exception e) {
            System.out.println("\nErro Não foi possível cancelar a requisição.");
            System.out.println("Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}