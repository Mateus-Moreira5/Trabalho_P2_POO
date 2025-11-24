package main.java.educaonline.menus;

import main.java.educaonline.services.*;

import java.util.Scanner;

public class MenuRelatorios {
    private Scanner scanner = new Scanner(System.in);

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.clearScreen();
            System.out.println("\n=== RELATÓRIOS ===");
            System.out.println("1. Relatório de Alunos");
            System.out.println("2. Relatório de Cursos");
            System.out.println("3. Relatório de Matrículas");
            System.out.println("4. Relatório Financeiro");
            System.out.println("0. Voltar");
            
            opcao = InputUtil.readInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    if (!relatorioAlunos()) {
                        pausar();
                    }
                    break;
                case 2:
                    if (!relatorioCursos()) {
                        pausar();
                    }
                    break;
                case 3:
                    if (!relatorioMatriculas()) {
                        pausar();
                    }
                    break;
                case 4:
                    if (!relatorioFinanceiro()) {
                        pausar();
                    }
                    break;
                case 0:
                    ConsoleUtil.clearScreen();
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    pausar();
            }
            
            if (opcao != 0) {
                pausar();
            }
        } while (opcao != 0);
    }

    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private boolean relatorioAlunos() {
        AlunoService alunoService = new AlunoService();
        var alunos = alunoService.listarTodosAlunos();
        System.out.println("\n--- Relatório de Alunos ---");
        System.out.println("Total de alunos: " + alunos.size());
        
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return false;
        }
        
        long vipCount = alunos.stream().filter(a -> a.isVip()).count();
        System.out.println("Alunos VIP: " + vipCount);
        System.out.println("Alunos comuns: " + (alunos.size() - vipCount));
        
        for (var aluno : alunos) {
            System.out.println("ID: " + aluno.getId() + " | " + aluno.getNome() + 
                         " | VIP: " + (aluno.isVip() ? "Sim" : "Não") +
                         " | Email: " + aluno.getEmail());
        }
        return true;
    }

    private boolean relatorioCursos() {
        CursoService cursoService = new CursoService();
        var cursos = cursoService.listarTodosCursos();
        System.out.println("\n--- Relatório de Cursos ---");
        System.out.println("Total de cursos: " + cursos.size());
        
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return false;
        }
        
        long vipCount = cursos.stream().filter(c -> c.isExclusivoVip()).count();
        System.out.println("Cursos exclusivos VIP: " + vipCount);
        
        for (var curso : cursos) {
            System.out.println("ID: " + curso.getId() + " | " + curso.getNome() + 
                         " | VIP: " + (curso.isExclusivoVip() ? "Sim" : "Não") +
                         " | Vagas: " + curso.getLimiteAlunos());
        }
        return true;
    }

    private boolean relatorioMatriculas() {
        MatriculaService matriculaService = new MatriculaService();
        var matriculas = matriculaService.buscarMatriculasPorAluno(0); // Busca todas
        System.out.println("\n--- Relatório de Matrículas ---");
        System.out.println("Total de matrículas: " + matriculas.size());
        
        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula encontrada.");
            return false;
        }
        
        long ativas = matriculas.stream().filter(m -> "ATIVA".equals(m.getStatus())).count();
        long concluidas = matriculas.stream().filter(m -> "CONCLUIDA".equals(m.getStatus())).count();
        
        System.out.println("Matrículas ativas: " + ativas);
        System.out.println("Matrículas concluídas: " + concluidas);
        return true;
    }

    private boolean relatorioFinanceiro() {
        PagamentoService pagamentoService = new PagamentoService();
        var pagamentos = pagamentoService.buscarPagamentosPorAluno(0); // Busca todos
        
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return false;
        }
        
        pagamentoService.exibirRelatorioFinanceiro();
        return true;
    }
}