package main.java.educaonline.menus;

import java.util.Scanner;

public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);
    
    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                return value;
            } catch (Exception e) {
                System.out.println("Erro: Por favor, digite um número válido!");
                scanner.nextLine(); // Limpar buffer
            }
        }
    }
    
    public static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = scanner.nextDouble();
                scanner.nextLine(); // Limpar buffer
                return value;
            } catch (Exception e) {
                System.out.println("Erro: Por favor, digite um número válido!");
                scanner.nextLine(); // Limpar buffer
            }
        }
    }
    
    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
    
    public static boolean readBoolean(String message) {
        while (true) {
            System.out.print(message + " (s/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("s") || input.equals("sim") || input.equals("true") || input.equals("1")) {
                return true;
            } else if (input.equals("n") || input.equals("não") || input.equals("nao") || input.equals("false") || input.equals("0")) {
                return false;
            } else {
                System.out.println("Erro: Por favor, digite 's' para Sim ou 'n' para Não!");
            }
        }
    }
    
    // Método específico para perguntas SIM/NÃO mais simples
    public static boolean readSimNao(String message) {
        while (true) {
            System.out.print(message + " (S/N): ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("s") || input.equals("sim")) {
                return true;
            } else if (input.equals("n") || input.equals("não") || input.equals("nao")) {
                return false;
            } else {
                System.out.println("Erro: Por favor, digite 'S' para Sim ou 'N' para Não!");
            }
        }
    }
}