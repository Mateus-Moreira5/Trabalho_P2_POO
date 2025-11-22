package main.java.com.educaonline.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import main.java.com.educaonline.model.*;
import main.java.com.educaonline.service.MatriculaService;
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
                return column == 5;
            }
        };
        
        tableMatriculas = new JTable(tableModel);
        tableMatriculas.setRowHeight(35);
        tableMatriculas.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tableMatriculas.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane scrollPane = new JScrollPane(tableMatriculas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void carregarMatriculas() {
        tableModel.setRowCount(0);
        
        if (usuario instanceof Aluno) {
            Aluno aluno = (Aluno) usuario;
            List<String> cursosMatriculados = aluno.getCursosMatriculados();
            
            for (String cursoId : cursosMatriculados) {
                Curso curso = DatabaseUtil.getCursoPorId(cursoId);
                if (curso != null) {
                    Object[] rowData = {
                        curso.getTitulo(),
                        curso.getCategoria(),
                        "01/01/2024", // Data fictícia - pode ser melhorada
                        "Ativa",
                        curso.getPrecoFormatado(),
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
    
    // Classes para botões na tabela (mantidas do código original)
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
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
            button.addActionListener(e -> fireEditingStopped());
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
                    // 🔥 INTEGRAÇÃO: Cancelar matrícula real
                    String cursoNome = (String) tableModel.getValueAt(tableMatriculas.getSelectedRow(), 0);
                    boolean sucesso = MatriculaService.cancelarMatricula(usuario.getEmail(), obterCursoIdPorNome(cursoNome));
                    
                    if (sucesso) {
                        JOptionPane.showMessageDialog(null, "Matrícula cancelada com sucesso!");
                        carregarMatriculas();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao cancelar matrícula.");
                    }
                }
            }
            isPushed = false;
            return label;
        }
        
        private String obterCursoIdPorNome(String cursoNome) {
            // Método auxiliar para encontrar ID do curso pelo nome
            for (Curso curso : DatabaseUtil.getCursos()) {
                if (curso.getTitulo().equals(cursoNome)) {
                    return curso.getId();
                }
            }
            return null;
        }
    }
}