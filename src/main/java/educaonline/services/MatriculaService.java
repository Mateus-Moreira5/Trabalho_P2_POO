package main.java.educaonline.services;

import main.java.educaonline.intities.*;
import main.java.educaonline.repositories.*;
import java.time.LocalDate;
import java.util.List;

public class MatriculaService {
    private MatriculaRepository matriculaRepository = new MatriculaRepository();
    private CursoService cursoService = new CursoService();
    private AlunoService alunoService = new AlunoService();
    
    public boolean matricularAluno(int alunoId, int cursoId) {
        // Verificar se aluno existe
        if (alunoService.buscarAlunoPorId(alunoId) == null) {
            System.out.println("Aluno não encontrado!");
            return false;
        }
        
        // Verificar se curso existe
        var curso = cursoService.buscarCursoPorId(cursoId);
        if (curso == null) {
            System.out.println("Curso não encontrado!");
            return false;
        }
        
        // Verificar se aluno é VIP para cursos exclusivos
        if (curso.isExclusivoVip() && !alunoService.verificarAcessoVIP(alunoId)) {
            System.out.println("Curso exclusivo para alunos VIP!");
            return false;
        }
        
        // Verificar vagas disponíveis
        List<Matricula> matriculasNoCurso = matriculaRepository.buscarPorCursoId(cursoId);
        long vagasOcupadas = matriculasNoCurso.stream()
                .filter(m -> "ATIVA".equals(m.getStatus()))
                .count();
        
        if (vagasOcupadas >= curso.getLimiteAlunos()) {
            System.out.println("Não há vagas disponíveis neste curso!");
            return false;
        }
        
        // Criar matrícula
        Matricula matricula = new Matricula();
        matricula.setId(matriculaRepository.getProximoId());
        matricula.setAlunoId(alunoId);
        matricula.setCursoId(cursoId);
        matricula.setDataMatricula(LocalDate.now());
        matricula.setStatus("ATIVA");
        matricula.setNota(0.0);
        
        matriculaRepository.salvar(matricula);
        System.out.println("Matrícula realizada com sucesso!");
        return true;
    }
    
    public boolean concluirCurso(int matriculaId, double nota) {
        if (nota < 0 || nota > 10) {
            System.out.println("Nota inválida! Deve ser entre 0 e 10.");
            return false;
        }
        
        List<Matricula> matriculas = matriculaRepository.carregarTodos();
        for (Matricula matricula : matriculas) {
            if (matricula.getId() == matriculaId) {
                matricula.setStatus("CONCLUIDA");
                matricula.setNota(nota);
                matriculaRepository.atualizar(matricula);
                System.out.println("Curso concluído com nota: " + nota);
                return true;
            }
        }
        System.out.println("Matrícula não encontrada!");
        return false;
    }
    
    public List<Matricula> buscarMatriculasPorAluno(int alunoId) {
        return matriculaRepository.buscarPorAlunoId(alunoId);
    }
    
    public boolean emitirCertificado(int matriculaId) {
        Matricula matricula = null;
        List<Matricula> matriculas = matriculaRepository.carregarTodos();
        for (Matricula m : matriculas) {
            if (m.getId() == matriculaId) {
                matricula = m;
                break;
            }
        }
        
        if (matricula == null) {
            System.out.println("Matrícula não encontrada!");
            return false;
        }
        
        if (!"CONCLUIDA".equals(matricula.getStatus())) {
            System.out.println("Curso não foi concluído!");
            return false;
        }
        
        if (matricula.getNota() < 6.0) {
            System.out.println("Nota insuficiente para certificado (mínimo 6.0)!");
            return false;
        }
        
        var curso = cursoService.buscarCursoPorId(matricula.getCursoId());
        var aluno = alunoService.buscarAlunoPorId(matricula.getAlunoId());
        
        System.out.println("\n=== CERTIFICADO ===");
        System.out.println("Certificamos que " + aluno.getNome());
        System.out.println("Concluiu o curso: " + curso.getNome());
        System.out.println("Com nota: " + matricula.getNota());
        System.out.println("Data de conclusão: " + matricula.getDataMatricula());
        System.out.println("Código do certificado: CERT-" + matriculaId);
        System.out.println("===================\n");
        return true;
    }
}