package main.java.com.educaonline.service;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class MatriculaService {
    
    public static boolean matricularAluno(String alunoEmail, String cursoId) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        Curso curso = DatabaseUtil.getCursoPorId(cursoId);
        
        if (aluno == null || curso == null) {
            return false;
        }
        
        if (curso.isExclusivoVIP() && !(aluno instanceof AlunoVIP)) {
            return false;
        }
        
        if (!curso.temVagas()) {
            return false;
        }
        
        if (aluno.estaMatriculado(cursoId)) {
            return false;
        }
        
        boolean matriculado = curso.matricularAluno();
        if (matriculado) {
            aluno.matricularCurso(cursoId);
            return true;
        }
        
        return false;
    }
    
    public static boolean cancelarMatricula(String alunoEmail, String cursoId) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        Curso curso = DatabaseUtil.getCursoPorId(cursoId);
        
        if (aluno == null || curso == null) {
            return false;
        }
        
        if (!aluno.estaMatriculado(cursoId)) {
            return false;
        }
        
        curso.cancelarMatricula();
        aluno.cancelarMatricula(cursoId);
        return true;
    }
    
    public static List<Curso> getCursosMatriculados(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return List.of();
        
        return aluno.getCursosMatriculados().stream()
                .map(DatabaseUtil::getCursoPorId)
                .filter(curso -> curso != null)
                .toList();
    }
}