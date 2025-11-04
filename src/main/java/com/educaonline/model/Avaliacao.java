package main.java.com.educaonline.model;

import java.util.Date;

public class Avaliacao {
    private String id;
    private String alunoId;
    private String cursoId;
    private double nota;
    private Date dataAvaliacao;
    private String tipoAvaliacao;
    private String comentario;
    
    public Avaliacao(String id, String alunoId, String cursoId, double nota, String tipoAvaliacao, String comentario) {
        this.id = id;
        this.alunoId = alunoId;
        this.cursoId = cursoId;
        this.nota = nota;
        this.dataAvaliacao = new Date();
        this.tipoAvaliacao = tipoAvaliacao;
        this.comentario = comentario;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getAlunoId() { return alunoId; }
    public void setAlunoId(String alunoId) { this.alunoId = alunoId; }
    
    public String getCursoId() { return cursoId; }
    public void setCursoId(String cursoId) { this.cursoId = cursoId; }
    
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    
    public Date getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(Date dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }
    
    public String getTipoAvaliacao() { return tipoAvaliacao; }
    public void setTipoAvaliacao(String tipoAvaliacao) { this.tipoAvaliacao = tipoAvaliacao; }
    
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    
    public boolean isAprovado() {
        return nota >= 7.0;
    }
    
    public String getStatus() {
        return isAprovado() ? "APROVADO" : "REPROVADO";
    }
    
    public String getNotaFormatada() {
        return String.format("%.1f", nota);
    }
    
    @Override
    public String toString() {
        return "Avaliacao{id='" + id + "', alunoId='" + alunoId + "', cursoId='" + cursoId + "', nota=" + nota + "}";
    }
}