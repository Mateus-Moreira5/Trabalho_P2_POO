package main.java.educaonline.intities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pagamento {
    private int id;
    private int alunoId;
    private double valor;
    private LocalDate data;
    private String tipo; // "CURSO" ou "ASSINATURA"
    private String status;
    
    public Pagamento() {}
    
    public Pagamento(int id, int alunoId, double valor, LocalDate data, String tipo, String status) {
        this.id = id;
        this.alunoId = alunoId;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.status = status;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return id + "|" + alunoId + "|" + valor + "|" + 
               data.format(DateTimeFormatter.ISO_LOCAL_DATE) + "|" + 
               tipo + "|" + status;
    }
    
    public static Pagamento fromString(String linha) {
        String[] dados = linha.split("\\|");
        Pagamento pagamento = new Pagamento();
        pagamento.setId(Integer.parseInt(dados[0]));
        pagamento.setAlunoId(Integer.parseInt(dados[1]));
        pagamento.setValor(Double.parseDouble(dados[2]));
        pagamento.setData(LocalDate.parse(dados[3]));
        pagamento.setTipo(dados[4]);
        pagamento.setStatus(dados[5]);
        return pagamento;
    }
}