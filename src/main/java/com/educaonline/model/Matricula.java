package main.java.com.educaonline.model;

import java.util.Date;

public class Matricula {
    private String id;
    private String alunoId;
    private String cursoId;
    private String turmaId;
    private Date dataMatricula;
    private String status;
    private double valorPago;
    
    public Matricula(String id, String alunoId, String cursoId, String turmaId, double valorPago) {
        this.id = id;
        this.alunoId = alunoId;
        this.cursoId = cursoId;
        this.turmaId = turmaId;
        this.dataMatricula = new Date();
        this.status = "ATIVA";
        this.valorPago = valorPago;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getAlunoId() { return alunoId; }
    public void setAlunoId(String alunoId) { this.alunoId = alunoId; }
    
    public String getCursoId() { return cursoId; }
    public void setCursoId(String cursoId) { this.cursoId = cursoId; }
    
    public String getTurmaId() { return turmaId; }
    public void setTurmaId(String turmaId) { this.turmaId = turmaId; }
    
    public Date getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(Date dataMatricula) { this.dataMatricula = dataMatricula; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
    
    public boolean isAtiva() {
        return "ATIVA".equals(status);
    }
    
    public void cancelarMatricula() {
        this.status = "CANCELADA";
    }
    
    public void concluirMatricula() {
        this.status = "CONCLUIDA";
    }
    
    @Override
    public String toString() {
        return "Matricula{id='" + id + "', alunoId='" + alunoId + "', cursoId='" + cursoId + "', status='" + status + "'}";
    }
}