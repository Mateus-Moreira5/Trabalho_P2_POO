package main.java.com.educaonline.model;

import java.util.Date;

public class AlunoVIP extends Aluno {
    private String mentorAssociado;
    private Date dataExpiracaoVIP;
    private int mentoriasDisponiveis;
    
    public AlunoVIP(String id, String nome, String email, String senha, String telefone) {
        super(id, nome, email, senha, telefone);
        setVip(true);
        this.mentoriasDisponiveis = 4;
        this.dataExpiracaoVIP = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));
    }
    
    public String getMentorAssociado() { return mentorAssociado; }
    public void setMentorAssociado(String mentorAssociado) { this.mentorAssociado = mentorAssociado; }
    
    public Date getDataExpiracaoVIP() { return dataExpiracaoVIP; }
    public void setDataExpiracaoVIP(Date dataExpiracaoVIP) { this.dataExpiracaoVIP = dataExpiracaoVIP; }
    
    public int getMentoriasDisponiveis() { return mentoriasDisponiveis; }
    public void setMentoriasDisponiveis(int mentoriasDisponiveis) { this.mentoriasDisponiveis = mentoriasDisponiveis; }
    
    public void agendarMentoria() {
        if (mentoriasDisponiveis > 0) {
            mentoriasDisponiveis--;
            System.out.println("Mentoria agendada para " + getNome());
        } else {
            System.out.println("Não há mentorias disponíveis");
        }
    }
    
    public boolean temMentoriasDisponiveis() {
        return mentoriasDisponiveis > 0;
    }
    
    public boolean isVIPAtivo() {
        return new Date().before(dataExpiracaoVIP);
    }
    
    @Override
    public String toString() {
        return "AlunoVIP{id='" + getId() + "', nome='" + getNome() + "', mentorias=" + mentoriasDisponiveis + "}";
    }
}