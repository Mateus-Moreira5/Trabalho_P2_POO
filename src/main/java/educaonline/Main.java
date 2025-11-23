package main.java.educaonline;
import main.java.educaonline.menus.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA EDUCACIONAL ONLINE ===");
        System.out.println("Inicializando sistema...");
        
        MenuPrincipal menu = new MenuPrincipal();
        menu.exibir();
        
        System.out.println("Sistema finalizado.");

    }
}
