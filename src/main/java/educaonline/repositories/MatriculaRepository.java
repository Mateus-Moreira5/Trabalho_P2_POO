package main.java.educaonline.repositories;

import main.java.educaonline.intities.*;
import java.util.List;
import java.util.stream.Collectors;

public class MatriculaRepository extends FileRepository<Matricula> {
    @Override
    protected String getFileName() {
        return "matriculas.txt";
    }
    
    @Override
    protected Matricula convertFromString(String linha) {
        return Matricula.fromString(linha);
    }
    
    public List<Matricula> buscarPorAlunoId(int alunoId) {
        List<Matricula> matriculas = carregarTodos();
        return matriculas.stream()
                .filter(m -> m.getAlunoId() == alunoId)
                .collect(Collectors.toList());
    }
    
    public List<Matricula> buscarPorCursoId(int cursoId) {
        List<Matricula> matriculas = carregarTodos();
        return matriculas.stream()
                .filter(m -> m.getCursoId() == cursoId)
                .collect(Collectors.toList());
    }
    
    public void salvar(Matricula matricula) {
        List<Matricula> matriculas = carregarTodos();
        matriculas.add(matricula);
        salvarTodos(matriculas);
    }
    
    public void atualizar(Matricula matriculaAtualizada) {
        List<Matricula> matriculas = carregarTodos();
        for (int i = 0; i < matriculas.size(); i++) {
            if (matriculas.get(i).getId() == matriculaAtualizada.getId()) {
                matriculas.set(i, matriculaAtualizada);
                break;
            }
        }
        salvarTodos(matriculas);
    }
}