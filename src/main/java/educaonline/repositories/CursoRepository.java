package main.java.educaonline.repositories;

import main.java.educaonline.intities.*;
import java.util.List;

public class CursoRepository extends FileRepository<Curso> {
    @Override
    protected String getFileName() {
        return "cursos.txt";
    }
    
    @Override
    protected Curso convertFromString(String linha) {
        return Curso.fromString(linha);
    }
    
    public Curso buscarPorId(int id) {
        List<Curso> cursos = carregarTodos();
        return cursos.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public void salvar(Curso curso) {
        List<Curso> cursos = carregarTodos();
        cursos.add(curso);
        salvarTodos(cursos);
    }
}