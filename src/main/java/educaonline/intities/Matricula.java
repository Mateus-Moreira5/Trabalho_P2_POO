package main.java.educaonline.intities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Matricula {
    private int id;
    private int alunoId;
    private int cursoId;
    private LocalDate dataMatricula;
    private String status; // "ATIVA", "CONCLUIDA", "CANCELADA"
    private double nota;
    
    public Matricula() {}
    
    public Matricula(int id, int alunoId, int cursoId, LocalDate dataMatricula, String status, double nota) {
        this.id = id;
        this.alunoId = alunoId;
        this.cursoId = cursoId;
        this.dataMatricula = dataMatricula;
        this.status = status;
        this.nota = nota;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    
    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
    
    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    
    @Override
    public String toString() {
        return id + "|" + alunoId + "|" + cursoId + "|" + 
               dataMatricula.format(DateTimeFormatter.ISO_LOCAL_DATE) + "|" + 
               status + "|" + nota;
    }
    
    public static Matricula fromString(String linha) {
        String[] dados = linha.split("\\|");
        Matricula matricula = new Matricula();
        matricula.setId(Integer.parseInt(dados[0]));
        matricula.setAlunoId(Integer.parseInt(dados[1]));
        matricula.setCursoId(Integer.parseInt(dados[2]));
        matricula.setDataMatricula(LocalDate.parse(dados[3]));
        matricula.setStatus(dados[4]);
        matricula.setNota(Double.parseDouble(dados[5]));
        return matricula;
    }
}