package main.java.educaonline.services;

import main.java.educaonline.intities.*;
import main.java.educaonline.repositories.*;
import java.util.List;
import java.util.stream.Collectors;

public class CursoService {
    private CursoRepository cursoRepository = new CursoRepository();
    
    public void cadastrarCurso(String nome, String categoria, String area, int limiteAlunos, boolean exclusivoVip) {
        Curso curso = new Curso();
        curso.setId(cursoRepository.getProximoId());
        curso.setNome(nome);
        curso.setCategoria(categoria);
        curso.setArea(area);
        curso.setLimiteAlunos(limiteAlunos);
        curso.setExclusivoVip(exclusivoVip);
        cursoRepository.salvar(curso);
        System.out.println("Curso cadastrado com sucesso! ID: " + curso.getId());
    }
    
    public List<Curso> listarCursosDisponiveis(boolean isAlunoVip) {
        List<Curso> cursos = cursoRepository.carregarTodos();
        if (isAlunoVip) {
            return cursos;
        }
        return cursos.stream()
                .filter(c -> !c.isExclusivoVip())
                .collect(Collectors.toList());
    }
    
    public Curso buscarCursoPorId(int id) {
        return cursoRepository.buscarPorId(id);
    }
    
    public List<Curso> listarTodosCursos() {
        return cursoRepository.carregarTodos();
    }
}