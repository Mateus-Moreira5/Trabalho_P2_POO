package main.java.educaonline.menus;

import main.java.educaonline.services.*;

import java.util.Scanner;

public class MenuRelatorios {
    private Scanner scanner = new Scanner(System.in);
    private AlunoService alunoService = new AlunoService();
    private CursoService cursoService = new CursoService();
    private MatriculaService matriculaService = new MatriculaService();
    private PagamentoService pagamentoService = new PagamentoService();

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n=== RELATÓRIOS ===");
            System.out.println("1. Relatório de Alunos");
            System.out.println("2. Relatório de Cursos");
            System.out.println("3. Relatório de Matrículas");
            System.out.println("4. Relatório Financeiro");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    relatorioAlunos();
                    break;
                case 2:
                    relatorioCursos();
                    break;
                case 3:
                    relatorioMatriculas();
                    break;
                case 4:
                    relatorioFinanceiro();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void relatorioAlunos() {
        var alunos = alunoService.listarTodosAlunos();
        System.out.println("\n--- Relatório de Alunos ---");
        System.out.println("Total de alunos: " + alunos.size());
        long vipCount = alunos.stream().filter(a -> a.isVip()).count();
        System.out.println("Alunos VIP: " + vipCount);
        System.out.println("Alunos comuns: " + (alunos.size() - vipCount));
        
        for (var aluno : alunos) {
            System.out.println(aluno.getId() + " - " + aluno.getNome() + 
                             " - VIP: " + (aluno.isVip() ? "Sim" : "Não"));
        }
    }

    private void relatorioCursos() {
        var cursos = cursoService.listarTodosCursos();
        System.out.println("\n--- Relatório de Cursos ---");
        System.out.println("Total de cursos: " + cursos.size());
        long vipCount = cursos.stream().filter(c -> c.isExclusivoVip()).count();
        System.out.println("Cursos exclusivos VIP: " + vipCount);
        
        for (var curso : cursos) {
            System.out.println(curso.getId() + " - " + curso.getNome() + 
                             " - VIP: " + (curso.isExclusivoVip() ? "Sim" : "Não") +
                             " - Vagas: " + curso.getLimiteAlunos());
        }
    }

    private void relatorioMatriculas() {
        var matriculas = matriculaService.buscarMatriculasPorAluno(0); // Busca todas
        System.out.println("\n--- Relatório de Matrículas ---");
        System.out.println("Total de matrículas: " + matriculas.size());
        
        long ativas = matriculas.stream().filter(m -> "ATIVA".equals(m.getStatus())).count();
        long concluidas = matriculas.stream().filter(m -> "CONCLUIDA".equals(m.getStatus())).count();
        
        System.out.println("Matrículas ativas: " + ativas);
        System.out.println("Matrículas concluídas: " + concluidas);
    }

    private void relatorioFinanceiro() {
        pagamentoService.exibirRelatorioFinanceiro();
    }
}