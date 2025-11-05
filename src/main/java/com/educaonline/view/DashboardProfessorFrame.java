package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.Professor;

public class DashboardProfessorFrame extends JFrame {
    private Usuario usuarioLogado;
    
    public DashboardProfessorFrame(Usuario usuario) {
        this.usuarioLogado = usuario;
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setTitle("👨‍🏫 EducaOnline - Área do Professor");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(criarHeader(), BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                                            criarMenu(), 
                                            criarAreaConteudo());
        splitPane.setDividerLocation(220);
        splitPane.setEnabled(false);
        
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(70, 130, 180));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(100, 70));
        
        JLabel titulo = new JLabel("👨‍🏫 Área do Professor - EducaOnline");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        
        Professor professor = (Professor) usuarioLogado;
        JLabel usuarioInfo = new JLabel("Professor: " + professor.getNome() + " | " + professor.getEspecialidade());
        usuarioInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        usuarioInfo.setForeground(Color.WHITE);
        
        header.add(titulo, BorderLayout.WEST);
        header.add(usuarioInfo, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel criarMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(240, 240, 240));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        
        JLabel menuTitulo = new JLabel("MENU DO PROFESSOR");
        menuTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        menuTitulo.setForeground(new Color(70, 130, 180));
        menuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuTitulo);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        String[] botoesMenu = {
            "📊 Dashboard",
            "📚 Meus Cursos", 
            "👥 Minhas Turmas",
            "📝 Cadastrar Curso",
            "📋 Lista de Alunos",
            "📊 Notas e Avaliações",
            "📈 Relatórios",
            "⚙️ Configurações"
        };
        
        for (String textoBotao : botoesMenu) {
            JButton botao = criarBotaoMenu(textoBotao);
            menuPanel.add(botao);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        
        menuPanel.add(Box.createVerticalGlue());
        
        JButton btnSair = criarBotaoSair();
        menuPanel.add(btnSair);
        
        return menuPanel;
    }
    
    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botao.setMaximumSize(new Dimension(180, 45));
        botao.setBackground(Color.WHITE);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        botao.addActionListener(new MenuListener());
        return botao;
    }
    
    private JButton criarBotaoSair() {
        JButton botaoSair = new JButton("🚪 Sair do Sistema");
        botaoSair.setAlignmentX(Component.LEFT_ALIGNMENT);
        botaoSair.setMaximumSize(new Dimension(180, 45));
        botaoSair.setBackground(new Color(220, 80, 60));
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setFocusPainted(false);
        botaoSair.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        botaoSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                DashboardProfessorFrame.this, 
                "Deseja realmente sair do sistema?", 
                "Confirmar Saída", 
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        return botaoSair;
    }
    
    private JPanel criarAreaConteudo() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        JPanel welcomePanel = criarPainelBoasVindas();
        contentPanel.add(welcomePanel, BorderLayout.NORTH);
        
        JPanel statsPanel = criarPainelEstatisticas();
        contentPanel.add(statsPanel, BorderLayout.CENTER);
        
        JPanel quickActionsPanel = criarPainelAcoesRapidas();
        contentPanel.add(quickActionsPanel, BorderLayout.SOUTH);
        
        return contentPanel;
    }
    
    private JPanel criarPainelBoasVindas() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(230, 240, 255));
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 230)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        Professor professor = (Professor) usuarioLogado;
        JLabel lblBoasVindas = new JLabel("<html><div style='text-align: center;'>"
            + "<h1>Bem-vindo, Professor " + professor.getNome() + "!</h1>"
            + "<p style='font-size: 14px; color: #666;'>"
            + "Especialidade: " + professor.getEspecialidade() + " | "
            + "Nível: " + professor.getNivelExperiencia() + " | "
            + "Experiência: " + professor.getAnosExperiencia() + " anos"
            + "</p></div></html>");
        lblBoasVindas.setHorizontalAlignment(SwingConstants.CENTER);
        
        welcomePanel.add(lblBoasVindas, BorderLayout.CENTER);
        
        return welcomePanel;
    }
    
    private JPanel criarPainelEstatisticas() {
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        statsPanel.setBackground(Color.WHITE);
        
        String[][] estatisticas = {
            {"📚", "Cursos Ministrados", "5"},
            {"👥", "Alunos Ativos", "127"}, 
            {"⭐", "Avaliação Média", "4.8"},
            {"💼", "Salário Base", "R$ 8.500"}
        };
        
        for (String[] stat : estatisticas) {
            JPanel card = criarCardEstatistica(stat[0], stat[1], stat[2]);
            statsPanel.add(card);
        }
        
        return statsPanel;
    }
    
    private JPanel criarCardEstatistica(String icone, String titulo, String valor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("Arial", Font.PLAIN, 28));
        lblIcone.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitulo.setForeground(Color.GRAY);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(lblIcone);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(lblValor);
        
        return card;
    }
    
    private JPanel criarPainelAcoesRapidas() {
        JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        quickPanel.setBackground(Color.WHITE);
        quickPanel.setBorder(BorderFactory.createTitledBorder("Ações Rápidas"));
        
        String[][] acoes = {
            {"📝", "Novo Curso", "NOVO_CURSO"},
            {"👥", "Minhas Turmas", "TURMAS"}, 
            {"📋", "Lista Alunos", "ALUNOS"},
            {"📊", "Lançar Notas", "NOTAS"}
        };
        
        for (String[] acao : acoes) {
            JButton btnAcao = criarBotaoAcaoRapida(acao[0], acao[1], acao[2]);
            quickPanel.add(btnAcao);
        }
        
        return quickPanel;
    }
    
    private JButton criarBotaoAcaoRapida(String icone, String texto, String comando) {
        JButton botao = new JButton("<html><center>" + icone + "<br>" + texto + "</center></html>");
        botao.setPreferredSize(new Dimension(120, 80));
        botao.setBackground(new Color(230, 240, 255));
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 230)),
            BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));
        botao.addActionListener(e -> executarAcaoRapida(comando));
        return botao;
    }
    
    private void executarAcaoRapida(String comando) {
        switch (comando) {
            case "NOVO_CURSO":
                new CursoCadastroFrame().setVisible(true);
                break;
            case "TURMAS":
                JOptionPane.showMessageDialog(this, 
                    "👥 Gerenciamento de Turmas\n\n"
                    + "• Java Básico - Turma A (30 alunos)\n"
                    + "• Java Avançado - Turma B (25 alunos)\n"
                    + "• Matemática - Turma C (40 alunos)",
                    "Minhas Turmas",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
            case "ALUNOS":
                JOptionPane.showMessageDialog(this, 
                    "📋 Lista de Alunos\n\n"
                    + "Total de alunos: 127\n"
                    + "Alunos VIP: 15\n"
                    + "Alunos Regulares: 112",
                    "Lista de Alunos",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
            case "NOTAS":
                JOptionPane.showMessageDialog(this, 
                    "📊 Sistema de Avaliações\n\n"
                    + "Funcionalidade em desenvolvimento\n"
                    + "Em breve: lançamento de notas e relatórios",
                    "Lançar Notas",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = ((JButton) e.getSource()).getText();
            
            switch (comando) {
                case "📚 Meus Cursos":
                    JOptionPane.showMessageDialog(DashboardProfessorFrame.this,
                        "📚 Meus Cursos Ministrados:\n\n"
                        + "• Java Básico (40 horas)\n"
                        + "• Java Avançado e POO (60 horas)\n"
                        + "• Matemática para Programadores (35 horas)\n"
                        + "• Design Patterns (45 horas)",
                        "Meus Cursos",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "👥 Minhas Turmas":
                    JOptionPane.showMessageDialog(DashboardProfessorFrame.this,
                        "👥 Minhas Turmas Ativas:\n\n"
                        + "• Java Básico - Turma A (30/30 alunos)\n"
                        + "• Java Avançado - Turma B (25/25 alunos)\n"
                        + "• Matemática - Turma C (40/40 alunos)",
                        "Minhas Turmas",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "📝 Cadastrar Curso":
                    new CursoCadastroFrame().setVisible(true);
                    break;
                case "📊 Notas e Avaliações":
                    JOptionPane.showMessageDialog(DashboardProfessorFrame.this,
                        "📊 Sistema de Avaliações\n\n"
                        + "• Lançar notas dos alunos\n"
                        + "• Gerar boletins\n"
                        + "• Estatísticas de desempenho\n"
                        + "• Relatórios de aprovação",
                        "Notas e Avaliações",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(DashboardProfessorFrame.this,
                        "Funcionalidade em desenvolvimento: " + comando,
                        "Em Desenvolvimento",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}