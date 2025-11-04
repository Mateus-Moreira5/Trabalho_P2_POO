package main.java.com.educaonline.util;

import main.java.com.educaonline.model.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Aluno> alunos = new ArrayList<>();
    private static List<AlunoVIP> alunosVIP = new ArrayList<>();
    private static List<Curso> cursos = new ArrayList<>();
    private static List<Professor> professores = new ArrayList<>();
    
    public static List<Usuario> getUsuarios() { 
        return new ArrayList<>(usuarios); 
    }
    
    public static List<Aluno> getAlunos() { 
        return new ArrayList<>(alunos); 
    }
    
    public static List<AlunoVIP> getAlunosVIP() { 
        return new ArrayList<>(alunosVIP); 
    }
    
    public static List<Curso> getCursos() { 
        return new ArrayList<>(cursos); 
    }
    
    public static List<Professor> getProfessores() { 
        return new ArrayList<>(professores); 
    }
    
    public static void inicializarDadosExemplo() {
        System.out.println("🎓 Inicializando dados do EducaOnline...");
        
        limparDados();
        criarProfessores();
        criarCursos();
        criarAlunos();
        fazerMatriculas();
        
        System.out.println("✅ Sistema carregado com:");
        System.out.println("   👨‍🏫 " + professores.size() + " professores");
        System.out.println("   📚 " + cursos.size() + " cursos");
        System.out.println("   👨‍🎓 " + alunos.size() + " alunos regulares");
        System.out.println("   ⭐ " + alunosVIP.size() + " alunos VIP");
        System.out.println("   👥 " + usuarios.size() + " usuários totais");
    }
    
    private static void limparDados() {
        usuarios.clear();
        alunos.clear();
        alunosVIP.clear();
        cursos.clear();
        professores.clear();
    }
    
    private static void criarProfessores() {
        Professor prof1 = new Professor("P001", "Carlos Silva", "carlos@educaonline.com", "prof123", "Matemática");
        Professor prof2 = new Professor("P002", "Ana Santos", "ana@educaonline.com", "prof123", "Programação");
        Professor prof3 = new Professor("P003", "Roberto Lima", "roberto@educaonline.com", "prof123", "Design");
        
        professores.add(prof1);
        professores.add(prof2);
        professores.add(prof3);
        usuarios.add(prof1);
        usuarios.add(prof2);
        usuarios.add(prof3);
    }
    
    private static void criarCursos() {
        Curso curso1 = new Curso("C001", "Java para Iniciantes", "Programação", 
                                "Aprenda Java desde o básico até conceitos intermediários", 
                                299.90, 40, false, 50);
        
        Curso curso2 = new Curso("C002", "Java Avançado e POO", "Programação", 
                                "Programação Orientada a Objetos e padrões de projeto", 
                                499.90, 60, false, 30);
        
        Curso curso3 = new Curso("C003", "Mentoria VIP - Arquitetura Java", "Programação", 
                                "Mentoria exclusiva sobre arquitetura de software", 
                                799.90, 20, true, 15);
        
        Curso curso4 = new Curso("C004", "Matemática para Programadores", "Matemática", 
                                "Lógica matemática aplicada à programação", 
                                349.90, 35, false, 40);
        
        Curso curso5 = new Curso("C005", "UX/UI Design Avançado", "Design", 
                                "Design de interfaces e experiência do usuário", 
                                449.90, 45, true, 25);
        
        cursos.add(curso1);
        cursos.add(curso2);
        cursos.add(curso3);
        cursos.add(curso4);
        cursos.add(curso5);
    }
    
    private static void criarAlunos() {
        // Alunos regulares
        Aluno aluno1 = new Aluno("A001", "João Silva", "joao@email.com", "123456", "(11) 99999-1111");
        Aluno aluno2 = new Aluno("A002", "Maria Oliveira", "maria@email.com", "123456", "(11) 99999-2222");
        Aluno aluno3 = new Aluno("A003", "Pedro Costa", "pedro@email.com", "123456", "(11) 99999-3333");
        
        // Alunos VIP
        AlunoVIP alunoVIP1 = new AlunoVIP("AV001", "Ana Santos", "ana.vip@email.com", "654321", "(11) 98888-1111");
        AlunoVIP alunoVIP2 = new AlunoVIP("AV002", "Lucas Lima", "lucas.vip@email.com", "123456", "(11) 98888-2222");
        
        alunoVIP1.setMentorAssociado("Carlos Silva");
        alunoVIP2.setMentorAssociado("Ana Santos");
        
        // Adicionar às listas
        alunos.add(aluno1);
        alunos.add(aluno2);
        alunos.add(aluno3);
        alunosVIP.add(alunoVIP1);
        alunosVIP.add(alunoVIP2);
        
        usuarios.add(aluno1);
        usuarios.add(aluno2);
        usuarios.add(aluno3);
        usuarios.add(alunoVIP1);
        usuarios.add(alunoVIP2);
    }
    
    private static void fazerMatriculas() {
        // João se matricula em Java Básico e Matemática
        alunos.get(0).matricularCurso("C001");
        alunos.get(0).matricularCurso("C004");
        cursos.get(0).matricularAluno(); // Java Básico
        cursos.get(3).matricularAluno(); // Matemática
        
        // Maria se matricula em Java Básico
        alunos.get(1).matricularCurso("C001");
        cursos.get(0).matricularAluno();
        
        // Ana VIP se matricula em todos os cursos, incluindo os exclusivos
        alunosVIP.get(0).matricularCurso("C001");
        alunosVIP.get(0).matricularCurso("C002");
        alunosVIP.get(0).matricularCurso("C003");
        alunosVIP.get(0).matricularCurso("C004");
        alunosVIP.get(0).matricularCurso("C005");
        
        cursos.get(0).matricularAluno();
        cursos.get(1).matricularAluno();
        cursos.get(2).matricularAluno();
        cursos.get(3).matricularAluno();
        cursos.get(4).matricularAluno();
        
        // Lucas VIP se matricula nos cursos VIP
        alunosVIP.get(1).matricularCurso("C003");
        alunosVIP.get(1).matricularCurso("C005");
        cursos.get(2).matricularAluno();
        cursos.get(4).matricularAluno();
    }
    
    public static Usuario validarLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.validarSenha(senha)) {
                System.out.println("✅ Login válido: " + usuario.getNome());
                return usuario;
            }
        }
        System.out.println("❌ Login inválido para: " + email);
        return null;
    }
    
    public static List<Curso> getCursosDisponiveis(boolean isVIP) {
        List<Curso> disponiveis = new ArrayList<>();
        for (Curso curso : cursos) {
            if (!curso.isExclusivoVIP() || (curso.isExclusivoVIP() && isVIP)) {
                if (curso.temVagas()) {
                    disponiveis.add(curso);
                }
            }
        }
        return disponiveis;
    }
    
    public static Aluno getAlunoPorEmail(String email) {
        for (Aluno aluno : alunos) {
            if (aluno.getEmail().equals(email)) {
                return aluno;
            }
        }
        for (AlunoVIP alunoVIP : alunosVIP) {
            if (alunoVIP.getEmail().equals(email)) {
                return alunoVIP;
            }
        }
        return null;
    }
    
    public static boolean matricularAlunoCurso(String alunoEmail, String cursoId) {
        Aluno aluno = getAlunoPorEmail(alunoEmail);
        Curso curso = getCursoPorId(cursoId);
        
        if (aluno != null && curso != null) {
            if (curso.isExclusivoVIP() && !aluno.isVip()) {
                System.out.println("❌ Curso exclusivo para VIPs");
                return false;
            }
            
            if (curso.matricularAluno()) {
                aluno.matricularCurso(cursoId);
                System.out.println("✅ " + aluno.getNome() + " matriculado em " + curso.getTitulo());
                return true;
            }
        }
        return false;
    }
    
    public static Curso getCursoPorId(String cursoId) {
        for (Curso curso : cursos) {
            if (curso.getId().equals(cursoId)) {
                return curso;
            }
        }
        return null;
    }
}