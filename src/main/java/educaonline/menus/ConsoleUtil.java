package main.java.educaonline.menus;
public class ConsoleUtil {
    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Linux, Mac, Unix
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Fallback: imprimir várias linhas em branco
            System.out.println("\n".repeat(50));
        }
    }
}