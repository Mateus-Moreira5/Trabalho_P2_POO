package main.java.com.educaonline.service;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class RelatorioService {
    
    public static String gerarRelatorioFinanceiro() {
        List<Pagamento> pagamentos = DatabaseUtil.getPagamentos();
        List<Usuario> usuarios = DatabaseUtil.getUsuarios();
        
        // Calcular receita total
        double receitaTotal = 0.0;
        for (Pagamento pagamento : pagamentos) {
            if (pagamento.isPago()) {
                receitaTotal += pagamento.getValor();
            }
        }
        
        // Contar alunos VIP e regulares
        int totalAlunosVIP = 0;
        int totalAlunosRegular = 0;
        
        for (Usuario usuario : usuarios) {
            if (usuario instanceof AlunoVIP) {
                totalAlunosVIP++;
            } else if (usuario instanceof Aluno) {
                totalAlunosRegular++;
            }
        }
        
        int totalAlunos = totalAlunosVIP + totalAlunosRegular;
        double mediaPorAluno = totalAlunos > 0 ? receitaTotal / totalAlunos : 0;
        
        return String.format(
            "RELATÓRIO FINANCEIRO - EDUCACIONLINE\n\n" +
            "Receita Total: R$ %.2f\n" +
            "Total de Alunos: %d\n" +
            "Alunos VIP: %d\n" +
            "Alunos Regulares: %d\n" +
            "Pagamentos Processados: %d\n" +
            "Média por Aluno: R$ %.2f",
            receitaTotal, totalAlunos, totalAlunosVIP, totalAlunosRegular,
            pagamentos.size(), mediaPorAluno
        );
    }
    
    public static String gerarRelatorioMatriculas() {
        List<Usuario> usuarios = DatabaseUtil.getUsuarios();
        List<Curso> cursos = DatabaseUtil.getCursos();
        
        // Calcular total de matrículas
        int totalMatriculas = 0;
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Aluno) {
                Aluno aluno = (Aluno) usuario;
                totalMatriculas += aluno.getCursosMatriculados().size();
            }
        }
        
        // Contar cursos com vagas
        int cursosComVagas = 0;
        for (Curso curso : cursos) {
            if (curso.temVagas()) {
                cursosComVagas++;
            }
        }
        
        int cursosEsgotados = cursos.size() - cursosComVagas;
        double mediaCursosPorAluno = usuarios.size() > 0 ? (double) totalMatriculas / usuarios.size() : 0;
        
        return String.format(
            "RELATÓRIO DE MATRÍCULAS - EDUCACIONLINE\n\n" +
            "Total de Alunos: %d\n" +
            "Total de Cursos: %d\n" +
            "Total de Matrículas: %d\n" +
            "Cursos com Vagas: %d\n" +
            "Cursos Esgotados: %d\n" +
            "Média de Cursos por Aluno: %.1f",
            usuarios.size(), cursos.size(), totalMatriculas,
            cursosComVagas, cursosEsgotados, mediaCursosPorAluno
        );
    }
    
    public static String gerarRelatorioVIP() {
        List<Usuario> usuarios = DatabaseUtil.getUsuarios();
        List<Curso> cursos = DatabaseUtil.getCursos();
        
        // Contar alunos VIP
        int totalVIP = 0;
        for (Usuario usuario : usuarios) {
            if (usuario instanceof AlunoVIP) {
                totalVIP++;
            }
        }
        
        // Contar cursos exclusivos VIP
        int cursosVIP = 0;
        for (Curso curso : cursos) {
            if (curso.isExclusivoVIP()) {
                cursosVIP++;
            }
        }
        
        double percentualVIP = usuarios.size() > 0 ? (double) totalVIP / usuarios.size() * 100 : 0;
        
        return String.format(
            "RELATÓRIO VIP - EDUCACIONLINE\n\n" +
            "Total de Alunos VIP: %d\n" +
            "Percentual VIP: %.1f%%\n" +
            "Cursos Exclusivos VIP: %d\n" +
            "Benefícios VIP:\n" +
            "• 20%% de desconto em todos os cursos\n" +
            "• Acesso a cursos exclusivos\n" +
            "• Suporte prioritário\n" +
            "• Mentorias personalizadas",
            totalVIP, percentualVIP, cursosVIP
        );
    }
    
    public static String gerarRelatorioAluno(String alunoEmail) {
        Usuario usuario = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (usuario == null || !(usuario instanceof Aluno)) {
            return "Aluno não encontrado";
        }
        
        Aluno aluno = (Aluno) usuario;
        List<String> cursosMatriculados = aluno.getCursosMatriculados();
        int totalCursos = cursosMatriculados.size();
        
        // Calcular investimento total (simulação)
        double investimentoTotal = 0.0;
        for (String cursoId : cursosMatriculados) {
            Curso curso = DatabaseUtil.getCursoPorId(cursoId);
            if (curso != null) {
                double preco = curso.getPreco();
                if (aluno instanceof AlunoVIP) {
                    preco *= 0.8; // Desconto VIP
                }
                investimentoTotal += preco;
            }
        }
        
        boolean isVIP = aluno instanceof AlunoVIP;
        
        return String.format(
            "RELATÓRIO INDIVIDUAL - %s\n\n" +
            "Nome: %s\n" +
            "Email: %s\n" +
            "Tipo: %s\n" +
            "Cursos Matriculados: %d\n" +
            "Investimento Total: R$ %.2f\n" +
            "Status: %s",
            aluno.getNome().toUpperCase(),
            aluno.getNome(),
            aluno.getEmail(),
            isVIP ? "Aluno VIP ⭐" : "Aluno Regular",
            totalCursos,
            investimentoTotal,
            isVIP ? "VIP Ativo" : "Regular"
        );
    }
}