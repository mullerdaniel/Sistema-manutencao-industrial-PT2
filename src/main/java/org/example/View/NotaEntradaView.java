package org.example.View;

import org.example.Model.Fornecedor;
import org.example.Model.Material;
import org.example.Model.NotaEntrada;
import org.example.Model.NotaEntradaItem;
import org.example.Service.FornecedorService;
import org.example.Service.MaterialService;
import org.example.Service.NotaEntradaService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NotaEntradaView {
    Scanner input = new Scanner(System.in);

    FornecedorService fornecedorService = new FornecedorService();
    MaterialService materialService = new MaterialService();
    NotaEntradaService notaEntradaService = new NotaEntradaService();


    // REGISTRAR NOTA DE ENTRADA
    public void registrarNotaEntrada() {
        System.out.println("\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃> Registrar Nota de Entrada <┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

        int idFornecedor = 0;
        Fornecedor fornecedor = null;
        List<NotaEntradaItem> itensNota = new ArrayList<>();
        List<Integer> materiaisJaAdicionados = new ArrayList<>();

        try {
            List<Fornecedor> fornecedores = fornecedorService.listarFornecedores();
            if (fornecedores.isEmpty()) {
                System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃> Não há fornecedores cadastrados. Operação cancelada. <┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                return;
            }

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Fornecedores disponíveis <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            for (Fornecedor f : fornecedores) {
                System.out.println("ID: " + f.getId() + ", Nome: " + f.getNome());
            }

            while (fornecedor == null) {
                System.out.print("\nSelecione o ID do Fornecedor: ");
                if (input.hasNextInt()) {
                    idFornecedor = input.nextInt();
                    input.nextLine();
                    fornecedor = fornecedorService.buscarFornecedorPorId(idFornecedor);
                    if (fornecedor == null) {
                        System.out.println("Erro. Fornecedor com ID " + idFornecedor + " não encontrado. Tente novamente.");
                    }
                } else {
                    System.out.println("Erro. Entrada inválida para o ID. Tente novamente.");
                    input.nextLine();
                }
            }
            System.out.println("Fornecedor selecionado: " + fornecedor.getNome());

        } catch (SQLException e) {
            System.out.println("Erro ao buscar fornecedores: " + e.getMessage());
            return;
        }

        LocalDateTime dataEntrada = LocalDateTime.now();


        boolean adicionarMais = true;

        while (adicionarMais) {
            int idMaterial = 0;
            double quantidade = 0.0;
            Material materialSelecionado = null;

            try {
                List<Material> materiaisDisponiveis = materialService.listarMateriais();

                if (materiaisDisponiveis.isEmpty() && itensNota.isEmpty()) {
                    System.out.println("\nNão há materiais cadastrados. Adicione materiais primeiro.");
                    break;
                }

                System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃> Materiais disponíveis para adicionar <┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");
                boolean encontrouMaterialParaListar = false;

                for (Material material : materiaisDisponiveis) {
                    if (!materiaisJaAdicionados.contains(material.getId())) {
                        System.out.printf("ID: %d, Nome: %s, Unidade: %s%n", material.getId(), material.getNome(), material.getUnidade());
                        encontrouMaterialParaListar = true;
                    }
                }

                if (!encontrouMaterialParaListar && !itensNota.isEmpty()) {
                    System.out.println("\nTodos os materiais cadastrados já foram adicionados a esta nota.");
                    break;
                } else if (!encontrouMaterialParaListar) {
                    System.out.println("\nNão há materiais disponíveis para associação.");
                    break;
                }

                System.out.print("\n\nSelecione qual material você deseja adicionar (ou 0 para finalizar): ");

                if (input.hasNextInt()) {
                    idMaterial = input.nextInt();
                    input.nextLine();
                } else {
                    System.out.println("Entrada inválida. Finalizando adição.");
                    input.nextLine();
                    break;
                }

                if (idMaterial == 0) {
                    adicionarMais = false;
                    continue;
                }

                int finalIdMaterial = idMaterial;
                materialSelecionado = materiaisDisponiveis.stream()
                        .filter(m -> m.getId() == finalIdMaterial)
                        .findFirst()
                        .orElse(null);

                if (materialSelecionado == null) {
                    System.out.println("Material com ID " + idMaterial + " não encontrado. Tente novamente.");
                    continue;
                }

                if (materiaisJaAdicionados.contains(idMaterial)) {
                    System.out.println("Material " + materialSelecionado.getNome() + " já foi adicionado a esta nota. Escolha outro.");
                    continue;
                }

                while (quantidade <= 0) {
                    System.out.print("Digite a quantidade de entrada para " + materialSelecionado.getNome() + ": ");
                    if (input.hasNextDouble()) {
                        quantidade = input.nextDouble();
                        input.nextLine();
                        if (quantidade <= 0) {
                            System.out.println("Erro. A quantidade deve ser maior que zero.");
                        }
                    } else {
                        System.out.println("Erro. Entrada inválida para a quantidade. Tente novamente.");
                        input.nextLine();
                        quantidade = 0;
                    }
                }

                itensNota.add(new NotaEntradaItem(idMaterial, quantidade));
                materiaisJaAdicionados.add(idMaterial);
                System.out.println("Material " + materialSelecionado.getNome() + " (" + quantidade + " " + materialSelecionado.getUnidade() + ") adicionado à nota.");

            } catch (SQLException e) {
                System.out.println("Erro ao listar ou buscar materiais: " + e.getMessage());
                adicionarMais = false;
            }
        }

        if (itensNota.isEmpty()) {
            System.out.println("\nNenhum material foi adicionado. Registro da nota cancelado.");
            return;
        }

        try {
            NotaEntrada nota = new NotaEntrada(idFornecedor, dataEntrada, itensNota);
            notaEntradaService.registrarNotaEntrada(nota);
            System.out.println("\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Nota de entrada registrada com sucesso! <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        } catch (Exception e) {
            System.out.println("\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃> Erro ao registrar a nota de entrada: " + e.getMessage() + " <┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        }
    }
}
