package main.java.educaonline.intities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Aluno {
    private int id;
    private String nome;
    private String email;
    private boolean vip;
    private LocalDate dataCadastro;
    
    public Aluno() {}
    
    public Aluno(int id, String nome, String email, boolean vip, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.vip = vip;
        this.dataCadastro = dataCadastro;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
    
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    
    @Override
    public String toString() {
        return id + "|" + nome + "|" + email + "|" + vip + "|" + 
               dataCadastro.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    public static Aluno fromString(String linha) {
        String[] dados = linha.split("\\|");
        Aluno aluno = new Aluno();
        aluno.setId(Integer.parseInt(dados[0]));
        aluno.setNome(dados[1]);
        aluno.setEmail(dados[2]);
        aluno.setVip(Boolean.parseBoolean(dados[3]));
        aluno.setDataCadastro(LocalDate.parse(dados[4]));
        return aluno;
    }
}