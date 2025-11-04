package main.java.com.educaonline.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.Aluno;
import main.java.com.educaonline.model.Curso;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class MatriculaFrame extends JFrame {
    private Usuario usuario;
    private JTable tableMatriculas;
    private DefaultTableModel tableModel;
    
    public MatriculaFrame(Usuario usuario) {
        this.usuario = usuario;
        configurarJanela();
        initComponents();
        carregarMatriculas();
    }
    
    private void configurarJanela() {
        setTitle("🎫 Minhas Matrículas - EducaOnline");
        setSize(900, 600);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Minhas Matrículas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titulo, BorderLayout.WEST);
        
        JButton btnAtualizar = new JButton("🔄 Atualizar");
        btnAtualizar.addActionListener(e -> carregarMatriculas());
        headerPanel.add(btnAtualizar, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Tabela de matrículas
        String[] colunas = {"Curso", "Categoria", "Data Matrícula", "Status", "Valor Pago", "Ações"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Apenas a coluna de ações é editável
            }
        };
        
        tableMatriculas = new JTable(tableModel);
        tableMatriculas.setRowHeight(35);
        tableMatriculas.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tableMatriculas.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane scrollPane = new JScrollPane(tableMatriculas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Painel de estatísticas
        mainPanel.add(criarPainelEstatisticas(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void carregarMatriculas() {
    tableModel.setRowCount(0);
    
    // CORREÇÃO: Usar instanceof em vez de cast direto
    if (usuario instanceof Aluno) {
        Aluno aluno = (Aluno) usuario;
        List<String> cursosMatriculados = aluno.getCursosMatriculados();
        
        for (String cursoId : cursosMatriculados) {
            Curso curso = DatabaseUtil.getCursoPorId(cursoId);
            if (curso != null) {
                Object[] rowData = {
                    curso.getTitulo(),
                    curso.getCategoria(),
                    "01/01/2024",
                    "Ativa",
                    String.format("R$ %.2f", curso.getPreco()),
                    "Cancelar"
                };
                tableModel.addRow(rowData);
            }
        }
    }
    
    if (tableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, 
            "Nenhuma matrícula encontrada.", 
            "Informação", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
    
    private JPanel criarPainelEstatisticas() {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        statsPanel.setBackground(new Color(240, 240, 240));
        
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(usuario.getEmail());
        int totalMatriculas = aluno != null ? aluno.getCursosMatriculados().size() : 0;
        
        JLabel lblStats = new JLabel(String.format(
            "📊 Estatísticas: %d matrícula(s) ativa(s) | 💰 Total investido: R$ %.2f",
            totalMatriculas, totalMatriculas * 299.90 // Valor fictício
        ));
        lblStats.setFont(new Font("Arial", Font.BOLD, 12));
        
        statsPanel.add(lblStats);
        return statsPanel;
    }
    
    // Classes para renderizar botões na tabela
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setBackground(new Color(220, 80, 60));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }
        
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.setBackground(new Color(200, 70, 50));
            button.setForeground(Color.WHITE);
            isPushed = true;
            return button;
        }
        
        public Object getCellEditorValue() {
            if (isPushed) {
                int resposta = JOptionPane.showConfirmDialog(null,
                    "Tem certeza que deseja cancelar esta matrícula?",
                    "Confirmar Cancelamento",
                    JOptionPane.YES_NO_OPTION);
                
                if (resposta == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, 
                        "Matrícula cancelada com sucesso!");
                    carregarMatriculas();
                }
            }
            isPushed = false;
            return label;
        }
    }
}