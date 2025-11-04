package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.Aluno;
import main.java.com.educaonline.util.DatabaseUtil;

public class RelatorioFrame extends JFrame {
    private Usuario usuario;
    
    public RelatorioFrame(Usuario usuario) {
        this.usuario = usuario;
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setTitle("📈 Relatórios - EducaOnline");
        setSize(700, 500);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Header
        JLabel titulo = new JLabel("Relatórios e Estatísticas", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // Cards de relatórios
        mainPanel.add(criarCardsRelatorios(), BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel criarCardsRelatorios() {
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        
        String[][] relatorios = {
            {"📊", "Relatório de Desempenho", "Analise seu progresso nos cursos", "GERAR DESEMPENHO"},
            {"💰", "Relatório Financeiro", "Extrato de pagamentos e investimentos", "GERAR FINANCEIRO"},
            {"📚", "Histórico Acadêmico", "Todos os cursos e notas obtidas", "GERAR HISTÓRICO"},
            {"🎯", "Metas de Aprendizado", "Acompanhamento de objetivos", "GERAR METAS"}
        };
        
        for (String[] relatorio : relatorios) {
            JPanel card = criarCardRelatorio(relatorio[0], relatorio[1], relatorio[2], relatorio[3]);
            cardsPanel.add(card);
        }
        
        return cardsPanel;
    }
    
    private JPanel criarCardRelatorio(String icone, String titulo, String descricao, String acao) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("Arial", Font.PLAIN, 32));
        lblIcone.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDescricao = new JLabel("<html><center>" + descricao + "</center></html>");
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDescricao.setForeground(Color.GRAY);
        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnGerar = new JButton(acao);
        btnGerar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGerar.setBackground(new Color(0, 120, 215));
        btnGerar.setForeground(Color.WHITE);
        btnGerar.setFocusPainted(false);
        btnGerar.setMaximumSize(new Dimension(180, 35));
        
        btnGerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarRelatorio(titulo);
            }
        });
        
        card.add(lblIcone);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(lblDescricao);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(btnGerar);
        
        return card;
    }
    
    private void gerarRelatorio(String tipoRelatorio) {
        // Simular geração de relatório
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(usuario.getEmail());
        int totalCursos = aluno != null ? aluno.getCursosMatriculados().size() : 0;
        
        String relatorio = "";
        String titulo = "";
        
        switch (tipoRelatorio) {
            case "Relatório de Desempenho":
                titulo = "📊 Relatório de Desempenho";
                relatorio = String.format(
                    "Aluno: %s\nEmail: %s\n\n" +
                    "📈 Estatísticas de Desempenho:\n" +
                    "• Cursos Matriculados: %d\n" +
                    "• Cursos Concluídos: 2\n" +
                    "• Média Geral: 8.2\n" +
                    "• Taxa de Conclusão: 75%%\n\n" +
                    "🎯 Recomendações:\n" +
                    "• Continue com o bom desempenho\n" +
                    "• Explore cursos da categoria Programação",
                    usuario.getNome(), usuario.getEmail(), totalCursos
                );
                break;
                
            case "Relatório Financeiro":
                titulo = "💰 Relatório Financeiro";
                relatorio = String.format(
                    "Aluno: %s\nPeríodo: Jan 2024 - Mar 2024\n\n" +
                    "💵 Investimento em Educação:\n" +
                    "• Total Investido: R$ 1.499,50\n" +
                    "• Cursos Ativos: %d\n" +
                    "• Economia VIP: R$ 300,00\n\n" +
                    "📋 Detalhamento:\n" +
                    "• Java para Iniciantes - R$ 299,90\n" +
                    "• Java Avançado - R$ 499,90\n" +
                    "• Matemática - R$ 349,90\n" +
                    "• UX/UI Design - R$ 449,90",
                    usuario.getNome(), totalCursos
                );
                break;
                
            case "Histórico Acadêmico":
                titulo = "📚 Histórico Acadêmico";
                relatorio = String.format(
                    "HISTÓRICO ACADÊMICO\n\n" +
                    "Aluno: %s\nData de Emissão: %s\n\n" +
                    "CURSOS CONCLUÍDOS:\n" +
                    "1. Java para Iniciantes\n" +
                    "   • Nota: 8.5 | Status: Aprovado\n" +
                    "   • Conclusão: 15/03/2024\n\n" +
                    "2. Matemática para Programadores\n" +
                    "   • Nota: 7.2 | Status: Aprovado\n" +
                    "   • Conclusão: 20/02/2024\n\n" +
                    "CURSOS EM ANDAMENTO:\n" +
                    "• Java Avançado e POO\n" +
                    "• UX/UI Design Avançado",
                    usuario.getNome(), java.time.LocalDate.now()
                );
                break;
                
            case "Metas de Aprendizado":
                titulo = "🎯 Metas de Aprendizado";
                relatorio = String.format(
                    "PLANO DE APRENDIZADO\n\n" +
                    "Aluno: %s\nData: %s\n\n" +
                    "🎯 Metas para 2024:\n" +
                    "✅ Concluir trilha Java Básico\n" +
                    "🔄 Concluir Java Avançado (75%%)\n" +
                    "⏳ Iniciar Spring Framework\n" +
                    "🎯 Certificação Java OCA\n\n" +
                    "📅 Próximos Passos:\n" +
                    "• Revisar POO - Prazo: 30/04/2024\n" +
                    "• Projeto Prático - Prazo: 15/05/2024",
                    usuario.getNome(), java.time.LocalDate.now()
                );
                break;
        }
        
        // Exibir relatório em uma área de texto
        JTextArea areaRelatorio = new JTextArea(relatorio);
        areaRelatorio.setEditable(false);
        areaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaRelatorio.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(areaRelatorio);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}