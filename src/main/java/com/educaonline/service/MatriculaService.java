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
        
        // Verificar se é VIP para cursos exclusivos
        if (curso.isExclusivoVIP() && !(aluno instanceof AlunoVIP)) {
            return false;
        }
        
        // Verificar se há vagas
        if (!curso.temVagas()) {
            return false;
        }
        
        // Verificar se já está matriculado
        if (aluno.estaMatriculado(cursoId)) {
            return false;
        }
        
        // Efetuar matrícula
        boolean sucessoMatricula = curso.matricularAluno();
        if (sucessoMatricula) {
            aluno.matricularCurso(cursoId);
            
            // Criar registro de matrícula
            String matriculaId = "MAT" + System.currentTimeMillis();
            Matricula matricula = new Matricula(matriculaId, aluno.getId(), cursoId, "TURMA001", curso.getPreco());
            DatabaseUtil.adicionarMatricula(matricula);
            
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
        
        // Cancelar matrícula
        curso.cancelarMatricula();
        aluno.cancelarMatricula(cursoId);
        return true;
    }
    
    public static List<Curso> getCursosDoAluno(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return List.of();
        
        return aluno.getCursosMatriculados().stream()
                .map(DatabaseUtil::getCursoPorId)
                .filter(curso -> curso != null)
                .toList();
    }
    
    public static List<Curso> getCursosDisponiveis(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return List.of();
        
        boolean isVIP = aluno instanceof AlunoVIP;
        return DatabaseUtil.getCursosDisponiveis(isVIP);
    }
}