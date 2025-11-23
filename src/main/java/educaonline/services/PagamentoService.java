package main.java.educaonline.services;

import main.java.educaonline.intities.*;
import main.java.educaonline.repositories.*;
import java.time.LocalDate;
import java.util.List;

public class PagamentoService {
    private PagamentoRepository pagamentoRepository = new PagamentoRepository();
    private AlunoService alunoService = new AlunoService();
    
    public void registrarPagamento(int alunoId, double valor, String tipo) {
        if (alunoService.buscarAlunoPorId(alunoId) == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }
        
        // Aplicar desconto para VIPs
        if (alunoService.verificarAcessoVIP(alunoId)) {
            valor = valor * 0.9; // 10% de desconto
            System.out.println("Desconto de 10% aplicado para VIP!");
        }
        
        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoRepository.getProximoId());
        pagamento.setAlunoId(alunoId);
        pagamento.setValor(valor);
        pagamento.setData(LocalDate.now());
        pagamento.setTipo(tipo);
        pagamento.setStatus("PAGO");
        
        pagamentoRepository.salvar(pagamento);
        System.out.println("Pagamento registrado com sucesso! Valor: R$ " + valor);
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