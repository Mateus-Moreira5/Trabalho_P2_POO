package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.controller.UsuarioController;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.AlunoVIP;
import main.java.com.educaonline.model.Professor;

public class LoginFrame extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> comboTipoUsuario;
    private JButton btnLogin;
    
    public LoginFrame() {
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setTitle("🎓 EducaOnline - Login");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titulo = new JLabel("EducaOnline", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(0, 100, 200));
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbc);
        
        JLabel subtitulo = new JLabel("Sistema de Ensino a Distância", JLabel.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 100, 100));
        gbc.gridy = 1;
        panel.add(subtitulo, gbc);
        
        gbc.gridy = 2;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; 
        gbc.gridy = 3; 
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel labelTipo = new JLabel("Tipo de Usuário:");
        labelTipo.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelTipo, gbc);
        
        gbc.gridx = 1; 
        gbc.gridy = 3; 
        gbc.anchor = GridBagConstraints.LINE_START;
        String[] tiposUsuario = {"Aluno", "Aluno VIP", "Professor"};
        comboTipoUsuario = new JComboBox<>(tiposUsuario);
        comboTipoUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(comboTipoUsuario, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 4; 
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelEmail, gbc);
        
        gbc.gridx = 1; 
        gbc.gridy = 4; 
        gbc.anchor = GridBagConstraints.LINE_START;
        campoEmail = new JTextField(20);
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(campoEmail, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 5; 
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelSenha, gbc);
        
        gbc.gridx = 1; 
        gbc.gridy = 5; 
        gbc.anchor = GridBagConstraints.LINE_START;
        campoSenha = new JPasswordField(20);
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(campoSenha, gbc);
        
        gbc.gridy = 6;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 7; 
        gbc.gridwidth = 2;
        btnLogin = new JButton("Entrar no Sistema");
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(200, 40));
        panel.add(btnLogin, gbc);
        
        campoEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        
        campoSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        
        add(panel);
        pack();
    }
    
    private void fazerLogin() {
        String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());
        
        if (email.isEmpty()) {
            mostrarErro("Por favor, informe seu email!");
            campoEmail.requestFocus();
            return;
        }
        
        if (senha.isEmpty()) {
            mostrarErro("Por favor, informe sua senha!");
            campoSenha.requestFocus();
            return;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            mostrarErro("Por favor, informe um email válido!");
            campoEmail.requestFocus();
            campoEmail.selectAll();
            return;
        }
        
        Usuario usuario = UsuarioController.fazerLogin(email, senha);
        
        if (usuario != null) {
            boolean tipoCorreto = validarTipoUsuario(usuario, tipoUsuario);
            
            if (!tipoCorreto) {
                String tipoReal = obterTipoRealUsuario(usuario);
                mostrarErro("Tipo de usuário incorreto! Você é um " + tipoReal + ".");
                return;
            }
            
            String mensagemSucesso = "Login realizado com sucesso!\n";
            if (usuario instanceof AlunoVIP) {
                mensagemSucesso += "Bem-vindo, Aluno VIP!";
            } else if (usuario instanceof Professor) {
                mensagemSucesso += "Bem-vindo, Professor!";
            } else {
                mensagemSucesso += "Bem-vindo!";
            }
            
            mostrarSucesso(mensagemSucesso);
            
            if (usuario instanceof main.java.com.educaonline.model.Aluno) {
                new DashboardAlunoFrame(usuario).setVisible(true);
            } else if (usuario instanceof Professor) {
                new DashboardProfessorFrame(usuario).setVisible(true);
            } else {
                new main.java.com.educaonline.view.DashboardFrame(usuario).setVisible(true);
            }
            dispose();
        } else {
            mostrarErro("Email ou senha incorretos!");
        }
    }
    
    private boolean validarTipoUsuario(Usuario usuario, String tipoSelecionado) {
        if (usuario instanceof AlunoVIP && "Aluno VIP".equals(tipoSelecionado)) {
            return true;
        } else if (usuario instanceof Professor && "Professor".equals(tipoSelecionado)) {
            return true;
        } else if (usuario instanceof main.java.com.educaonline.model.Aluno && "Aluno".equals(tipoSelecionado)) {
            return true;
        }
        return false;
    }
    
    private String obterTipoRealUsuario(Usuario usuario) {
        if (usuario instanceof AlunoVIP) {
            return "Aluno VIP";
        } else if (usuario instanceof Professor) {
            return "Professor";
        } else if (usuario instanceof main.java.com.educaonline.model.Aluno) {
            return "Aluno";
        }
        return "Usuário";
    }
    
    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, 
            mensagem, 
            "Erro no Login", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, 
            mensagem, 
            "Login Bem-sucedido!", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}