package main.java.com.educaonline.model;

public class Professor extends Usuario {
    private String especialidade;
    private double salario;
    private int anosExperiencia;
    
    public Professor(String id, String nome, String email, String senha, String especialidade) {
        super(id, nome, email, senha, "PROFESSOR");
        this.especialidade = especialidade;
        this.salario = 0.0;
        this.anosExperiencia = 0;
    }
    
    public Professor(String id, String nome, String email, String senha, String especialidade, double salario, int anosExperiencia) {
        super(id, nome, email, senha, "PROFESSOR");
        this.especialidade = especialidade;
        this.salario = salario;
        this.anosExperiencia = anosExperiencia;
    }
    
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    
    public int getAnosExperiencia() { return anosExperiencia; }
    public void setAnosExperiencia(int anosExperiencia) { this.anosExperiencia = anosExperiencia; }
    
    public String getNivelExperiencia() {
        if (anosExperiencia < 2) return "Júnior";
        if (anosExperiencia < 5) return "Pleno";
        return "Sênior";
    }
    
    public double calcularBonus() {
        return salario * 0.1; // 10% de bônus
    }
    
    public boolean podeMinistrarCurso(String categoriaCurso) {
        return especialidade.equalsIgnoreCase(categoriaCurso) || 
               "Geral".equalsIgnoreCase(especialidade);
    }
    
    @Override
    public String toString() {
        return "Professor{id='" + getId() + "', nome='" + getNome() + "', especialidade='" + especialidade + "', experiencia=" + anosExperiencia + " anos}";
    }
}