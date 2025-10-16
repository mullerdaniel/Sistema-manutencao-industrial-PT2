package org.example.View;

import org.example.Model.Fornecedor;
import org.example.Model.Material;
import org.example.Service.FornecedorService;
import org.example.Service.MaterialService;

import java.util.Scanner;

public class MaterialView {
    static Scanner input = new Scanner(System.in);
    MaterialService materialService = new MaterialService();


    public void cadastrarMaterial() {
        System.out.println("\n\n--- Cadastrar Material ---\n");

        System.out.println("Nome: ");
        String nome = input.nextLine();

        if (nome.isEmpty()) {
            System.out.println("Erro. O nome do material não pode ser vazio.");
            return;
        }

        System.out.println("Unidade (KG, M): ");
        String unidade = input.nextLine();


        Double estoque = 0.0;

        while (estoque <= 0.0) {
            System.out.println("Estoque: ");
            estoque = input.nextDouble();

            if (estoque <= 0.0) {
                System.out.println("\nErro. O estoque está menor ou igual a 0. Tente Novamento!\n");
                continue;
            }
        }

        Material material = new Material(nome, unidade, estoque);

        try {
            materialService.cadastrarMaterial(material);

        } catch (Exception e) {
            System.out.println("\nErro ao tentar cadastrar o Material: " + e.getMessage());
        }




    }
}
