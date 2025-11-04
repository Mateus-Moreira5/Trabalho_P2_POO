package main.java.com.educaonline.model;

import java.util.Date;

public class Pagamento {
    private String id;
    private String alunoId;
    private String matriculaId;
    private double valor;
    private Date dataPagamento;
    private String metodoPagamento;
    private String status;
    private String codigoTransacao;
    
    public Pagamento(String id, String alunoId, String matriculaId, double valor, String metodoPagamento) {
        this.id = id;
        this.alunoId = alunoId;
        this.matriculaId = matriculaId;
        this.valor = valor;
        this.dataPagamento = new Date();
        this.metodoPagamento = metodoPagamento;
        this.status = "PENDENTE";
        this.codigoTransacao = gerarCodigoTransacao();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getAlunoId() { return alunoId; }
    public void setAlunoId(String alunoId) { this.alunoId = alunoId; }
    
    public String getMatriculaId() { return matriculaId; }
    public void setMatriculaId(String matriculaId) { this.matriculaId = matriculaId; }
    
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    
    public Date getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(Date dataPagamento) { this.dataPagamento = dataPagamento; }
    
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getCodigoTransacao() { return codigoTransacao; }
    public void setCodigoTransacao(String codigoTransacao) { this.codigoTransacao = codigoTransacao; }
    
    private String gerarCodigoTransacao() {
        return "TXN-" + System.currentTimeMillis() + "-" + alunoId.substring(0, 3).toUpperCase();
    }
    
    public void processarPagamento() {
        this.status = "APROVADO";
        this.dataPagamento = new Date();
    }
    
    public void cancelarPagamento() {
        this.status = "CANCELADO";
    }
    
    public void reembolsarPagamento() {
        this.status = "REEMBOLSADO";
    }
    
    public boolean isPago() {
        return "APROVADO".equals(status);
    }
    
    public boolean isPendente() {
        return "PENDENTE".equals(status);
    }
    
    public String getValorFormatado() {
        return String.format("R$ %.2f", valor);
    }
    
    public double calcularDescontoVIP(boolean isVIP) {
        return isVIP ? valor * 0.8 : valor; // 20% de desconto para VIPs
    }
    
    @Override
    public String toString() {
        return "Pagamento{id='" + id + "', alunoId='" + alunoId + "', valor=" + valor + ", status='" + status + "'}";
    }
}