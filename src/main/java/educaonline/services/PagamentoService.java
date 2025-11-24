package main.java.educaonline.services;

import main.java.educaonline.intities.*;
import main.java.educaonline.repositories.*;    
import java.time.LocalDate;
import java.util.List;

public class PagamentoService {
    private PagamentoRepository pagamentoRepository = new PagamentoRepository();
    private AlunoService alunoService = new AlunoService();
    
    public boolean registrarPagamento(int alunoId, double valor, String tipo) {
        if (alunoService.buscarAlunoPorId(alunoId) == null) {
            System.out.println("Aluno não encontrado!");
            return false;
        }
        
        if (valor <= 0) {
            System.out.println("Valor inválido! Deve ser maior que zero.");
            return false;
        }
        
        if (!tipo.equals("CURSO") && !tipo.equals("ASSINATURA")) {
            System.out.println("Tipo inválido! Use 'CURSO' ou 'ASSINATURA'.");
            return false;
        }
        
        // Aplicar desconto para VIPs
        double valorFinal = valor;
        if (alunoService.verificarAcessoVIP(alunoId)) {
            valorFinal = valor * 0.9; // 10% de desconto
            System.out.println("Desconto de 10% aplicado para VIP!");
        }
        
        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoRepository.getProximoId());
        pagamento.setAlunoId(alunoId);
        pagamento.setValor(valorFinal);
        pagamento.setData(LocalDate.now());
        pagamento.setTipo(tipo);
        pagamento.setStatus("PAGO");
        
        pagamentoRepository.salvar(pagamento);
        System.out.println("Pagamento registrado com sucesso! Valor: R$ " + valorFinal);
        return true;
    }
    
    public List<Pagamento> buscarPagamentosPorAluno(int alunoId) {
        return pagamentoRepository.buscarPorAlunoId(alunoId);
    }
    
    public void exibirRelatorioFinanceiro() {
        List<Pagamento> pagamentos = pagamentoRepository.carregarTodos();
        double totalRecebido = pagamentos.stream()
                .mapToDouble(Pagamento::getValor)
                .sum();
        
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.println("Total de pagamentos: " + pagamentos.size());
        System.out.println("Total recebido: R$ " + totalRecebido);
        System.out.println("============================\n");
    }
}