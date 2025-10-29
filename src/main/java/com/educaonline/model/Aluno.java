package main.java.com.educaonline.model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private boolean vip;
    private String telefone;
    private List<String> cursosMatriculados;
    
    public Aluno(String id, String nome, String email, String senha, String telefone) {
        super(id, nome, email, senha, "ALUNO");
        this.vip = false;
        this.telefone = telefone;
        this.cursosMatriculados = new ArrayList<>();
    }
    
    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public List<String> getCursosMatriculados() { return cursosMatriculados; }
    
    public void matricularCurso(String cursoId) {
        if (!cursosMatriculados.contains(cursoId)) {
            cursosMatriculados.add(cursoId);
        }
    }
    
    public void cancelarMatricula(String cursoId) {
        cursosMatriculados.remove(cursoId);
    }
    
    public boolean estaMatriculado(String cursoId) {
        return cursosMatriculados.contains(cursoId);
    }
    
    @Override
    public String toString() {
        return "Aluno{id='" + getId() + "', nome='" + getNome() + "', email='" + getEmail() + "', vip=" + vip + "}";
    }
}