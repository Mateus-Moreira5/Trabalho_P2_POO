package main.java.com.educaonline;
import main.java.com.educaonline.util.DatabaseUtil;
import main.java.com.educaonline.view.LoginFrame;
import javax.swing.*;

public class Main {
    private static void configurarLookAndFell(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Erro para definir a interface.");
        }
    }
    private static void iniciarInterfaceGrafica() {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            System.out.println("🖥️  Tela de login aberta");
        });
    }
    public static void main(String[] args) {
        System.out.println("Iniciando EducaOnline...");
        configurarLookAndFell();
        DatabaseUtil.inicializarDadosExemplo();
        iniciarInterfaceGrafica();
    }
}
