package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.model.Curso;
import main.java.com.educaonline.util.DatabaseUtil;

public class CursoCadastroFrame extends JFrame {
    private JTextField campoTitulo, campoCategoria, campoCargaHoraria, campoPreco, campoLimiteVagas;
    private JTextArea campoDescricao;
    private JCheckBox checkExclusivoVIP;
    private JButton btnCadastrar, btnLimpar, btnCancelar;
    
    public CursoCadastroFrame() {
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setTitle("📝 Cadastro de Curso - EducaOnline");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titulo = new JLabel("Cadastro de Novo Curso", JLabel.CENTER);
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
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Título do Curso:*"), gbc);
        gbc.gridx = 1;
        campoTitulo = new JTextField(25);
        formPanel.add(campoTitulo, gbc);
        
        // Categoria
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Categoria:*"), gbc);
        gbc.gridx = 1;
        campoCategoria = new JTextField(25);
        formPanel.add(campoCategoria, gbc);
        
        // Carga Horária
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Carga Horária (horas):*"), gbc);
        gbc.gridx = 1;
        campoCargaHoraria = new JTextField(10);
        formPanel.add(campoCargaHoraria, gbc);
        
        // Preço
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Preço (R$):*"), gbc);
        gbc.gridx = 1;
        campoPreco = new JTextField(10);
        formPanel.add(campoPreco, gbc);
        
        // Limite de Vagas
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Limite de Vagas:*"), gbc);
        gbc.gridx = 1;
        campoLimiteVagas = new JTextField(10);
        formPanel.add(campoLimiteVagas, gbc);
        
        // Descrição
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Descrição:*"), gbc);
        gbc.gridx = 1;
        campoDescricao = new JTextArea(4, 25);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(campoDescricao);
        scrollDescricao.setPreferredSize(new Dimension(300, 80));
        formPanel.add(scrollDescricao, gbc);
        
        // Checkbox Exclusivo VIP
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        checkExclusivoVIP = new JCheckBox("Curso Exclusivo para Alunos VIP ⭐");
        checkExclusivoVIP.setToolTipText("Apenas alunos VIP poderão se matricular neste curso");
        formPanel.add(checkExclusivoVIP, gbc);
        
        // Sugestões de categoria
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        JLabel lblSugestoes = new JLabel("💡 Sugestões de categoria: Programação, Matemática, Design, Marketing, Negócios");
        lblSugestoes.setFont(new Font("Arial", Font.PLAIN, 10));
        lblSugestoes.setForeground(Color.GRAY);
        formPanel.add(lblSugestoes, gbc);
        
        return formPanel;
    }
    
    private JPanel criarBotoes() {
        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        btnCadastrar = new JButton("✅ Cadastrar Curso");
        btnCadastrar.setBackground(new Color(0, 150, 0));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarCurso();
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
        
        btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setBackground(new Color(220, 80, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelBotoes.add(btnCadastrar);
        panelBotoes.add(btnLimpar);
        panelBotoes.add(btnCancelar);
        
        return panelBotoes;
    }
    
    private void cadastrarCurso() {
        // Validações
        if (!validarFormulario()) {
            return;
        }
        
        // Coletar dados
        String titulo = campoTitulo.getText().trim();
        String categoria = campoCategoria.getText().trim();
        String descricao = campoDescricao.getText().trim();
        int cargaHoraria = Integer.parseInt(campoCargaHoraria.getText().trim());
        double preco = Double.parseDouble(campoPreco.getText().trim());
        int limiteVagas = Integer.parseInt(campoLimiteVagas.getText().trim());
        boolean exclusivoVIP = checkExclusivoVIP.isSelected();
        
        // Gerar ID único (simulação)
        String cursoId = "C" + String.format("%03d", (DatabaseUtil.getCursos().size() + 1));
        
        // Criar curso
        Curso novoCurso = new Curso(cursoId, titulo, categoria, descricao, preco, cargaHoraria, exclusivoVIP, limiteVagas);
        
        // Simular adição ao banco (em um sistema real, isso seria persistido)
        DatabaseUtil.getCursos().add(novoCurso);
        
        // Mensagem de sucesso
        JOptionPane.showMessageDialog(this,
            "✅ Curso cadastrado com sucesso!\n\n" +
            "ID: " + cursoId + "\n" +
            "Título: " + titulo + "\n" +
            "Categoria: " + categoria + "\n" +
            "Preço: R$ " + String.format("%.2f", preco) + "\n" +
            "Carga Horária: " + cargaHoraria + " horas\n" +
            "Vagas: " + limiteVagas + " alunos\n" +
            "Tipo: " + (exclusivoVIP ? "Exclusivo VIP ⭐" : "Curso Regular"),
            "Curso Cadastrado",
            JOptionPane.INFORMATION_MESSAGE);
            
        limparFormulario();
    }
    
    private boolean validarFormulario() {
        String titulo = campoTitulo.getText().trim();
        String categoria = campoCategoria.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String cargaHorariaStr = campoCargaHoraria.getText().trim();
        String precoStr = campoPreco.getText().trim();
        String limiteVagasStr = campoLimiteVagas.getText().trim();
        
        // Validações básicas
        if (titulo.isEmpty()) {
            mostrarErro("Por favor, informe o título do curso!");
            campoTitulo.requestFocus();
            return false;
        }
        
        if (categoria.isEmpty()) {
            mostrarErro("Por favor, informe a categoria do curso!");
            campoCategoria.requestFocus();
            return false;
        }
        
        if (descricao.isEmpty()) {
            mostrarErro("Por favor, informe a descrição do curso!");
            campoDescricao.requestFocus();
            return false;
        }
        
        // Validação numérica
        try {
            int cargaHoraria = Integer.parseInt(cargaHorariaStr);
            if (cargaHoraria <= 0) {
                mostrarErro("A carga horária deve ser maior que zero!");
                campoCargaHoraria.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarErro("Carga horária deve ser um número válido!");
            campoCargaHoraria.requestFocus();
            return false;
        }
        
        try {
            double preco = Double.parseDouble(precoStr);
            if (preco < 0) {
                mostrarErro("O preço não pode ser negativo!");
                campoPreco.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarErro("Preço deve ser um valor numérico válido!");
            campoPreco.requestFocus();
            return false;
        }
        
        try {
            int limiteVagas = Integer.parseInt(limiteVagasStr);
            if (limiteVagas <= 0) {
                mostrarErro("O limite de vagas deve ser maior que zero!");
                campoLimiteVagas.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarErro("Limite de vagas deve ser um número válido!");
            campoLimiteVagas.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limparFormulario() {
        campoTitulo.setText("");
        campoCategoria.setText("");
        campoDescricao.setText("");
        campoCargaHoraria.setText("");
        campoPreco.setText("");
        campoLimiteVagas.setText("");
        checkExclusivoVIP.setSelected(false);
        campoTitulo.requestFocus();
    }
    
    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, 
            mensagem, 
            "Erro no Cadastro", 
            JOptionPane.ERROR_MESSAGE);
    }
}