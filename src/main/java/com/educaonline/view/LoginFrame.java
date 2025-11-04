package main.java.com.educaonline.view;
import main.java.com.educaonline.controller.UsuarioController;
import main.java.com.educaonline.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JCheckBox checkVIP;
    private JButton btnLogin;
    
    public LoginFrame() {
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setTitle("🎓 EducaOnline - Login");
        setSize(400, 400);
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
        panel.add(Box.createVerticalStrut(30), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; 
        gbc.gridy = 3; 
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelEmail, gbc);
        
        gbc.gridx = 1; 
        gbc.gridy = 3; 
        gbc.anchor = GridBagConstraints.LINE_START;
        campoEmail = new JTextField(20);
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(campoEmail, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 4; 
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelSenha, gbc);
        
        gbc.gridx = 1; 
        gbc.gridy = 4; 
        gbc.anchor = GridBagConstraints.LINE_START;
        campoSenha = new JPasswordField(20);
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(campoSenha, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 5; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        checkVIP = new JCheckBox("Sou aluno VIP");
        checkVIP.setBackground(new Color(245, 245, 245));
        checkVIP.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(checkVIP, gbc);
        
        gbc.gridy = 6;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 7; 
        gbc.gridwidth = 2;
        btnLogin = new JButton("Entrar no Sistema");
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.BLUE);
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
    String email = campoEmail.getText().trim();
    String senha = new String(campoSenha.getPassword());
    boolean isVIP = checkVIP.isSelected();
    
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
    
    // Validação de email
    if (!email.contains("@") || !email.contains(".")) {
        mostrarErro("Por favor, informe um email válido!");
        campoEmail.requestFocus();
        campoEmail.selectAll();
        return;
    }
    
    // Tentar fazer login
    Usuario usuario = UsuarioController.fazerLogin(email, senha);
    
    if (usuario != null) {
        mostrarSucesso("Login realizado com sucesso!\n" + 
                      (usuario instanceof main.java.com.educaonline.model.AlunoVIP ? "🌟 Bem-vindo, Aluno VIP!" : "👋 Bem-vindo!"));
        
        // Abrir dashboard
        new DashboardFrame(usuario).setVisible(true);
        dispose(); // Fecha a tela de login
    } else {
        mostrarErro("Email ou senha incorretos!");
    }
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
            "Login Bem-sucedido", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}