package main.java.com.educaonline.controller;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class MatriculaController {
    
    public static boolean matricularAluno(String alunoEmail, String cursoId) {
        return DatabaseUtil.matricularAlunoCurso(alunoEmail, cursoId);
    }
    
    public static boolean cancelarMatricula(String alunoEmail, String cursoId) {
        try {
            Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
            Curso curso = DatabaseUtil.getCursoPorId(cursoId);
            
            if (aluno != null && curso != null) {
                aluno.cancelarMatricula(cursoId);
                
                System.out.println("Matrícula cancelada: " + alunoEmail + " no curso " + cursoId);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao cancelar matrícula: " + e.getMessage());
            return false;
        }
    }
    
    public static List<String> getCursosMatriculados(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno != null) {
            return aluno.getCursosMatriculados();
        }
        return new java.util.ArrayList<>();
    }
    
    public static boolean verificarMatriculaAtiva(String alunoEmail, String cursoId) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        return aluno != null && aluno.estaMatriculado(cursoId);
    }
    
    public static int getTotalMatriculasAtivas(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        return aluno != null ? aluno.getCursosMatriculados().size() : 0;
    }
    
    public static double calcularInvestimentoTotal(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return 0.0;
        
        double total = 0.0;
        for (String cursoId : aluno.getCursosMatriculados()) {
            Curso curso = DatabaseUtil.getCursoPorId(cursoId);
            if (curso != null) {
                total += curso.getPreco();
            }
        }
        return total;
    }
    
    public static List<Curso> getCursosMatriculadosComDetalhes(String alunoEmail) {
        List<Curso> cursos = new java.util.ArrayList<>();
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        
        if (aluno != null) {
            for (String cursoId : aluno.getCursosMatriculados()) {
                Curso curso = DatabaseUtil.getCursoPorId(cursoId);
                if (curso != null) {
                    cursos.add(curso);
                }
            }
        }
        return cursos;
    }
}