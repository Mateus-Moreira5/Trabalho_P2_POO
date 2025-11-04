package main.java.com.educaonline.util;

public class ValidacaoUtil {
    
    public static boolean validarEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 6;
    }
}