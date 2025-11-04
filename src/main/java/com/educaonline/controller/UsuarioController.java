package main.java.com.educaonline.controller;

import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.util.DatabaseUtil;

public class UsuarioController {
    
    public static Usuario fazerLogin(String email, String senha) {
        // Para teste, aceita qualquer login com senha "123456"
        if (senha.equals("123456")) {
            // Busca usuário pelo email
            for (Usuario usuario : DatabaseUtil.getUsuarios()) {
                if (usuario.getEmail().equals(email)) {
                    return usuario;
                }
            }
        }
        return null;
    }
}