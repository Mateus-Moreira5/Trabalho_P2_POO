package main.java.educaonline.menus;

import main.java.educaonline.services.*;

import java.util.Scanner;

public class MenuAdmin {
    private Scanner scanner = new Scanner(System.in);
    private AlunoService alunoService = new AlunoService();
    private CursoService cursoService = new CursoService();
    private PagamentoService pagamentoService = new PagamentoService();
    private MatriculaService matriculaService = new MatriculaService();

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n=== ADMINISTRAÇÃO ===");
            System.out.println("1. Cadastrar Curso");
            System.out.println("2. Listar Cursos");
            System.out.println("3. Cadastrar Aluno");
            System.out.println("4. Listar Alunos");
            System.out.println("5. Registrar Pagamento");
            System.out.println("6. Concluir Curso/Aluno");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCurso();
                    break;
                case 2:
                    listarCursos();
                    break;
                case 3:
                    cadastrarAluno();
                    break;
                case 4:
                    listarAlunos();
                    break;
                case 5:
                    registrarPagamento();
                    break;
                case 6:
                    concluirCurso();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarCurso() {
        System.out.print("Nome do curso: ");
        String nome = scanner.nextLine();
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();
        System.out.print("Área: ");
        String area = scanner.nextLine();
        System.out.print("Limite de alunos: ");
        int limite = scanner.nextInt();
        scanner.nextLine();
        System.out.print("É exclusivo para VIP? (true/false): ");
        boolean exclusivoVip = scanner.nextBoolean();
        scanner.nextLine();

        cursoService.cadastrarCurso(nome, categoria, area, limite, exclusivoVip);
    }

    private void listarCursos() {
        var cursos = cursoService.listarTodosCursos();
        System.out.println("\n--- Todos os Cursos ---");
        for (var curso : cursos) {
            System.out.println(curso.getId() + " - " + curso.getNome() + 
                             " - VIP: " + (curso.isExclusivoVip() ? "Sim" : "Não") +
                             " - Vagas: " + curso.getLimiteAlunos());
        }
    }

    private void cadastrarAluno() {
        System.out.print("Nome do aluno: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("É VIP? (true/false): ");
        boolean vip = scanner.nextBoolean();
        scanner.nextLine();

        alunoService.cadastrarAluno(nome, email, vip);
    }

    private void listarAlunos() {
        var alunos = alunoService.listarTodosAlunos();
        System.out.println("\n--- Todos os Alunos ---");
        for (var aluno : alunos) {
            System.out.println(aluno.getId() + " - " + aluno.getNome() + 
                             " - VIP: " + (aluno.isVip() ? "Sim" : "Não"));
        }
    }

    private void registrarPagamento() {
        System.out.print("ID do aluno: ");
        int alunoId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Tipo (CURSO/ASSINATURA): ");
        String tipo = scanner.nextLine();

        pagamentoService.registrarPagamento(alunoId, valor, tipo);
    }

    private void concluirCurso() {
        System.out.print("ID da matrícula: ");
        int matriculaId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nota final: ");
        double nota = scanner.nextDouble();
        scanner.nextLine();

        matriculaService.concluirCurso(matriculaId, nota);
    }
}