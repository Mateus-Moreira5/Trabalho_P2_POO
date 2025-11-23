package main.java.educaonline.services;

import main.java.educaonline.repositories.*;
import main.java.educaonline.intities.*;
import java.time.LocalDate;
import java.util.List;

public class AlunoService {
    private AlunoRepository alunoRepository = new AlunoRepository();
    
    public void cadastrarAluno(String nome, String email, boolean vip) {
        Aluno aluno = new Aluno();
        aluno.setId(alunoRepository.getProximoId());
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setVip(vip);
        aluno.setDataCadastro(LocalDate.now());
        alunoRepository.salvar(aluno);
        System.out.println("Aluno cadastrado com sucesso! ID: " + aluno.getId());
    }
    
    public Aluno buscarAlunoPorId(int id) {
        return alunoRepository.buscarPorId(id);
    }
    
    public List<Aluno> listarTodosAlunos() {
        return alunoRepository.carregarTodos();
    }
    
    public boolean verificarAcessoVIP(int alunoId) {
        Aluno aluno = alunoRepository.buscarPorId(alunoId);
        return aluno != null && aluno.isVip();
    }
}