package org.example.View;

import java.util.Scanner;

public class MenuView {
    static Scanner input = new Scanner(System.in);

    FornecedorView fornecedorView = new FornecedorView();

    // METODO PARA MENU PRINCIPAL
    public void menuPrincipal() {

        System.out.println("\n\n----------------------------------------------------");
        System.out.println(">   Sistema de Gestão de Almoxarifado Industrial   <");
        System.out.println("----------------------------------------------------\n");
        System.out.println("1- Cadastrar Fornecedor");
        System.out.println("2- Cadastrar Material");
        System.out.println("3- Registrar Nota de Entrega");
        System.out.println("4- Criar Requisição de Material");
        System.out.println("5- Atender Requisição");
        System.out.println("6- Cancelar Requisição");
        System.out.println("\n0- Sair");
        int opcao = input.nextInt();

        switch (opcao) {

            case 1: {
                fornecedorView.cadastrarFornecedor();
                break;
            }

            case 2: {
                break;
            }

            case 3: {
                break;
            }

            case 4: {
                break;
            }

            case 5: {
                break;
            }

            case 6: {
                break;
            }

            case 0: {
                System.out.println("\n\nSaindo do Sistema...");
                break;
            }
        }

    }
}
