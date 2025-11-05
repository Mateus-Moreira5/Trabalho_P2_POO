package main.java.com.educaonline.view;

import javax.swing.*;
import java.awt.*;
import main.java.com.educaonline.model.Usuario;
import main.java.com.educaonline.model.Curso;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class CursoListagemFrame extends JFrame {
    private Usuario usuario;
    private JPanel cursosPanel;
    
    public CursoListagemFrame(Usuario usuario) {
        this.usuario = usuario;
        configurarJanela();
        initComponents();
        carregarCursos();
    }
    
    private void configurarJanela() {
        setTitle("Cursos Disponíveis - EducaOnline");
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titulo = new JLabel("Cursos Disponíveis", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // Painel de cursos com scroll
        cursosPanel = new JPanel();
        cursosPanel.setLayout(new BoxLayout(cursosPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(cursosPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void carregarCursos() {
        boolean isVIP = usuario instanceof main.java.com.educaonline.model.AlunoVIP;
        List<Curso> cursos = DatabaseUtil.getCursosDisponiveis(isVIP);
        
        cursosPanel.removeAll();
        
        if (cursos.isEmpty()) {
            JLabel semCursos = new JLabel("Nenhum curso disponível no momento.", JLabel.CENTER);
            semCursos.setFont(new Font("Arial", Font.PLAIN, 16));
            cursosPanel.add(semCursos);
        } else {
            for (Curso curso : cursos) {
                cursosPanel.add(criarCardCurso(curso));
                cursosPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        
        cursosPanel.revalidate();
        cursosPanel.repaint();
    }

    private JPanel criarCardCurso(Curso curso) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ));
    card.setMaximumSize(new Dimension(800, 120));
    
    JPanel infoPanel = new JPanel(new GridLayout(3, 1));
    infoPanel.setBackground(Color.WHITE);
    
    JLabel titulo = new JLabel(curso.getTitulo() + (curso.isExclusivoVIP() ? " [VIP]" : ""));
    titulo.setFont(new Font("Arial", Font.BOLD, 16));
    
    JLabel descricao = new JLabel(curso.getDescricao());
    descricao.setFont(new Font("Arial", Font.PLAIN, 12));
    descricao.setForeground(Color.GRAY);
    
    JLabel detalhes = new JLabel(String.format(
        "R$ %.2f | %d horas | %d/%d vagas | %s",
        curso.getPreco(), curso.getCargaHoraria(), 
        curso.getAlunosMatriculados(), curso.getLimiteVagas(),
        curso.getCategoria()
    ));
    detalhes.setFont(new Font("Arial", Font.PLAIN, 11));
    
    infoPanel.add(titulo);
    infoPanel.add(descricao);
    infoPanel.add(detalhes);
    
    JButton btnMatricular = new JButton("Matricular");
    btnMatricular.setBackground(new Color(0, 150, 0));
    btnMatricular.setForeground(Color.BLACK);
    btnMatricular.setFocusPainted(false);
    btnMatricular.addActionListener(e -> matricularCurso(curso));
    
    card.add(infoPanel, BorderLayout.CENTER);
    card.add(btnMatricular, BorderLayout.EAST);
    
    return card;
}  

    private void matricularCurso(Curso curso) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja se matricular no curso:\n" + curso.getTitulo() + "\n\nValor: R$ " + curso.getPreco(),
            "Confirmar Matrícula",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            boolean sucesso = DatabaseUtil.matricularAlunoCurso(usuario.getEmail(), curso.getId());
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Matrícula realizada com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                carregarCursos(); // Atualiza a lista
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao realizar matrícula. Verifique se há vagas disponíveis.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}