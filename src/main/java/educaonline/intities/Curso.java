package main.java.educaonline.intities;

public class Curso {
    private int id;
    private String nome;
    private String categoria;
    private String area;
    private int limiteAlunos;
    private boolean exclusivoVip;
    
    public Curso() {}
    
    public Curso(int id, String nome, String categoria, String area, int limiteAlunos, boolean exclusivoVip) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.area = area;
        this.limiteAlunos = limiteAlunos;
        this.exclusivoVip = exclusivoVip;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    
    public int getLimiteAlunos() { return limiteAlunos; }
    public void setLimiteAlunos(int limiteAlunos) { this.limiteAlunos = limiteAlunos; }
    
    public boolean isExclusivoVip() { return exclusivoVip; }
    public void setExclusivoVip(boolean exclusivoVip) { this.exclusivoVip = exclusivoVip; }
    
    @Override
    public String toString() {
        return id + "|" + nome + "|" + categoria + "|" + area + "|" + 
               limiteAlunos + "|" + exclusivoVip;
    }
    
    public static Curso fromString(String linha) {
        String[] dados = linha.split("\\|");
        Curso curso = new Curso();
        curso.setId(Integer.parseInt(dados[0]));
        curso.setNome(dados[1]);
        curso.setCategoria(dados[2]);
        curso.setArea(dados[3]);
        curso.setLimiteAlunos(Integer.parseInt(dados[4]));
        curso.setExclusivoVip(Boolean.parseBoolean(dados[5]));
        return curso;
    }
}