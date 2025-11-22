package main.java.com.educaonline.controller;

import main.java.com.educaonline.model.Curso;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class CursoController {
    
    public static List<Curso> listarCursosDisponiveis(boolean isVIP) {
        return DatabaseUtil.getCursosDisponiveis(isVIP);
    }
    
    public static Curso buscarCursoPorId(String cursoId) {
        return DatabaseUtil.getCursoPorId(cursoId);
    }
    
    public static boolean cadastrarCurso(Curso curso) {
        try {
            DatabaseUtil.getCursos().add(curso);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar curso: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean verificarDisponibilidadeVagas(String cursoId) {
        Curso curso = buscarCursoPorId(cursoId);
        return curso != null && curso.temVagas();
    }
    
    public static List<Curso> filtrarCursosPorCategoria(String categoria, boolean isVIP) {
        List<Curso> cursosDisponiveis = DatabaseUtil.getCursosDisponiveis(isVIP);
        List<Curso> cursosFiltrados = new java.util.ArrayList<>();
        
        for (Curso curso : cursosDisponiveis) {
            if (curso.getCategoria().equalsIgnoreCase(categoria)) {
                cursosFiltrados.add(curso);
            }
        }
        return cursosFiltrados;
    }
    
    public static boolean atualizarCurso(String cursoId, Curso cursoAtualizado) {
        try {
            Curso cursoExistente = buscarCursoPorId(cursoId);
            if (cursoExistente != null) {
                DatabaseUtil.getCursos().remove(cursoExistente);
                DatabaseUtil.getCursos().add(cursoAtualizado);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluirCurso(String cursoId) {
        try {
            Curso curso = buscarCursoPorId(cursoId);
            if (curso != null) {
                DatabaseUtil.getCursos().remove(curso);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao excluir curso: " + e.getMessage());
            return false;
        }
    }
    
    public static int getTotalCursosDisponiveis(boolean isVIP) {
        return DatabaseUtil.getCursosDisponiveis(isVIP).size();
    }
}