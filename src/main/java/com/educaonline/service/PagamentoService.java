package main.java.com.educaonline.service;

import main.java.com.educaonline.model.*;

public class PagamentoService {
    
    public static Pagamento processarPagamento(String alunoId, String matriculaId, double valor, String metodo) {
        String pagamentoId = "PAG-" + System.currentTimeMillis();
        Pagamento pagamento = new Pagamento(pagamentoId, alunoId, matriculaId, valor, metodo);
        pagamento.processarPagamento();
        return pagamento;
    }
    
    public static double calcularDescontoVIP(double valorOriginal, boolean isVIP) {
        if (isVIP) {
            return valorOriginal * 0.8;
        }
        return valorOriginal;
    }
    
    public static double calcularParcela(double valorTotal, int parcelas) {
        if (parcelas <= 0) return valorTotal;
        return valorTotal / parcelas;
    }
    
    public static boolean validarCartao(String numeroCartao, String dataValidade, String cvv) {
        return numeroCartao != null && numeroCartao.length() == 16 &&
               dataValidade != null && dataValidade.matches("\\d{2}/\\d{2}") &&
               cvv != null && cvv.length() == 3;
    }
}