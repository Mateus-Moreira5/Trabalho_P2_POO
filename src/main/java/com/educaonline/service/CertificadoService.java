package main.java.com.educaonline.service;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class CertificadoService {
    
    public static Certificado emitirCertificado(String alunoEmail, String cursoId, double notaFinal) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        Curso curso = DatabaseUtil.getCursoPorId(cursoId);
        
        if (aluno == null || curso == null) {
            return null;
        }
        
        if (!aluno.estaMatriculado(cursoId)) {
            return null;
        }
        
        if (notaFinal < 7.0) {
            return null;
        }
        
        // Criar certificado
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
        
        DatabaseUtil.adicionarCertificado(certificado);
        return certificado;
    }
    
    public static List<Certificado> getCertificadosAluno(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return List.of();
        
        return DatabaseUtil.getCertificados().stream()
                .filter(cert -> cert.getAlunoId().equals(aluno.getId()))
                .toList();
    }
    
    public static List<Curso> getHistoricoCursos(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return List.of();
        
        return getCursosDoAluno(alunoEmail);
    }
    
    private static List<Curso> getCursosDoAluno(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return List.of();
        
        return aluno.getCursosMatriculados().stream()
                .map(DatabaseUtil::getCursoPorId)
                .filter(curso -> curso != null)
                .toList();
    }
}