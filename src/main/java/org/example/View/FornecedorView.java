package org.example.View;

import org.example.Model.Fornecedor;
import org.example.Service.FornecedorService; // Certifique-se de ter a importação correta

import java.util.Scanner;

public class FornecedorView {
    private final Scanner input = new Scanner(System.in);
    private final FornecedorService fornecedorService = new FornecedorService();


    // CADASTRAR FORNECEDOR
    public void cadastrarFornecedor() {
        System.out.println("\n\n--- Cadastrar Fornecedor ---\n");

        System.out.println("Nome: ");
        String nome = input.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Erro. O nome do fornecedor não pode ser vazio.");
            return;
        }

        String cnpj = "";
        boolean cnpjValido = false;

        while (!cnpjValido) {
            System.out.println("CNPJ: ");
            cnpj = input.nextLine().trim();

            if (cnpj.isEmpty()) {
                System.out.println("\nErro. O CNPJ não pode ser vazio. Tente novamente.\n");
                continue;
            }

            if (fornecedorService.cnpjJaExiste(cnpj)) {
                System.out.println("\nErro. O CNPJ " + cnpj + " já está cadastrado.\n");

            } else {
                cnpjValido = true;
            }
        }

        Fornecedor fornecedor = new Fornecedor(nome, cnpj);
    }
}