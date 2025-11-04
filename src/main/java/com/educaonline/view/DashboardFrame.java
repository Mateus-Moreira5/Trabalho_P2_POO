package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.Aluno;
import main.java.com.educaonline.model.AlunoVIP;

public class DashboardFrame extends JFrame {
    private Usuario usuarioLogado;
    
    public DashboardFrame(Usuario usuario) {
        this.usuarioLogado = usuario;
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setTitle("🎓 EducaOnline - Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = criarHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Menu de Navegação
        JPanel menuPanel = criarMenu();
        mainPanel.add(menuPanel, BorderLayout.WEST);
        
        // Área de Conteúdo
        JPanel contentPanel = criarAreaConteudo();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 100, 200));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titulo = new JLabel("EducaOnline - Plataforma EAD");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        
        JLabel usuarioInfo = new JLabel("Olá, " + usuarioLogado.getNome() + 
                                      (usuarioLogado instanceof AlunoVIP ? " ⭐" : ""));
        usuarioInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        usuarioInfo.setForeground(Color.WHITE);
        
        header.add(titulo, BorderLayout.WEST);
        header.add(usuarioInfo, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel criarMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(240, 240, 240));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        menu.setPreferredSize(new Dimension(200, 0));
        
        // Botões do menu
       String[] botoesMenu = {
          "📊 Meu Dashboard", "📚 Cursos Disponíveis", "🎫 Minhas Matrículas",
          "📋 Meu Histórico", "🏆 Certificados", "📈 Relatórios",
          "📝 Cadastrar Curso", "👤 Cadastrar Aluno", "⚙️ Configurações" 
        };
        
        for (String texto : botoesMenu) {
            JButton btn = new JButton(texto);
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.addActionListener(new MenuListener());
            menu.add(btn);
            menu.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        // Botão Sair
        JButton btnSair = new JButton("🚪 Sair");
        btnSair.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSair.setMaximumSize(new Dimension(180, 40));
        btnSair.setBackground(new Color(220, 80, 60));
        btnSair.setForeground(Color.BLACK);
        btnSair.setFocusPainted(false);
        btnSair.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        menu.add(Box.createVerticalGlue());
        menu.add(btnSair);
        
        return menu;
    }
    
    private JPanel criarAreaConteudo() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Cards de Estatísticas
        JPanel statsPanel = criarCardsEstatisticas();
        content.add(statsPanel, BorderLayout.NORTH);
        
        // Área principal
        JLabel bemVindo = new JLabel("Bem-vindo ao EducaOnline!", JLabel.CENTER);
        bemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        bemVindo.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        content.add(bemVindo, BorderLayout.CENTER);
        
        return content;
    }
    
    private JPanel criarCardsEstatisticas() {
        JPanel stats = new JPanel(new GridLayout(1, 4, 15, 15));
        
        String[][] dadosStats = {
            {"📚", "Cursos Matriculados", "5"},
            {"✅", "Cursos Concluídos", "2"},
            {"⭐", "VIP Status", usuarioLogado instanceof AlunoVIP ? "Ativo" : "Regular"},
            {"📊", "Média Geral", "8.5"}
        };
        
        for (String[] dado : dadosStats) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            
            JLabel icone = new JLabel(dado[0]);
            icone.setFont(new Font("Arial", Font.PLAIN, 24));
            icone.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel titulo = new JLabel(dado[1]);
            titulo.setFont(new Font("Arial", Font.PLAIN, 12));
            titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel valor = new JLabel(dado[2]);
            valor.setFont(new Font("Arial", Font.BOLD, 18));
            valor.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            card.add(icone);
            card.add(Box.createRigidArea(new Dimension(0, 10)));
            card.add(titulo);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(valor);
            
            stats.add(card);
        }
        
        return stats;
    }
    
    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = ((JButton) e.getSource()).getText();
            
            switch (comando) {
                case "📚 Cursos Disponíveis":
                    new CursoListagemFrame(usuarioLogado).setVisible(true);
                    break;
                case "🎫 Minhas Matrículas":
                    new MatriculaFrame(usuarioLogado).setVisible(true);
                    break;
                case "🏆 Certificados":
                    new CertificadoFrame(usuarioLogado).setVisible(true);
                    break;
                case "📈 Relatórios":
                    new RelatorioFrame(usuarioLogado).setVisible(true);
                    break;
                case "👤 Cadastrar Aluno":
                    new AlunoCadastroFrame().setVisible(true);
                    break;
                case "📝 Cadastrar Curso":
                    new CursoCadastroFrame().setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(DashboardFrame.this, 
                        "Funcionalidade em desenvolvimento: " + comando);
            }
        }
    }
}