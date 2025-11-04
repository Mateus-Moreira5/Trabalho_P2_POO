package main.java.com.educaonline.model;

public class Curso {
    private String id;
    private String titulo;
    private String categoria;
    private String descricao;
    private double preco;
    private int duracaoHoras;
    private boolean exclusivoVIP;
    private int vagasDisponiveis;
    private int vagasTotal;
    
    public Curso(String id, String titulo, String categoria, String descricao, double preco, int duracaoHoras, boolean exclusivoVIP, int vagasTotal) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoHoras = duracaoHoras;
        this.exclusivoVIP = exclusivoVIP;
        this.vagasTotal = vagasTotal;
        this.vagasDisponiveis = vagasTotal;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public int getDuracaoHoras() { return duracaoHoras; }
    public void setDuracaoHoras(int duracaoHoras) { this.duracaoHoras = duracaoHoras; }
    
    public boolean isExclusivoVIP() { return exclusivoVIP; }
    public void setExclusivoVIP(boolean exclusivoVIP) { this.exclusivoVIP = exclusivoVIP; }
    
    public int getVagasDisponiveis() { return vagasDisponiveis; }
    public int getVagasTotal() { return vagasTotal; }
    
    public boolean temVagas() {
        return vagasDisponiveis > 0;
    }
    
    public boolean matricularAluno() {
        if (temVagas()) {
            vagasDisponiveis--;
            return true;
        }
        return false;
    }
    
    public void cancelarMatricula() {
        if (vagasDisponiveis < vagasTotal) {
            vagasDisponiveis++;
        }
    }
    
    public double calcularDescontoVIP() {
        return exclusivoVIP ? preco * 0.8 : preco * 0.9;
    }
    
    @Override
    public String toString() {
        return "Curso{id='" + id + "', titulo='" + titulo + "', categoria='" + categoria + "', preco=" + preco + "}";
    }
}