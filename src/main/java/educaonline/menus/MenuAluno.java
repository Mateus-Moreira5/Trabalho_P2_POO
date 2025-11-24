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
        ConsoleUtil.clearScreen();
        
        // Mostrar alunos cadastrados para referência
        listarAlunosParaReferencia();
        
        int alunoId = InputUtil.readInt("Informe o ID do aluno: ");

        Aluno aluno = alunoService.buscarAlunoPorId(alunoId);
        if (aluno == null) {
            System.out.println("Aluno não encontrado! Verifique o ID na lista acima.");
            System.out.println("Pressione ENTER para continuar...");
            scanner.nextLine();
            return; // Volta para o menu principal
        }

        int opcao;
        do {
            ConsoleUtil.clearScreen();
            System.out.println("\n=== ÁREA DO ALUNO ===");
            System.out.println("Aluno: " + aluno.getNome() + (aluno.isVip() ? " (VIP)" : ""));
            System.out.println("ID: " + aluno.getId() + " | Email: " + aluno.getEmail());
            System.out.println("=================================");
            System.out.println("1. Listar Cursos Disponíveis");
            System.out.println("2. Matricular em Curso");
            System.out.println("3. Meus Cursos");
            System.out.println("4. Histórico Acadêmico");
            System.out.println("5. Certificados");
            System.out.println("6. Suporte VIP (apenas VIPs)");
            System.out.println("0. Voltar");
            
            opcao = InputUtil.readInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    if (!listarCursosDisponiveis(aluno.isVip())) {
                        pausar();
                    }
                    break;
                case 2:
                    if (!matricularEmCurso(alunoId, aluno.isVip())) {
                        pausar();
                    }
                    break;
                case 3:
                    if (!meusCursos(alunoId)) {
                        pausar();
                    }
                    break;
                case 4:
                    if (!historicoAcademico(alunoId)) {
                        pausar();
                    }
                    break;
                case 5:
                    if (!emitirCertificados(alunoId)) {
                        pausar();
                    }
                    break;
                case 6:
                    if (!suporteVip(aluno.isVip())) {
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
            
            if (opcao != 0 && opcao != 2) { // Não pausa após matrícula (já pausou se falhou)
                pausar();
            }
        } while (opcao != 0);
    }

    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private void listarAlunosParaReferencia() {
        List<Aluno> alunos = alunoService.listarTodosAlunos();
        if (alunos.isEmpty()) {
            System.out.println("=== ATENÇÃO ===");
            System.out.println("Nenhum aluno cadastrado ainda!");
            System.out.println("Acesse Administração → Cadastrar Aluno primeiro.");
            System.out.println("================\n");
        } else {
            System.out.println("=== ALUNOS CADASTRADOS (para referência) ===");
            for (Aluno aluno : alunos) {
                System.out.println("ID: " + aluno.getId() + " | Nome: " + aluno.getNome() + 
                                 " | VIP: " + (aluno.isVip() ? "Sim" : "Não"));
            }
            System.out.println("============================================\n");
        }
    }

    private boolean listarCursosDisponiveis(boolean isVip) {
        List<Curso> cursos = cursoService.listarCursosDisponiveis(isVip);
        System.out.println("\n--- Cursos Disponíveis ---");
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso disponível.");
            return false;
        } else {
            for (Curso curso : cursos) {
                System.out.println("ID: " + curso.getId() + " | " + curso.getNome() + 
                                 " | VIP: " + (curso.isExclusivoVip() ? "Sim" : "Não") +
                                 " | Vagas: " + curso.getLimiteAlunos());
            }
            return true;
        }
    }

    private boolean matricularEmCurso(int alunoId, boolean isVip) {
        if (!listarCursosDisponiveis(isVip)) {
            System.out.println("Não há cursos disponíveis para matrícula.");
            return false;
        }
        
        System.out.println();
        int cursoId = InputUtil.readInt("Informe o ID do curso (0 para cancelar): ");
        
        if (cursoId == 0) {
            System.out.println("Operação cancelada.");
            return true; // Cancelamento não é erro
        }
        
        boolean sucesso = matriculaService.matricularAluno(alunoId, cursoId);
        if (!sucesso) {
            System.out.println("Falha na matrícula!");
        }
        return sucesso;
    }

    private boolean meusCursos(int alunoId) {
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorAluno(alunoId);
        System.out.println("\n--- Meus Cursos ---");
        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula encontrada.");
            return false;
        } else {
            for (Matricula matricula : matriculas) {
                Curso curso = cursoService.buscarCursoPorId(matricula.getCursoId());
                System.out.println("ID Matrícula: " + matricula.getId() + 
                                 " | Curso: " + curso.getNome() + 
                                 " | Status: " + matricula.getStatus() + 
                                 " | Nota: " + matricula.getNota());
            }
            return true;
        }
    }

    private boolean historicoAcademico(int alunoId) {
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorAluno(alunoId);
        System.out.println("\n--- Histórico Acadêmico ---");
        if (matriculas.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return false;
        } else {
            for (Matricula matricula : matriculas) {
                Curso curso = cursoService.buscarCursoPorId(matricula.getCursoId());
                System.out.println("Curso: " + curso.getNome());
                System.out.println("Status: " + matricula.getStatus());
                System.out.println("Nota: " + matricula.getNota());
                System.out.println("Data: " + matricula.getDataMatricula());
                System.out.println("------------------------");
            }
            return true;
        }
    }

    private boolean emitirCertificados(int alunoId) {
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorAluno(alunoId);
        boolean temCertificado = false;
        int certificadosEmitidos = 0;
        
        System.out.println("\n--- Emissão de Certificados ---");
        
        for (Matricula matricula : matriculas) {
            if ("CONCLUIDA".equals(matricula.getStatus()) && matricula.getNota() >= 6.0) {
                boolean emitido = matriculaService.emitirCertificado(matricula.getId());
                if (emitido) {
                    certificadosEmitidos++;
                    temCertificado = true;
                }
            }
        }
        
        if (certificadosEmitidos > 0) {
            System.out.println("Total de certificados emitidos: " + certificadosEmitidos);
            return true;
        }
        
        if (!temCertificado) {
            System.out.println("Nenhum certificado disponível para emissão.");
            System.out.println("Verifique se há cursos concluídos com nota ≥ 6.0.");
        }
        return temCertificado;
    }

    private boolean suporteVip(boolean isVip) {
        if (!isVip) {
            System.out.println("Acesso negado. Apenas alunos VIP.");
            return false;
        }
        
        System.out.println("\n--- Suporte VIP ---");
        System.out.println("Entre em contato pelo telefone 0800-VIP-EDUCA.");
        System.out.println("Ou agende uma mentoria exclusiva!");
        System.out.println("Email: suportevip@educaonline.com");
        return true;
    }
}