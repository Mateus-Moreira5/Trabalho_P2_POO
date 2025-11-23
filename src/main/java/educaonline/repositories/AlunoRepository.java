package main.java.educaonline.repositories;

import main.java.educaonline.intities.*;
import java.util.List;

public class AlunoRepository extends FileRepository<Aluno> {
    @Override
    protected String getFileName() {
        return "alunos.txt";
    }
    
    @Override
    protected Aluno convertFromString(String linha) {
        return Aluno.fromString(linha);
    }
    
    public Aluno buscarPorId(int id) {
        List<Aluno> alunos = carregarTodos();
        return alunos.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public void salvar(Aluno aluno) {
        List<Aluno> alunos = carregarTodos();
        alunos.add(aluno);
        salvarTodos(alunos);
    }
}