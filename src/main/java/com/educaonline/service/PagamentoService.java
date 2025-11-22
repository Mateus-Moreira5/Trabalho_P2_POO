package main.java.com.educaonline.service;

import main.java.com.educaonline.model.*;
import main.java.com.educaonline.util.DatabaseUtil;
import java.util.List;

public class PagamentoService {
    
    public static Pagamento processarPagamento(String alunoEmail, String cursoId, String metodoPagamento) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        Curso curso = DatabaseUtil.getCursoPorId(cursoId);
        
        if (aluno == null || curso == null) {
            return null;
        }
        
        // Calcular valor com desconto VIP
        double valor = curso.getPreco();
        if (aluno instanceof AlunoVIP) {
            valor = valor * 0.8; // 20% de desconto para VIPs
        }
        
        // Criar pagamento
        String pagamentoId = "PAG-" + System.currentTimeMillis();
        String matriculaId = "MAT" + System.currentTimeMillis();
        
        Pagamento pagamento = new Pagamento(pagamentoId, aluno.getId(), matriculaId, valor, metodoPagamento);
        pagamento.processarPagamento();
        
        DatabaseUtil.adicionarPagamento(pagamento);
        return pagamento;
    }
    
    public static List<Pagamento> getHistoricoPagamentos(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return List.of();
        
        return DatabaseUtil.getPagamentos().stream()
                .filter(pag -> pag.getAlunoId().equals(aluno.getId()))
                .toList();
    }
    
    public static double getTotalPago(String alunoEmail) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return 0.0;
        
        return DatabaseUtil.getPagamentos().stream()
                .filter(pag -> pag.getAlunoId().equals(aluno.getId()) && pag.isPago())
                .mapToDouble(Pagamento::getValor)
                .sum();
    }
    
    public static boolean hasPagamentoPendente(String alunoEmail, String cursoId) {
        Aluno aluno = DatabaseUtil.getAlunoPorEmail(alunoEmail);
        if (aluno == null) return false;
        
        return DatabaseUtil.getPagamentos().stream()
                .anyMatch(pag -> pag.getAlunoId().equals(aluno.getId()) && pag.isPendente());
    }
}