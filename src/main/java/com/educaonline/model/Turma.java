package main.java.com.educaonline.model;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String id;
    private String cursoId;
    private String professorId;
    private String horario;
    private int vagasTotal;
    private int vagasOcupadas;
    private List<String> alunosMatriculados;
    
    public Turma(String id, String cursoId, String professorId, String horario, int vagasTotal) {
        this.id = id;
        this.cursoId = cursoId;
        this.professorId = professorId;
        this.horario = horario;
        this.vagasTotal = vagasTotal;
        this.vagasOcupadas = 0;
        this.alunosMatriculados = new ArrayList<>();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCursoId() { return cursoId; }
    public void setCursoId(String cursoId) { this.cursoId = cursoId; }
    
    public String getProfessorId() { return professorId; }
    public void setProfessorId(String professorId) { this.professorId = professorId; }
    
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    
    public int getVagasTotal() { return vagasTotal; }
    public void setVagasTotal(int vagasTotal) { this.vagasTotal = vagasTotal; }
    
    public int getVagasOcupadas() { return vagasOcupadas; }
    public int getVagasDisponiveis() { return vagasTotal - vagasOcupadas; }
    
    public List<String> getAlunosMatriculados() { return new ArrayList<>(alunosMatriculados); }
    
    public boolean temVagas() {
        return vagasOcupadas < vagasTotal;
    }
    
    public boolean matricularAluno(String alunoId) {
        if (temVagas() && !alunosMatriculados.contains(alunoId)) {
            alunosMatriculados.add(alunoId);
            vagasOcupadas++;
            return true;
        }
        return false;
    }
    
    public boolean cancelarMatricula(String alunoId) {
        if (alunosMatriculados.contains(alunoId)) {
            alunosMatriculados.remove(alunoId);
            vagasOcupadas--;
            return true;
        }
        return false;
    }
    
    public boolean alunoEstaMatriculado(String alunoId) {
        return alunosMatriculados.contains(alunoId);
    }
    
    @Override
    public String toString() {
        return "Turma{id='" + id + "', cursoId='" + cursoId + "', vagas=" + vagasOcupadas + "/" + vagasTotal + "}";
    }
}