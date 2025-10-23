package org.example.View;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuView {
    static Scanner input = new Scanner(System.in);

    // METODO PARA MENU PRINCIPAL
    public void menuPrincipal() throws SQLException {

        System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃>   Sistema de Gestão de Almoxarifado Industrial   <┃");
        System.out.println("┃━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┃");
        System.out.println("┃1- Cadastrar Fornecedor                             ┃");
        System.out.println("┃2- Cadastrar Material                               ┃");
        System.out.println("┃3- Registrar Nota de Entrega                        ┃");
        System.out.println("┃4- Criar Requisição de Material                     ┃");
        System.out.println("┃5- Atender Requisição                               ┃");
        System.out.println("┃6- Cancelar Requisição                              ┃");
        System.out.println("┃----------------------------------------------------┃");
        System.out.println("┃0- Sair                                             ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        int opcao = input.nextInt();

        switch (opcao) {

            case 1: {
                FornecedorView fornecedorView = new FornecedorView();
                fornecedorView.cadastrarFornecedor();
                break;
            }

            case 2: {
                MaterialView materialView = new MaterialView();
                materialView.cadastrarMaterial();
                break;
            }

            case 3: {
                NotaEntradaView notaEntregaView = new NotaEntradaView();
                notaEntregaView.registrarNotaEntrada();
                break;
            }

            case 4: {
                RequisicaoView requisicaoView = new RequisicaoView();
                requisicaoView.CriarRequisicaoMaterial();
                break;
            }

            case 5: {
                RequisicaoView requisicaoView = new RequisicaoView();
                requisicaoView.atenderRequisicao();
                break;
            }

            case 6: {
                RequisicaoView requisicaoView = new RequisicaoView();
                requisicaoView.cancelarRequisicao();
                break;
            }

            case 0: {
                System.out.println("\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃     Saindo do Sistema...    ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                break;
            }

            default: {
                System.out.println("\nOpção invalidade!");
                break;
            }
        }
    }


    // METODO PARA MENU Criar Requisição de Material
    public void MenuCriarRequisicaoMaterial() throws SQLException {
        System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃> Criação de Requisição de Material <┃");
        System.out.println("┃━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┃");
        System.out.println("┃1- Criar Setor                       ┃");
        System.out.println("┃2- Criar Requisição de Material      ┃");
        System.out.println("┃-------------------------------------┃");
        System.out.println("┃0- Sair                              ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

    }

}