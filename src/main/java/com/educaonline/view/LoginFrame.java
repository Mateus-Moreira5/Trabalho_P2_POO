package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import main.java.com.educaonline.controller.UsuarioController;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.Aluno;
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
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(200, 40));
        panel.add(btnLogin, gbc);
        
        // Adicionar dados de exemplo para teste
        gbc.gridy = 8;
        JButton btnDadosTeste = new JButton("Usar Dados de Teste");
        btnDadosTeste.setBackground(new Color(100, 100, 100));
        btnDadosTeste.setForeground(Color.WHITE);
        btnDadosTeste.setFocusPainted(false);
        btnDadosTeste.addActionListener(e -> preencherDadosTeste());
        panel.add(btnDadosTeste, gbc);
        
        // Event listeners
        campoEmail.addActionListener(e -> fazerLogin());
        campoSenha.addActionListener(e -> fazerLogin());
        btnLogin.addActionListener(e -> fazerLogin());
        
        add(panel);
    }
    
    private void fazerLogin() {
        String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());
        
        // Validações básicas
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
        
        // Tentar fazer login
        Usuario usuario = UsuarioController.fazerLogin(email, senha);
        
        if (usuario != null) {
            // Verificar se o tipo de usuário está correto
            if (!validarTipoUsuario(usuario, tipoUsuario)) {
                String tipoReal = obterTipoRealUsuario(usuario);
                mostrarErro("Tipo de usuário incorreto! Você é um " + tipoReal + ".");
                return;
            }
            
            // Login bem-sucedido
            String mensagemSucesso = "Login realizado com sucesso!\n";
            if (usuario instanceof AlunoVIP) {
                mensagemSucesso += "Bem-vindo, Aluno VIP! ⭐";
            } else if (usuario instanceof Professor) {
                mensagemSucesso += "Bem-vindo, Professor!";
            } else {
                mensagemSucesso += "Bem-vindo!";
            }
            
            mostrarSucesso(mensagemSucesso);
            
            // Abrir dashboard apropriado
            abrirDashboard(usuario);
            dispose();
            
        } else {
            mostrarErro("Email ou senha incorretos!\n\nDica: Use o botão 'Usar Dados de Teste' para ver credenciais válidas.");
        }
    }
    
    private boolean validarTipoUsuario(Usuario usuario, String tipoSelecionado) {
        if (usuario instanceof AlunoVIP && "Aluno VIP".equals(tipoSelecionado)) {
            return true;
        } else if (usuario instanceof Professor && "Professor".equals(tipoSelecionado)) {
            return true;
        } else if (usuario instanceof Aluno && !(usuario instanceof AlunoVIP) && "Aluno".equals(tipoSelecionado)) {
            return true;
        }
        return false;
    }
    
    private String obterTipoRealUsuario(Usuario usuario) {
        if (usuario instanceof AlunoVIP) {
            return "Aluno VIP";
        } else if (usuario instanceof Professor) {
            return "Professor";
        } else if (usuario instanceof Aluno) {
            return "Aluno";
        }
        return "Usuário";
    }
    
    private void abrirDashboard(Usuario usuario) {
        // Usar DashboardFrame unificado para todos os tipos de usuário
        new DashboardFrame(usuario).setVisible(true);
    }
    
    private void preencherDadosTeste() {
        String tipoSelecionado = (String) comboTipoUsuario.getSelectedItem();
        
        switch (tipoSelecionado) {
            case "Aluno":
                campoEmail.setText("joao@email.com");
                campoSenha.setText("123");
                break;
            case "Aluno VIP":
                campoEmail.setText("ana@vip.com");
                campoSenha.setText("123");
                break;
            case "Professor":
                // Adicionar um professor de exemplo se não existir
                campoEmail.setText("professor@educa.com");
                campoSenha.setText("123");
                break;
        }
        
        JOptionPane.showMessageDialog(this,
            "Dados de teste preenchidos!\n\n" +
            "Email: " + campoEmail.getText() + "\n" +
            "Senha: " + campoSenha.getText() + "\n\n" +
            "Clique em 'Entrar no Sistema'",
            "Dados de Teste",
            JOptionPane.INFORMATION_MESSAGE);
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