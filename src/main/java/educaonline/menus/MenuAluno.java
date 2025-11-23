package main.java.educaonline.menus;

import main.java.educaonline.intities.*;
import main.java.educaonline.services.*;
import java.util.List;
import java.util.Scanner;

public class MenuAluno {
    private Scanner scanner = new Scanner(System.in);
    private AlunoService alunoService = new AlunoService();
    private CursoService cursoService = new CursoService();
    private MatriculaService matriculaService = new MatriculaService();

    public void exibir() {
        System.out.print("Informe o ID do aluno: ");
        int alunoId = scanner.nextInt();
        scanner.nextLine();

        Aluno aluno = alunoService.buscarAlunoPorId(alunoId);
        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        int opcao;
        do {
            System.out.println("\n=== ÁREA DO ALUNO ===");
            System.out.println("Aluno: " + aluno.getNome() + (aluno.isVip() ? " (VIP)" : ""));
            System.out.println("1. Listar Cursos Disponíveis");
            System.out.println("2. Matricular em Curso");
            System.out.println("3. Meus Cursos");
            System.out.println("4. Histórico Acadêmico");
            System.out.println("5. Certificados");
            System.out.println("6. Suporte VIP (apenas VIPs)");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarCursosDisponiveis(aluno.isVip());
                    break;
                case 2:
                    matricularEmCurso(alunoId, aluno.isVip());
                    break;
                case 3:
                    meusCursos(alunoId);
                    break;
                case 4:
                    historicoAcademico(alunoId);
                    break;
                case 5:
                    emitirCertificados(alunoId);
                    break;
                case 6:
                    if (aluno.isVip()) {
                        suporteVip();
                    } else {
                        System.out.println("Acesso negado. Apenas alunos VIP.");
                    }
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarCursosDisponiveis(boolean isVip) {
        List<Curso> cursos = cursoService.listarCursosDisponiveis(isVip);
        System.out.println("\n--- Cursos Disponíveis ---");
        for (Curso curso : cursos) {
            System.out.println(curso.getId() + " - " + curso.getNome() + 
                             " (VIP: " + (curso.isExclusivoVip() ? "Sim" : "Não") + ")");
        }
    }

    private void matricularEmCurso(int alunoId, boolean isVip) {
        System.out.print("Informe o ID do curso: ");
        int cursoId = scanner.nextInt();
        scanner.nextLine();

        matriculaService.matricularAluno(alunoId, cursoId);
    }

    private void meusCursos(int alunoId) {
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorAluno(alunoId);
        System.out.println("\n--- Meus Cursos ---");
        for (Matricula matricula : matriculas) {
            Curso curso = cursoService.buscarCursoPorId(matricula.getCursoId());
            System.out.println(curso.getNome() + " - Status: " + matricula.getStatus() + 
                             " - Nota: " + matricula.getNota());
        }
    }

    private void historicoAcademico(int alunoId) {
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorAluno(alunoId);
        System.out.println("\n--- Histórico Acadêmico ---");
        for (Matricula matricula : matriculas) {
            Curso curso = cursoService.buscarCursoPorId(matricula.getCursoId());
            System.out.println("Curso: " + curso.getNome());
            System.out.println("Status: " + matricula.getStatus());
            System.out.println("Nota: " + matricula.getNota());
            System.out.println("Data: " + matricula.getDataMatricula());
            System.out.println("------------------------");
        }
    }

    private void emitirCertificados(int alunoId) {
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorAluno(alunoId);
        for (Matricula matricula : matriculas) {
            if ("CONCLUIDA".equals(matricula.getStatus()) && matricula.getNota() >= 6.0) {
                matriculaService.emitirCertificado(matricula.getId());
            }
        }
    }

    private void suporteVip() {
        System.out.println("\n--- Suporte VIP ---");
        System.out.println("Entre em contato pelo telefone 0800-VIP-EDUCA.");
        System.out.println("Ou agende uma mentoria exclusiva!");
    }
}