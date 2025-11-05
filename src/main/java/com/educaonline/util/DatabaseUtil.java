package main.java.com.educaonline.util;

import main.java.com.educaonline.model.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Aluno> alunos = new ArrayList<>();
    private static List<AlunoVIP> alunosVIP = new ArrayList<>();
    private static List<Professor> professores = new ArrayList<>();
    private static List<Curso> cursos = new ArrayList<>();
    
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
        limparDados();
        criarAdmTeste();
        criarProfessores();
        criarCursos();
        criarAlunos();
    }
    
    private static void limparDados() {
        usuarios.clear();
        alunos.clear();
        alunosVIP.clear();
        cursos.clear();
        professores.clear();
    }
    
    private static void criarAdmTeste() {
        Aluno admAluno = new Aluno("ADM001", "Adm Aluno", "adm@aluno.com", "123", "(11) 99999-9999");
        AlunoVIP admVIP = new AlunoVIP("ADMVIP001", "Adm VIP", "adm@vip.com", "123", "(11) 98888-8888");
        Professor admProfessor = new Professor("ADMP001", "Adm Professor", "adm@professor.com", "123", "Administração");
        
        alunos.add(admAluno);
        alunosVIP.add(admVIP);
        professores.add(admProfessor);
        
        usuarios.add(admAluno);
        usuarios.add(admVIP);
        usuarios.add(admProfessor);
    }
    
    private static void criarProfessores() {
        Professor prof1 = new Professor("P001", "Carlos Silva", "carlos@educaonline.com", "123", "Programação");
        Professor prof2 = new Professor("P002", "Ana Santos", "ana@educaonline.com", "123", "Matemática");
        Professor prof3 = new Professor("P003", "Roberto Lima", "roberto@educaonline.com", "123", "Design");
        
        professores.add(prof1);
        professores.add(prof2);
        professores.add(prof3);
        
        usuarios.add(prof1);
        usuarios.add(prof2);
        usuarios.add(prof3);
    }
    
    private static void criarCursos() {
        Curso curso1 = new Curso("C001", "Java para Iniciantes", "Programação", 
                                "Aprenda Java desde o básico", 
                                299.90, 40, false, 50);
        
        Curso curso2 = new Curso("C002", "Java Avançado e POO", "Programação", 
                                "Programação Orientada a Objetos", 
                                499.90, 60, false, 30);
        
        Curso curso3 = new Curso("C003", "Mentoria VIP - Arquitetura", "Programação", 
                                "Mentoria exclusiva sobre arquitetura", 
                                799.90, 20, true, 15);
        
        cursos.add(curso1);
        cursos.add(curso2);
        cursos.add(curso3);
    }
    
    private static void criarAlunos() {
        Aluno aluno1 = new Aluno("A001", "João Silva", "joao@email.com", "123", "(11) 99999-1111");
        Aluno aluno2 = new Aluno("A002", "Maria Oliveira", "maria@email.com", "123", "(11) 99999-2222");
        
        AlunoVIP alunoVIP1 = new AlunoVIP("AV001", "Ana Santos", "ana.vip@email.com", "123", "(11) 98888-1111");
        AlunoVIP alunoVIP2 = new AlunoVIP("AV002", "Lucas Lima", "lucas.vip@email.com", "123", "(11) 98888-2222");
        
        alunoVIP1.setMentorAssociado("Carlos Silva");
        alunoVIP2.setMentorAssociado("Ana Santos");
        
        alunos.add(aluno1);
        alunos.add(aluno2);
        alunosVIP.add(alunoVIP1);
        alunosVIP.add(alunoVIP2);
        
        usuarios.add(aluno1);
        usuarios.add(aluno2);
        usuarios.add(alunoVIP1);
        usuarios.add(alunoVIP2);
    }
    
    public static Usuario validarLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.validarSenha(senha)) {
                return usuario;
            }
        }
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
            if (curso.isExclusivoVIP() && !(aluno instanceof AlunoVIP)) {
                return false;
            }
            
            if (curso.matricularAluno()) {
                aluno.matricularCurso(cursoId);
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