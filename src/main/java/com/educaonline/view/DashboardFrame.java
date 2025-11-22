package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.Aluno;
import main.java.com.educaonline.model.AlunoVIP;
import main.java.com.educaonline.model.Professor;
import main.java.com.educaonline.service.RelatorioService;
import main.java.com.educaonline.util.DatabaseUtil;

public class DashboardFrame extends JFrame {
    private Usuario usuarioLogado;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    public DashboardFrame(Usuario usuario) {
        this.usuarioLogado = usuario;
        configurarJanela();
        initComponents();
        mostrarPainelPrincipal();
    }
    
    private void configurarJanela() {
        setTitle("🎓 EducaOnline - Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void initComponents() {
        // Layout principal
        setLayout(new BorderLayout());
        
        // Header
        add(criarHeader(), BorderLayout.NORTH);
        
        // Menu lateral + conteúdo
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, criarMenuLateral(), criarAreaConteudo());
        splitPane.setDividerLocation(250);
        splitPane.setEnabled(false);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 100, 200));
        header.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        header.setPreferredSize(new Dimension(100, 70));
        
        // Título
        JLabel titulo = new JLabel("🎓 EducaOnline - Plataforma EAD");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        
        // Informações do usuário
        String tipoUsuario = obterTipoUsuario();
        JLabel infoUsuario = new JLabel(usuarioLogado.getNome() + " | " + tipoUsuario);
        infoUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        infoUsuario.setForeground(Color.WHITE);
        
        header.add(titulo, BorderLayout.WEST);
        header.add(infoUsuario, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel criarMenuLateral() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(245, 245, 245));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        menuPanel.setPreferredSize(new Dimension(250, 0));
        
        // Título do menu
        JLabel menuTitulo = new JLabel("MENU PRINCIPAL");
        menuTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        menuTitulo.setForeground(new Color(0, 100, 200));
        menuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuTitulo);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Botões do menu baseados no tipo de usuário
        String[][] botoesMenu = obterBotoesMenu();
        
        for (String[] botaoInfo : botoesMenu) {
            JButton botao = criarBotaoMenu(botaoInfo[0], botaoInfo[1]);
            menuPanel.add(botao);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        menuPanel.add(Box.createVerticalGlue());
        
        // Botão sair
        JButton btnSair = criarBotaoSair();
        menuPanel.add(btnSair);
        
        return menuPanel;
    }
    
    private String[][] obterBotoesMenu() {
        if (usuarioLogado instanceof Professor) {
            return new String[][] {
                {"🏠", "Dashboard"},
                {"📚", "Gerenciar Cursos"},
                {"👥", "Minhas Turmas"},
                {"📊", "Relatórios"},
                {"⚙️", "Configurações"}
            };
        } else if (usuarioLogado instanceof AlunoVIP) {
            return new String[][] {
                {"🏠", "Dashboard"},
                {"📚", "Cursos Disponíveis"},
                {"🎫", "Minhas Matrículas"},
                {"🏆", "Meus Certificados"},
                {"⭐", "Área VIP"},
                {"💼", "Mentorias"},
                {"📊", "Relatórios"},
                {"💰", "Pagamentos"}
            };
        } else {
            return new String[][] {
                {"🏠", "Dashboard"},
                {"📚", "Cursos Disponíveis"},
                {"🎫", "Minhas Matrículas"},
                {"🏆", "Meus Certificados"},
                {"📊", "Relatórios"},
                {"💰", "Pagamentos"}
            };
        }
    }
    
    private JButton criarBotaoMenu(String icone, String texto) {
        JButton botao = new JButton("<html><div style='text-align: left;'>" + icone + " " + texto + "</div></html>");
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botao.setMaximumSize(new Dimension(220, 50));
        botao.setBackground(Color.WHITE);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.addActionListener(new MenuListener());
        return botao;
    }
    
    private JButton criarBotaoSair() {
        JButton btnSair = new JButton("🚪 Sair do Sistema");
        btnSair.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSair.setMaximumSize(new Dimension(220, 45));
        btnSair.setBackground(new Color(220, 80, 60));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente sair do sistema?", 
                "Confirmar Saída", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        return btnSair;
    }
    
    private JPanel criarAreaConteudo() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        // Adicionar diferentes "cards" (telas)
        contentPanel.add(criarPainelPrincipal(), "PRINCIPAL");
        contentPanel.add(criarPainelPlaceholder("Cursos"), "CURSOS");
        contentPanel.add(criarPainelPlaceholder("Matrículas"), "MATRICULAS");
        contentPanel.add(criarPainelPlaceholder("Certificados"), "CERTIFICADOS");
        contentPanel.add(criarPainelPlaceholder("Relatórios"), "RELATORIOS");
        
        return contentPanel;
    }
    
    private JPanel criarPainelPrincipal() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Painel de boas-vindas
        mainPanel.add(criarPainelBoasVindas(), BorderLayout.NORTH);
        
        // Cards de estatísticas
        mainPanel.add(criarCardsEstatisticas(), BorderLayout.CENTER);
        
        // Ações rápidas
        mainPanel.add(criarPainelAcoesRapidas(), BorderLayout.SOUTH);
        
        return mainPanel;
    }
    
    private JPanel criarPainelBoasVindas() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(240, 245, 255));
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 255)),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        String mensagemBoasVindas = criarMensagemBoasVindas();
        JLabel lblBoasVindas = new JLabel("<html><div style='text-align: center;'>" + mensagemBoasVindas + "</div></html>");
        lblBoasVindas.setFont(new Font("Arial", Font.PLAIN, 16));
        
        welcomePanel.add(lblBoasVindas, BorderLayout.CENTER);
        
        return welcomePanel;
    }
    
    private String criarMensagemBoasVindas() {
        String mensagem = "<h1>Bem-vindo, " + usuarioLogado.getNome() + "!</h1>";
        
        if (usuarioLogado instanceof AlunoVIP) {
            mensagem += "<p style='color: #666; font-size: 14px;'>⭐ Você é um Aluno VIP! Aproveite todos os benefícios exclusivos.</p>";
        } else if (usuarioLogado instanceof Professor) {
            mensagem += "<p style='color: #666; font-size: 14px;'>👨‍🏫 Área do Professor - Gerencie seus cursos e turmas.</p>";
        } else {
            mensagem += "<p style='color: #666; font-size: 14px;'>🎓 Continue sua jornada de aprendizado!</p>";
        }
        
        return mensagem;
    }
    
    private JPanel criarCardsEstatisticas() {
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        statsPanel.setBackground(Color.WHITE);
        
        String[][] estatisticas = obterEstatisticas();
        
        for (String[] stat : estatisticas) {
            JPanel card = criarCardEstatistica(stat[0], stat[1], stat[2], stat[3]);
            statsPanel.add(card);
        }
        
        return statsPanel;
    }
    
    private String[][] obterEstatisticas() {
        if (usuarioLogado instanceof Professor) {
            return new String[][] {
                {"📚", "Cursos Ministrados", "5", "info"},
                {"👥", "Alunos Ativos", "127", "success"},
                {"⭐", "Avaliação Média", "4.8/5", "warning"},
                {"💰", "Salário Base", "R$ 8.500", "success"},
                {"📊", "Turmas Ativas", "3", "info"},
                {"🎯", "Meta Batida", "92%", "success"}
            };
        } else {
            Aluno aluno = DatabaseUtil.getAlunoPorEmail(usuarioLogado.getEmail());
            int totalCursos = aluno != null ? aluno.getCursosMatriculados().size() : 0;
            
            return new String[][] {
                {"📚", "Cursos Matriculados", String.valueOf(totalCursos), "info"},
                {"✅", "Cursos Concluídos", "2", "success"},
                {"⭐", "Status", usuarioLogado instanceof AlunoVIP ? "VIP" : "Regular", "warning"},
                {"📊", "Média Geral", "8.2", "success"},
                {"⏱️", "Horas Estudadas", "45h", "info"},
                {"🎯", "Progresso", "75%", "success"}
            };
        }
    }
    
    private JPanel criarCardEstatistica(String icone, String titulo, String valor, String tipo) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(obterCorBorda(tipo)),
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
        lblValor.setForeground(obterCorTexto(tipo));
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(lblIcone);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(lblValor);
        
        return card;
    }
    
    private Color obterCorBorda(String tipo) {
        switch (tipo) {
            case "success": return new Color(200, 230, 200);
            case "warning": return new Color(255, 240, 200);
            case "error": return new Color(255, 200, 200);
            default: return new Color(200, 200, 200);
        }
    }
    
    private Color obterCorTexto(String tipo) {
        switch (tipo) {
            case "success": return new Color(0, 150, 0);
            case "warning": return new Color(200, 120, 0);
            case "error": return new Color(200, 0, 0);
            default: return new Color(0, 100, 200);
        }
    }
    
    private JPanel criarPainelAcoesRapidas() {
        JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        quickPanel.setBackground(Color.WHITE);
        quickPanel.setBorder(BorderFactory.createTitledBorder("Ações Rápidas"));
        
        String[][] acoes = obterAcoesRapidas();
        
        for (String[] acao : acoes) {
            JButton btnAcao = criarBotaoAcaoRapida(acao[0], acao[1], acao[2]);
            quickPanel.add(btnAcao);
        }
        
        return quickPanel;
    }
    
    private String[][] obterAcoesRapidas() {
        if (usuarioLogado instanceof Professor) {
            return new String[][] {
                {"📝", "Cadastrar Curso", "CADASTRAR_CURSO"},
                {"👥", "Ver Turmas", "VER_TURMAS"},
                {"📊", "Relatório Geral", "RELATORIO_GERAL"}
            };
        } else {
            return new String[][] {
                {"📚", "Ver Cursos", "VER_CURSOS"},
                {"🎫", "Minhas Matrículas", "VER_MATRICULAS"},
                {"🏆", "Meus Certificados", "VER_CERTIFICADOS"},
                {"📈", "Meu Desempenho", "VER_DESEMPENHO"}
            };
        }
    }
    
    private JButton criarBotaoAcaoRapida(String icone, String texto, String comando) {
        JButton botao = new JButton("<html><center>" + icone + "<br>" + texto + "</center></html>");
        botao.setPreferredSize(new Dimension(140, 80));
        botao.setBackground(new Color(240, 245, 255));
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 255)),
            BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));
        botao.addActionListener(e -> executarAcaoRapida(comando));
        return botao;
    }
    
    private JPanel criarPainelPlaceholder(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel(titulo + " - Funcionalidade em Desenvolvimento", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        
        panel.add(label, BorderLayout.NORTH);
        
        JButton btnVoltar = new JButton("⬅️ Voltar ao Dashboard");
        btnVoltar.addActionListener(e -> mostrarPainelPrincipal());
        btnVoltar.setPreferredSize(new Dimension(200, 40));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnVoltar);
        
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void executarAcaoRapida(String comando) {
        switch (comando) {
            case "VER_CURSOS":
                new CursoListagemFrame(usuarioLogado).setVisible(true);
                break;
            case "VER_MATRICULAS":
                new MatriculaFrame(usuarioLogado).setVisible(true);
                break;
            case "VER_CERTIFICADOS":
                new CertificadoFrame(usuarioLogado).setVisible(true);
                break;
            case "CADASTRAR_CURSO":
                JOptionPane.showMessageDialog(this, "Abrindo cadastro de curso...");
                break;
            case "VER_DESEMPENHO":
                String relatorio = RelatorioService.gerarRelatorioAluno(usuarioLogado.getEmail());
                JOptionPane.showMessageDialog(this, relatorio, "Meu Desempenho", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "RELATORIO_GERAL":
                String relatorioGeral = RelatorioService.gerarRelatorioFinanceiro();
                JOptionPane.showMessageDialog(this, relatorioGeral, "Relatório Geral", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    private void mostrarPainelPrincipal() {
        cardLayout.show(contentPanel, "PRINCIPAL");
    }
    
    private String obterTipoUsuario() {
        if (usuarioLogado instanceof AlunoVIP) return "Aluno VIP ⭐";
        if (usuarioLogado instanceof Professor) return "Professor 👨‍🏫";
        if (usuarioLogado instanceof Aluno) return "Aluno 🎓";
        return "Usuário";
    }
    
    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String textoBotao = ((JButton) e.getSource()).getText();
            
            if (textoBotao.contains("Dashboard")) {
                mostrarPainelPrincipal();
            } else if (textoBotao.contains("Cursos Disponíveis") || textoBotao.contains("Gerenciar Cursos")) {
                new CursoListagemFrame(usuarioLogado).setVisible(true);
            } else if (textoBotao.contains("Matrículas") || textoBotao.contains("Turmas")) {
                new MatriculaFrame(usuarioLogado).setVisible(true);
            } else if (textoBotao.contains("Certificados")) {
                new CertificadoFrame(usuarioLogado).setVisible(true);
            } else if (textoBotao.contains("Relatórios")) {
                cardLayout.show(contentPanel, "RELATORIOS");
            } else if (textoBotao.contains("Área VIP")) {
                JOptionPane.showMessageDialog(DashboardFrame.this,
                    "🌟 Área VIP - Benefícios Exclusivos:\n\n" +
                    "• Cursos exclusivos VIP\n" +
                    "• Mentorias personalizadas\n" +
                    "• Suporte prioritário 24/7\n" +
                    "• Descontos especiais\n" +
                    "• Certificados premium",
                    "Área VIP", JOptionPane.INFORMATION_MESSAGE);
            } else if (textoBotao.contains("Mentorias")) {
                JOptionPane.showMessageDialog(DashboardFrame.this,
                    "Agende suas mentorias com nossos especialistas!\n\n" +
                    "Disponível para alunos VIP.",
                    "Mentorias VIP", JOptionPane.INFORMATION_MESSAGE);
            } else {
                cardLayout.show(contentPanel, textoBotao.toUpperCase().replace(" ", ""));
            }
        }
    }
}