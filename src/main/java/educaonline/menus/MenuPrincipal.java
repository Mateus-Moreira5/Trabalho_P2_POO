package main.java.educaonline.menus;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner = new Scanner(System.in);
    
    public void exibir() {
        int opcao;
        do {
            System.out.println("\n=== EDUCACIONAL ONLINE ===");
            System.out.println("1. Área do Aluno");
            System.out.println("2. Administração");
            System.out.println("3. Relatórios");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer
            
            switch (opcao) {
                case 1:
                    new MenuAluno().exibir();
                    break;
                case 2:
                    new MenuAdmin().exibir();
                    break;
                case 3:
                    new MenuRelatorios().exibir();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}