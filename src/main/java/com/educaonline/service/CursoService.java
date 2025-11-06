package main.java.com.educaonline.service;

import main.java.com.educaonline.model.Curso;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class CursoService {
    
    public static Curso criarCurso(String titulo, String categoria, String descricao, 
                                  double preco, int duracaoHoras, boolean exclusivoVIP, int vagas) {
        String cursoId = "C" + String.format("%03d", DatabaseUtil.getCursos().size() + 1);
        Curso curso = new Curso(cursoId, titulo, categoria, descricao, preco, duracaoHoras, exclusivoVIP, vagas);
        DatabaseUtil.getCursos().add(curso);
        return curso;
    }
    
    public static List<Curso> buscarCursosPorCategoria(String categoria) {
        return DatabaseUtil.getCursos().stream()
                .filter(curso -> curso.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }
    
    public static List<Curso> buscarCursosPorPreco(double precoMaximo) {
        return DatabaseUtil.getCursos().stream()
                .filter(curso -> curso.getPreco() <= precoMaximo)
                .toList();
    }
    
    public static boolean cursoExiste(String cursoId) {
        return DatabaseUtil.getCursoPorId(cursoId) != null;
    }
    
    public static int getTotalVagasOcupadas() {
        return DatabaseUtil.getCursos().stream()
                .mapToInt(curso -> curso.getVagasTotal() - curso.getVagasDisponiveis())
                .sum();
    }
}