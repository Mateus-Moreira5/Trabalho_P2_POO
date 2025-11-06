package main.java.com.educaonline.service;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class RelatorioService {
    
    public static String gerarRelatorioMatriculas() {
        List<Aluno> alunos = DatabaseUtil.getAlunos();
        List<Curso> cursos = DatabaseUtil.getCursos();
        
        int totalMatriculas = alunos.stream()
                .mapToInt(aluno -> aluno.getCursosMatriculados().size())
                .sum();
        
        int alunosVIP = (int) alunos.stream()
                .filter(aluno -> aluno instanceof AlunoVIP)
                .count();
        
        return String.format(
            "RELATÓRIO DE MATRÍCULAS\n\n" +
            "Total de Alunos: %d\n" +
            "Alunos VIP: %d\n" +
            "Alunos Regulares: %d\n" +
            "Total de Cursos: %d\n" +
            "Total de Matrículas: %d\n" +
            "Taxa de Ocupação: %.1f%%",
            alunos.size(), alunosVIP, alunos.size() - alunosVIP,
            cursos.size(), totalMatriculas,
            ((double) totalMatriculas / (cursos.size() * 50)) * 100
        );
    }
    
    public static String gerarRelatorioFinanceiro() {
        List<Curso> cursos = DatabaseUtil.getCursos();
        double receitaTotal = cursos.stream()
                .mapToDouble(curso -> curso.getPreco() * (curso.getVagasTotal() - curso.getVagasDisponiveis()))
                .sum();
        
        double receitaVIP = cursos.stream()
                .filter(Curso::isExclusivoVIP)
                .mapToDouble(curso -> curso.getPreco() * (curso.getVagasTotal() - curso.getVagasDisponiveis()))
                .sum();
        
        return String.format(
            "RELATÓRIO FINANCEIRO\n\n" +
            "Receita Total: R$ %.2f\n" +
            "Receita VIP: R$ %.2f\n" +
            "Receita Regular: R$ %.2f\n" +
            "Cursos VIP: %d\n" +
            "Cursos Regulares: %d",
            receitaTotal, receitaVIP, receitaTotal - receitaVIP,
            cursos.stream().filter(Curso::isExclusivoVIP).count(),
            cursos.stream().filter(curso -> !curso.isExclusivoVIP()).count()
        );
    }
    
    public static String gerarRelatorioDesempenho(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return "Aluno não encontrado";
        
        int cursosMatriculados = aluno.getCursosMatriculados().size();
        int cursosConcluidos = 2;
        double mediaGeral = 8.2;
        
        return String.format(
            "RELATÓRIO DE DESEMPENHO\n\n" +
            "Aluno: %s\n" +
            "Email: %s\n\n" +
            "Cursos Matriculados: %d\n" +
            "Cursos Concluídos: %d\n" +
            "Média Geral: %.1f\n" +
            "Taxa de Conclusão: %.1f%%",
            aluno.getNome(), aluno.getEmail(),
            cursosMatriculados, cursosConcluidos,
            mediaGeral, ((double) cursosConcluidos / cursosMatriculados) * 100
        );
    }
}