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
            ConsoleUtil.clearScreen();
            System.out.println("\n=== ADMINISTRAÇÃO ===");
            System.out.println("1. Cadastrar Curso");
            System.out.println("2. Listar Cursos");
            System.out.println("3. Cadastrar Aluno");
            System.out.println("4. Listar Alunos");
            System.out.println("5. Registrar Pagamento");
            System.out.println("6. Concluir Curso/Aluno");
            System.out.println("0. Voltar");
            
            opcao = InputUtil.readInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    if (!cadastrarCurso()) {
                        pausar();
                    }
                    break;
                case 2:
                    if (!listarCursos()) {
                        pausar();
                    }
                    break;
                case 3:
                    if (!cadastrarAluno()) {
                        pausar();
                    }
                    break;
                case 4:
                    if (!listarAlunos()) {
                        pausar();
                    }
                    break;
                case 5:
                    if (!registrarPagamento()) {
                        pausar();
                    }
                    break;
                case 6:
                    if (!concluirCurso()) {
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

    private boolean cadastrarCurso() {
        System.out.println("=== CADASTRO DE CURSO ===");
        String nome = InputUtil.readString("Nome do curso: ");
        if (nome.trim().isEmpty()) {
            System.out.println("Nome do curso não pode estar vazio!");
            return false;
        }
        
        String categoria = InputUtil.readString("Categoria: ");
        String area = InputUtil.readString("Área: ");
        int limite = InputUtil.readInt("Limite de alunos: ");
        
        if (limite <= 0) {
            System.out.println("Limite de alunos deve ser maior que zero!");
            return false;
        }
        
        boolean exclusivoVip = InputUtil.readSimNao("É exclusivo para VIP?");
        cursoService.cadastrarCurso(nome, categoria, area, limite, exclusivoVip);
        return true;
    }

    private boolean listarCursos() {
        var cursos = cursoService.listarTodosCursos();
        System.out.println("\n--- Todos os Cursos ---");
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return false;
        } else {
            for (var curso : cursos) {
                System.out.println("ID: " + curso.getId() + " | " + curso.getNome() + 
                             " | VIP: " + (curso.isExclusivoVip() ? "Sim" : "Não") +
                             " | Vagas: " + curso.getLimiteAlunos());
            }
            return true;
        }
    }

    private boolean cadastrarAluno() {
        System.out.println("=== CADASTRO DE ALUNO ===");
        String nome = InputUtil.readString("Nome do aluno: ");
        if (nome.trim().isEmpty()) {
            System.out.println("Nome do aluno não pode estar vazio!");
            return false;
        }
        
        String email = InputUtil.readString("Email: ");
        boolean vip = InputUtil.readSimNao("É VIP?");

        alunoService.cadastrarAluno(nome, email, vip);
        return true;
    }

    private boolean listarAlunos() {
        var alunos = alunoService.listarTodosAlunos();
        System.out.println("\n--- Todos os Alunos ---");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return false;
        } else {
            for (var aluno : alunos) {
                System.out.println("ID: " + aluno.getId() + " | " + aluno.getNome() + 
                             " | VIP: " + (aluno.isVip() ? "Sim" : "Não") +
                             " | Email: " + aluno.getEmail());
            }
            return true;
        }
    }

    private boolean registrarPagamento() {
        System.out.println("=== REGISTRO DE PAGAMENTO ===");
        if (!listarAlunos()) {
            System.out.println("Não há alunos cadastrados para registrar pagamento.");
            return false;
        }
        
        System.out.println();
        int alunoId = InputUtil.readInt("ID do aluno: ");
        double valor = InputUtil.readDouble("Valor: R$ ");
        String tipo = InputUtil.readString("Tipo (CURSO/ASSINATURA): ");

        return pagamentoService.registrarPagamento(alunoId, valor, tipo);
    }

    private boolean concluirCurso() {
        System.out.println("=== CONCLUSÃO DE CURSO ===");
        int matriculaId = InputUtil.readInt("ID da matrícula: ");
        double nota = InputUtil.readDouble("Nota final (0-10): ");

        return matriculaService.concluirCurso(matriculaId, nota);
    }
}