package main.java.com.educaonline.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.educaonline.model.Usuario;

public class CertificadoFrame extends JFrame {
    private Usuario usuario;
    private JTable tableCertificados;
    private DefaultTableModel tableModel;
    
    public CertificadoFrame(Usuario usuario) {
        this.usuario = usuario;
        configurarJanela();
        initComponents();
        carregarCertificados();
    }
    
    private void configurarJanela() {
        setTitle(" Meus Certificados - EducaOnline");
        setSize(800, 500);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("Meus Certificados", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"Curso", "Data Conclusão", "Carga Horária", "Nota Final", "Status", "Ação"};
        tableModel = new DefaultTableModel(colunas, 0);
        
        tableCertificados = new JTable(tableModel);
        tableCertificados.setRowHeight(40);
        
        tableCertificados.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());
        tableCertificados.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tableCertificados.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane scrollPane = new JScrollPane(tableCertificados);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        infoPanel.setBackground(new Color(240, 240, 240));
        
        JLabel lblInfo = new JLabel("💡 Certificados disponíveis para cursos concluídos com nota ≥ 7.0");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        infoPanel.add(lblInfo);
        
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void carregarCertificados() {
        tableModel.setRowCount(0);
        
        Object[][] dadosExemplo = {
            {"Java para Iniciantes", "15/03/2024", "40 horas", "8.5", "Disponível", "Emitir"},
            {"Matemática para Programadores", "20/02/2024", "35 horas", "7.2", "Disponível", "Emitir"},
            {"UX/UI Design Avançado", "--", "45 horas", "6.8", "Em Andamento", "--"},
            {"Java Avançado e POO", "--", "60 horas", "--", "Cursando", "--"}
        };
        
        for (Object[] row : dadosExemplo) {
            tableModel.addRow(row);
        }
    }
    
    class StatusRenderer extends JLabel implements javax.swing.table.TableCellRenderer {
        public StatusRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setFont(new Font("Arial", Font.BOLD, 11));
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            String status = (String) value;
            setText(status);
            
            switch (status) {
                case "Disponível":
                    setBackground(new Color(200, 230, 200));
                    setForeground(new Color(0, 100, 0));
                    break;
                case "Em Andamento":
                    setBackground(new Color(255, 240, 200));
                    setForeground(new Color(150, 100, 0));
                    break;
                case "Cursando":
                    setBackground(new Color(200, 220, 255));
                    setForeground(new Color(0, 80, 180));
                    break;
                default:
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
            }
            
            return this;
        }
    }
    
    // Classes para botão de emitir certificado
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            if ("Emitir".equals(value)) {
                setBackground(new Color(0, 120, 215));
                setForeground(Color.WHITE);
                setEnabled(true);
            } else {
                setBackground(Color.LIGHT_GRAY);
                setForeground(Color.DARK_GRAY);
                setEnabled(false);
            }
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
            button.setBackground(new Color(0, 100, 200));
            button.setForeground(Color.WHITE);
            isPushed = true;
            return button;
        }
        
        public Object getCellEditorValue() {
            if (isPushed) {
                String curso = (String) tableModel.getValueAt(tableCertificados.getSelectedRow(), 0);
                
                JOptionPane.showMessageDialog(null, 
                    "Certificado emitido com sucesso!\n\n" +
                    "Curso: " + curso + "\n" +
                    "Aluno: " + usuario.getNome() + "\n" +
                    "Data de Emissão: " + java.time.LocalDate.now(),
                    "Certificado Emitido",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            isPushed = false;
            return label;
        }
    }
}