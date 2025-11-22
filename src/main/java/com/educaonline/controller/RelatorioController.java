package main.java.com.educaonline.controller;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class RelatorioController {
    
    public static String gerarRelatorioDesempenho(String alunoEmail) {
        StringBuilder relatorio = new StringBuilder();
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        
        if (aluno != null) {
            relatorio.append("=== RELATÓRIO DE DESEMPENHO ===\n\n");
            relatorio.append("Aluno: ").append(aluno.getNome()).append("\n");
            relatorio.append("Email: ").append(aluno.getEmail()).append("\n");
            relatorio.append("Tipo: ").append(aluno.isVip() ? "VIP " : "Regular").append("\n\n");
            
            List<String> cursosMatriculados = aluno.getCursosMatriculados();
            relatorio.append(" ESTATÍSTICAS:\n");
            relatorio.append("• Cursos Matriculados: ").append(cursosMatriculados.size()).append("\n");
            relatorio.append("• Cursos Concluídos: ").append(calcularCursosConcluidos(alunoEmail)).append("\n");
            relatorio.append("• Média Geral: ").append(calcularMediaGeral(alunoEmail)).append("\n");
            relatorio.append("• Taxa de Conclusão: ").append(calcularTaxaConclusao(alunoEmail)).append("%\n\n");
            
            relatorio.append(" RECOMENDAÇÕES:\n");
            if (cursosMatriculados.size() < 3) {
                relatorio.append("• Explore mais cursos da plataforma\n");
            }
            relatorio.append("• Mantenha a consistência nos estudos\n");
            relatorio.append("• Participe das atividades práticas\n");
        }
        
        return relatorio.toString();
    }
    
    public static String gerarRelatorioFinanceiro(String alunoEmail) {
        StringBuilder relatorio = new StringBuilder();
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        
        if (aluno != null) {
            double totalInvestido = MatriculaController.calcularInvestimentoTotal(alunoEmail);
            double economiaVIP = aluno.isVip() ? totalInvestido * 0.2 : 0;
            
            relatorio.append("== RELATÓRIO FINANCEIRO ==\n\n");
            relatorio.append("Aluno: ").append(aluno.getNome()).append("\n");
            relatorio.append("Período: Jan 2024 - Mar 2024\n\n");
            
            relatorio.append("INVESTIMENTO EM EDUCAÇÃO:\n");
            relatorio.append("• Total Investido: R$ ").append(String.format("%.2f", totalInvestido)).append("\n");
            relatorio.append("• Cursos Ativos: ").append(aluno.getCursosMatriculados().size()).append("\n");
            
            if (aluno.isVip()) {
                relatorio.append("• Economia VIP: R$ ").append(String.format("%.2f", economiaVIP)).append("\n");
            }
            
            relatorio.append("\n DETALHAMENTO DOS CURSOS:\n");
            for (String cursoId : aluno.getCursosMatriculados()) {
                Curso curso = DatabaseUtil.getCursoPorId(cursoId);
                if (curso != null) {
                    double precoComDesconto = aluno.isVip() ? curso.getPreco() * 0.8 : curso.getPreco();
                    relatorio.append("• ").append(curso.getTitulo())
                            .append(" - R$ ").append(String.format("%.2f", precoComDesconto))
                            .append(aluno.isVip() ? " (com desconto VIP)" : "").append("\n");
                }
            }
        }
        
        return relatorio.toString();
    }
    
    public static String gerarRelatorioAdministrativo() {
        StringBuilder relatorio = new StringBuilder();
        
        relatorio.append("=== RELATÓRIO ADMINISTRATIVO ===\n\n");
        relatorio.append("ESTATÍSTICAS GERAIS DA PLATAFORMA:\n");
        relatorio.append("• Total de Alunos: ").append(DatabaseUtil.getAlunos().size()).append("\n");
        relatorio.append("• Alunos VIP: ").append(DatabaseUtil.getAlunosVIP().size()).append("\n");
        relatorio.append("• Total de Professores: ").append(DatabaseUtil.getProfessores().size()).append("\n");
        relatorio.append("• Total de Cursos: ").append(DatabaseUtil.getCursos().size()).append("\n");
        
        relatorio.append("\n DISTRIBUIÇÃO POR CATEGORIA:\n");
        Map<String, Integer> cursosPorCategoria = contarCursosPorCategoria();
        for (Map.Entry<String, Integer> entry : cursosPorCategoria.entrySet()) {
            relatorio.append("• ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" cursos\n");
        }
        
        relatorio.append("\n FATURAMENTO ESTIMADO:\n");
        double faturamentoTotal = calcularFaturamentoTotal();
        relatorio.append("• Faturamento Total: R$ ").append(String.format("%.2f", faturamentoTotal)).append("\n");
        relatorio.append("• Faturamento Médio por Aluno: R$ ").append(String.format("%.2f", faturamentoTotal / DatabaseUtil.getAlunos().size())).append("\n");
        
        return relatorio.toString();
    }

    private static int calcularCursosConcluidos(String alunoEmail) {
        return 2;
    }
    
    private static double calcularMediaGeral(String alunoEmail) {
        return 8.2;
    }
    
    private static double calcularTaxaConclusao(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return 0.0;
        
        int totalCursos = aluno.getCursosMatriculados().size();
        int cursosConcluidos = calcularCursosConcluidos(alunoEmail);
        
        return totalCursos > 0 ? (cursosConcluidos * 100.0 / totalCursos) : 0.0;
    }
    
    private static Map<String, Integer> contarCursosPorCategoria() {
        Map<String, Integer> categorias = new HashMap<>();
        for (Curso curso : DatabaseUtil.getCursos()) {
            String categoria = curso.getCategoria();
            categorias.put(categoria, categorias.getOrDefault(categoria, 0) + 1);
        }
        return categorias;
    }
    
    private static double calcularFaturamentoTotal() {
        double total = 0.0;
        for (Aluno aluno : DatabaseUtil.getAlunos()) {
            total += MatriculaController.calcularInvestimentoTotal(aluno.getEmail());
        }
        for (AlunoVIP alunoVIP : DatabaseUtil.getAlunosVIP()) {
            total += MatriculaController.calcularInvestimentoTotal(alunoVIP.getEmail());
        }
        return total;
    }
}