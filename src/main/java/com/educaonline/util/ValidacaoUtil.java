package main.java.com.educaonline.util;

import java.util.regex.Pattern;

public final class ValidacaoUtil {

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern TELEFONE_PATTERN = 
        Pattern.compile("^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$");
    
    private static final Pattern CPF_PATTERN = 
        Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
    
    private static final Pattern APENAS_LETRAS_PATTERN = 
        Pattern.compile("^[a-zA-ZÀ-ÿ\\s]+$");
    
    private static final Pattern APENAS_NUMEROS_PATTERN = 
        Pattern.compile("^\\d+$");
    
    private static final Pattern SENHA_FORTE_PATTERN =
        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private ValidacaoUtil() {
        throw new IllegalStateException("Classe utilitária - não instanciar");
    }

    public static boolean validarEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 6;
    }

    public static boolean validarSenhaForte(String senha) {
        return senha != null && SENHA_FORTE_PATTERN.matcher(senha).matches();
    }

    public static boolean validarTelefone(String telefone) {
        return telefone != null && TELEFONE_PATTERN.matcher(telefone).matches();
    }

    public static boolean validarCPF(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }

            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) firstDigit = 0;
            
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) secondDigit = 0;
            
            return digits[9] == firstDigit && digits[10] == secondDigit;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarApenasLetras(String texto) {
        return texto != null && APENAS_LETRAS_PATTERN.matcher(texto).matches();
    }

    public static boolean validarApenasNumeros(String texto) {
        return texto != null && APENAS_NUMEROS_PATTERN.matcher(texto).matches();
    }

    public static boolean validarNaoVazio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarTamanho(String texto, int min, int max) {
        return texto != null && texto.length() >= min && texto.length() <= max;
    }

    public static boolean validarIntervalo(double valor, double min, double max) {
        return valor >= min && valor <= max;
    }

    public static boolean validarIntervalo(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }

    public static boolean validarPositivo(double valor) {
        return valor > 0;
    }

    public static boolean validarNaoNegativo(double valor) {
        return valor >= 0;
    }

    public static String removerNaoNumericos(String texto) {
        return texto != null ? texto.replaceAll("\\D", "") : "";
    }

    public static String formatarCPF(String cpf) {
        if (cpf == null) return "";
        
        cpf = removerNaoNumericos(cpf);
        if (cpf.length() != 11) return cpf;
        
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + 
               cpf.substring(6, 9) + "-" + cpf.substring(9);
    }

    public static String formatarTelefone(String telefone) {
        if (telefone == null) return "";
        
        telefone = removerNaoNumericos(telefone);
        if (telefone.length() == 11) {
            return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7);
        } else if (telefone.length() == 10) {
            return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-" + telefone.substring(6);
        }
        return telefone;
    }

    public static boolean validarNome(String nome) {
        return validarNaoVazio(nome) && 
               validarApenasLetras(nome) && 
               validarTamanho(nome, 2, 100);
    }

    public static boolean validarPreco(double preco) {
        return validarPositivo(preco) && 
               String.valueOf(preco).matches("^\\d+(\\.\\d{1,2})?$");
    }

    public static String getMensagemErro(String campo, String valor, String tipoValidacao) {
        switch (tipoValidacao) {
            case "email":
                return String.format("O campo '%s' deve conter um email válido", campo);
            case "senha":
                return String.format("O campo '%s' deve ter pelo menos 6 caracteres", campo);
            case "cpf":
                return String.format("O campo '%s' deve conter um CPF válido", campo);
            case "telefone":
                return String.format("O campo '%s' deve estar no formato (XX) XXXXX-XXXX", campo);
            case "obrigatorio":
                return String.format("O campo '%s' é obrigatório", campo);
            case "tamanho":
                return String.format("O campo '%s' deve ter entre %s caracteres", campo, valor);
            default:
                return String.format("O campo '%s' é inválido", campo);
        }
    }
}