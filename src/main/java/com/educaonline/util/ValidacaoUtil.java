package main.java.com.educaonline.util;

public class ValidacaoUtil {
    
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
    
    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 3;
    }
    
    public static boolean validarTelefone(String telefone) {
        if (telefone == null) return false;
        String digits = telefone.replaceAll("\\D", "");
        return digits.length() >= 10 && digits.length() <= 11;
    }
    
    public static boolean validarPreco(double preco) {
        return preco >= 0;
    }
    
    public static boolean validarVagas(int vagas) {
        return vagas > 0;
    }
    
    public static boolean validarDuracao(int duracaoHoras) {
        return duracaoHoras > 0;
    }
    
    public static boolean validarNome(String nome) {
        return nome != null && nome.trim().length() >= 2;
    }
    
    public static String formatarTelefone(String telefone) {
        if (telefone == null) return "";
        String digits = telefone.replaceAll("\\D", "");
        if (digits.length() == 11) {
            return String.format("(%s) %s-%s", 
                digits.substring(0, 2),
                digits.substring(2, 7),
                digits.substring(7));
        } else if (digits.length() == 10) {
            return String.format("(%s) %s-%s", 
                digits.substring(0, 2),
                digits.substring(2, 6),
                digits.substring(6));
        }
        return telefone;
    }
}