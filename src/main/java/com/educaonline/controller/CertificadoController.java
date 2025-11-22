package main.java.com.educaonline.controller;

import main.java.com.educaonline.model.Certificado;
import main.java.com.educaonline.model.Aluno;
import main.java.com.educaonline.model.Curso;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.ArrayList;
import java.util.List;

public class CertificadoController {
    
    public static List<Certificado> getCertificadosDisponiveis(String alunoEmail) {
        List<Certificado> certificados = new ArrayList<>();
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        
        if (aluno != null) {
            if (aluno.getCursosMatriculados().size() > 0) {
                certificados.add(new Certificado(
                    "CERT001", 
                    aluno.getId(), 
                    "C001", 
                    aluno.getNome(), 
                    "Java para Iniciantes", 
                    40, 
                    8.5
                ));
                
                certificados.add(new Certificado(
                    "CERT002", 
                    aluno.getId(), 
                    "C002", 
                    aluno.getNome(), 
                    "Matemática para Programadores", 
                    35, 
                    7.2
                ));
            }
        }
        
        return certificados;
    }
    
    public static boolean emitirCertificado(String alunoEmail, String cursoId) {
        try {
            Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
            Curso curso = DatabaseUtil.getCursoPorId(cursoId);
            
            if (aluno != null && curso != null) {
                boolean apto = verificarAptidaoCertificado(alunoEmail, cursoId);
                
                if (apto) {
                    String certificadoId = "CERT-" + System.currentTimeMillis();
                    Certificado certificado = new Certificado(
                        certificadoId,
                        aluno.getId(),
                        cursoId,
                        aluno.getNome(),
                        curso.getTitulo(),
                        curso.getCargaHoraria(),
                        8.5 // Nota exemplo
                    );
                    System.out.println("Certificado emitido: " + certificadoId);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao emitir certificado: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean verificarCertificadoValido(String codigoVerificacao) {
        return codigoVerificacao != null && codigoVerificacao.startsWith("CERT-");
    }
    
    private static boolean verificarAptidaoCertificado(String alunoEmail, String cursoId) {
        return true;
    }
}