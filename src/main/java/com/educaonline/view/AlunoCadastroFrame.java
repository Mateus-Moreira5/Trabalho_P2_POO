package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.util.ValidacaoUtil;

public class AlunoCadastroFrame extends JFrame {
    private JTextField campoNome, campoEmail, campoTelefone;
    private JPasswordField campoSenha, campoConfirmarSenha;
    private JCheckBox checkVIP;
    private JButton btnCadastrar, btnLimpar;
    
    public AlunoCadastroFrame() {
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setTitle("👤 Cadastro de Aluno - EducaOnline");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titulo = new JLabel("Cadastro de Novo Aluno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // Formulário
        mainPanel.add(criarFormulario(), BorderLayout.CENTER);
        
        // Botões
        mainPanel.add(criarBotoes(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel criarFormulario() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome Completo:*"), gbc);
        gbc.gridx = 1;
        campoNome = new JTextField(20);
        formPanel.add(campoNome, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:*"), gbc);
        gbc.gridx = 1;
        campoEmail = new JTextField(20);
        formPanel.add(campoEmail, gbc);
        
        // Telefone
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Telefone:*"), gbc);
        gbc.gridx = 1;
        campoTelefone = new JTextField(20);
        formPanel.add(campoTelefone, gbc);
        
        // Senha
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Senha:*"), gbc);
        gbc.gridx = 1;
        campoSenha = new JPasswordField(20);
        formPanel.add(campoSenha, gbc);
        
        // Confirmar Senha
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Confirmar Senha:*"), gbc);
        gbc.gridx = 1;
        campoConfirmarSenha = new JPasswordField(20);
        formPanel.add(campoConfirmarSenha, gbc);
        
        // Checkbox VIP
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        checkVIP = new JCheckBox("Aluno VIP (acesso a cursos exclusivos e suporte personalizado)");
        formPanel.add(checkVIP, gbc);
        
        return formPanel;
    }
    
    private JPanel criarBotoes() {
        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        btnCadastrar = new JButton("✅ Cadastrar Aluno");
        btnCadastrar.setBackground(new Color(0, 150, 0));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarAluno();
            }
        });
        
        btnLimpar = new JButton("🔄 Limpar");
        btnLimpar.setBackground(new Color(200, 200, 200));
        btnLimpar.setFocusPainted(false);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparFormulario();
            }
        });
        
        panelBotoes.add(btnCadastrar);
        panelBotoes.add(btnLimpar);
        
        return panelBotoes;
    }
    
    private void cadastrarAluno() {
        // Validações
        if (!validarFormulario()) {
            return;
        }
        
        // Simular cadastro
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        boolean isVIP = checkVIP.isSelected();
        
        JOptionPane.showMessageDialog(this,
            "✅ Aluno cadastrado com sucesso!\n\n" +
            "Nome: " + nome + "\n" +
            "Email: " + email + "\n" +
            "Tipo: " + (isVIP ? "Aluno VIP ⭐" : "Aluno Regular"),
            "Cadastro Realizado",
            JOptionPane.INFORMATION_MESSAGE);
            
        limparFormulario();
    }
    
    private boolean validarFormulario() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String senha = new String(campoSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());
        
        if (nome.isEmpty()) {
            mostrarErro("Por favor, informe o nome completo!");
            campoNome.requestFocus();
            return false;
        }
        
        if (email.isEmpty() || !ValidacaoUtil.validarEmail(email)) {
            mostrarErro("Por favor, informe um email válido!");
            campoEmail.requestFocus();
            return false;
        }
        
        if (telefone.isEmpty()) {
            mostrarErro("Por favor, informe o telefone!");
            campoTelefone.requestFocus();
            return false;
        }
        
        if (senha.length() < 6) {
            mostrarErro("A senha deve ter pelo menos 6 caracteres!");
            campoSenha.requestFocus();
            return false;
        }
        
        if (!senha.equals(confirmarSenha)) {
            mostrarErro("As senhas não coincidem!");
            campoConfirmarSenha.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limparFormulario() {
        campoNome.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        campoSenha.setText("");
        campoConfirmarSenha.setText("");
        checkVIP.setSelected(false);
        campoNome.requestFocus();
    }
    
    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, 
            mensagem, 
            "Erro no Cadastro", 
            JOptionPane.ERROR_MESSAGE);
    }
}