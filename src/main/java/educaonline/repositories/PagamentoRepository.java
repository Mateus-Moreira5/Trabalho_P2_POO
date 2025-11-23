package main.java.educaonline.repositories;

import main.java.educaonline.intities.*;
import java.util.List;
import java.util.stream.Collectors;

public class PagamentoRepository extends FileRepository<Pagamento> {
    @Override
    protected String getFileName() {
        return "pagamentos.txt";
    }
    
    @Override
    protected Pagamento convertFromString(String linha) {
        return Pagamento.fromString(linha);
    }
    
    public List<Pagamento> buscarPorAlunoId(int alunoId) {
        List<Pagamento> pagamentos = carregarTodos();
        return pagamentos.stream()
                .filter(p -> p.getAlunoId() == alunoId)
                .collect(Collectors.toList());
    }
    
    public void salvar(Pagamento pagamento) {
        List<Pagamento> pagamentos = carregarTodos();
        pagamentos.add(pagamento);
        salvarTodos(pagamentos);
    }
}