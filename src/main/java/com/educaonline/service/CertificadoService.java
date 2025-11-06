package main.java.com.educaonline.service;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;

public class CertificadoService {
    
    public static Certificado emitirCertificado(String alunoEmail, String cursoId, double notaFinal) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        Curso curso = DatabaseUtil.getCursoPorId(cursoId);
        
        if (aluno == null || curso == null) {
            return null;
        }
        
        if (notaFinal < 7.0) {
            return null;
        }
        
        if (!aluno.estaMatriculado(cursoId)) {
            return null;
        }
        
        String certificadoId = "CERT-" + System.currentTimeMillis();
        Certificado certificado = new Certificado(
            certificadoId,
            aluno.getId(),
            cursoId,
            aluno.getNome(),
            curso.getTitulo(),
            curso.getDuracaoHoras(),
            notaFinal
        );
        
        return certificado;
    }
    
    public static boolean validarCertificado(String codigoVerificacao) {
        return codigoVerificacao != null && codigoVerificacao.startsWith("CERT-");
    }
}