package main.java.com.educaonline.util;

import main.java.com.educaonline.model.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    // Listas básicas para os 4 requisitos
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Curso> cursos = new ArrayList<>();
    private static List<Matricula> matriculas = new ArrayList<>();
    private static List<Certificado> certificados = new ArrayList<>();
    private static List<Pagamento> pagamentos = new ArrayList<>();
    
    public static void inicializarDadosExemplo() {
        limparDados();
        criarUsuariosTeste();
        criarCursosTeste();
        System.out.println("✅ Sistema inicializado com dados de teste");
    }
    
    private static void limparDados() {
        usuarios.clear();
        cursos.clear();
        matriculas.clear();
        certificados.clear();
        pagamentos.clear();
    }
    
    private static void criarUsuariosTeste() {
        // Alunos regulares
        usuarios.add(new Aluno("A001", "João Silva", "joao@email.com", "123", "(11) 99999-1111"));
        usuarios.add(new Aluno("A002", "Maria Oliveira", "maria@email.com", "123", "(11) 99999-2222"));
        
        // Alunos VIP
        usuarios.add(new AlunoVIP("AV001", "Ana Santos VIP", "ana@vip.com", "123", "(11) 98888-1111"));
        usuarios.add(new AlunoVIP("AV002", "Carlos Lima VIP", "carlos@vip.com", "123", "(11) 98888-2222"));
    }
    
    private static void criarCursosTeste() {
        // Cursos regulares
        cursos.add(new Curso("C001", "Java Básico", "Programação", 
                            "Introdução à programação Java", 299.90, 40, false, 50));
        cursos.add(new Curso("C002", "Banco de Dados", "TI", 
                            "Fundamentos de SQL e modelagem", 349.90, 35, false, 40));
        
        // Cursos exclusivos VIP
        cursos.add(new Curso("C003", "Java Avançado VIP", "Programação", 
                            "Design Patterns e Arquitetura", 599.90, 60, true, 20));
        cursos.add(new Curso("C004", "Mentoria Carreira VIP", "Desenvolvimento", 
                            "Mentoria exclusiva para desenvolvimento", 799.90, 30, true, 15));
    }
    
    // === MÉTODOS BÁSICOS DE ACESSO ===
    public static List<Usuario> getUsuarios() { return new ArrayList<>(usuarios); }
    public static List<Curso> getCursos() { return new ArrayList<>(cursos); }
    public static List<Matricula> getMatriculas() { return new ArrayList<>(matriculas); }
    public static List<Certificado> getCertificados() { return new ArrayList<>(certificados); }
    public static List<Pagamento> getPagamentos() { return new ArrayList<>(pagamentos); }
    
    // === MÉTODOS DE BUSCA ===
    public static Usuario validarLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }
    
    public static Aluno getAlunoPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Aluno && usuario.getEmail().equals(email)) {
                return (Aluno) usuario;
            }
        }
        return null;
    }
    
    public static Curso getCursoPorId(String cursoId) {
        for (Curso curso : cursos) {
            if (curso.getId().equals(cursoId)) {
                return curso;
            }
        }
        return null;
    }
    
    public static List<Curso> getCursosDisponiveis(boolean isVIP) {
        List<Curso> disponiveis = new ArrayList<>();
        for (Curso curso : cursos) {
            if (!curso.isExclusivoVIP() || (curso.isExclusivoVIP() && isVIP)) {
                disponiveis.add(curso);
            }
        }
        return disponiveis;
    }
    
    // === MÉTODOS DE AÇÃO (serão usados pelos services) ===
    public static boolean adicionarMatricula(Matricula matricula) {
        return matriculas.add(matricula);
    }
    
    public static boolean adicionarCertificado(Certificado certificado) {
        return certificados.add(certificado);
    }
    
    public static boolean adicionarPagamento(Pagamento pagamento) {
        return pagamentos.add(pagamento);
    }
    
    public static boolean adicionarCurso(Curso curso) {
        return cursos.add(curso);
    }
    
    public static boolean adicionarUsuario(Usuario usuario) {
        return usuarios.add(usuario);
    }
}