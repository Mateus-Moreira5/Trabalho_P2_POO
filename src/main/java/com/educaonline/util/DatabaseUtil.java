package main.java.com.educaonline.util;

import main.java.com.educaonline.model.*;

import java.util.ArrayList;
import java.util.List;
public class DatabaseUtil {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Aluno> alunos = new ArrayList<>();
    private static List<Curso> cursos  = new ArrayList<>();
    private static List<Turma> turmas = new ArrayList<>();
    private static List<Matricula> matriculas = new ArrayList<>();

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
    public static List<Aluno> getAlunos() {
        return alunos;
    }
    public static List<Curso> getCursos() {
        return cursos;
    }
    public static List<Turma> getTurmas() {
        return turmas;
    }
    public static List<Matricula> getMatriculas() {
        return matriculas;
    }
    public static void iniciarDadosExemplo(){
        usuarios.clear();
        alunos.clear();
        cursos.clear();
        turmas.clear();
        matriculas.clear();
        System.out.println("Inicializando dados de exemplo...");
    }
    
}
