package main.java.com.educaonline.model;

import java.util.Date;

public class Certificado {
    private String id;
    private String alunoId;
    private String cursoId;
    private String alunoNome;
    private String cursoTitulo;
    private int cargaHoraria;
    private Date dataEmissao;
    private String codigoVerificacao;
    private double notaFinal;
    
    public Certificado(String id, String alunoId, String cursoId, String alunoNome, String cursoTitulo, int cargaHoraria, double notaFinal) {
        this.id = id;
        this.alunoId = alunoId;
        this.cursoId = cursoId;
        this.alunoNome = alunoNome;
        this.cursoTitulo = cursoTitulo;
        this.cargaHoraria = cargaHoraria;
        this.dataEmissao = new Date();
        this.notaFinal = notaFinal;
        this.codigoVerificacao = gerarCodigoVerificacao();
    }
    
    public Certificado(String certificadoId, String id2, String cursoId2, String nome, String titulo,
            Object cargaHoraria2, double notaFinal2) {
        //TODO Auto-generated constructor stub
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getAlunoId() { return alunoId; }
    public void setAlunoId(String alunoId) { this.alunoId = alunoId; }
    
    public String getCursoId() { return cursoId; }
    public void setCursoId(String cursoId) { this.cursoId = cursoId; }
    
    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }
    
    public String getCursoTitulo() { return cursoTitulo; }
    public void setCursoTitulo(String cursoTitulo) { this.cursoTitulo = cursoTitulo; }
    
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    
    public Date getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(Date dataEmissao) { this.dataEmissao = dataEmissao; }
    
    public String getCodigoVerificacao() { return codigoVerificacao; }
    public void setCodigoVerificacao(String codigoVerificacao) { this.codigoVerificacao = codigoVerificacao; }
    
    public double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(double notaFinal) { this.notaFinal = notaFinal; }
    
    private String gerarCodigoVerificacao() {
        return "CERT-" + System.currentTimeMillis() + "-" + alunoId.substring(0, 3).toUpperCase();
    }
    
    public boolean isValido() {
        return notaFinal >= 7.0;
    }
    
    public String getNotaFinalFormatada() {
        return String.format("%.1f", notaFinal);
    }
    
    public String getDataEmissaoFormatada() {
        return String.format("%td/%tm/%tY", dataEmissao, dataEmissao, dataEmissao);
    }
    
    @Override
    public String toString() {
        return "Certificado{id='" + id + "', aluno='" + alunoNome + "', curso='" + cursoTitulo + "', codigo='" + codigoVerificacao + "'}";
    }
}