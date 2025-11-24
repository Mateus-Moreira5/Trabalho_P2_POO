package main.java.educaonline.menus;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner = new Scanner(System.in);
    
    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.clearScreen();
            System.out.println("\n=== EDUCACIONAL ONLINE ===");
            System.out.println("1. Área do Aluno");
            System.out.println("2. Administração");
            System.out.println("3. Relatórios");
            System.out.println("0. Sair");
            
            opcao = InputUtil.readInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    ConsoleUtil.clearScreen();
                    new MenuAluno().exibir();
                    break;
                case 2:
                    ConsoleUtil.clearScreen();
                    new MenuAdmin().exibir();
                    break;
                case 3:
                    ConsoleUtil.clearScreen();
                    new MenuRelatorios().exibir();
                    break;
                case 0:
                    ConsoleUtil.clearScreen();
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("Pressione ENTER para continuar...");
                    scanner.nextLine();
            }
        } while (opcao != 0);
    }
}