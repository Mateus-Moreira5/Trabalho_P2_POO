package main.java.com.educaonline.controller;

import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.util.DatabaseUtil;

public class UsuarioController {
    
    public static Usuario fazerLogin(String email, String senha) {
        return DatabaseUtil.validarLogin(email, senha);
    }
}