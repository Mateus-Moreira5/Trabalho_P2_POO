package main.java.educaonline.repositories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FileRepository<T> {
    protected abstract String getFileName();
    protected abstract T convertFromString(String linha);
    
    public List<T> carregarTodos() {
        List<T> lista = new ArrayList<>();
        File arquivo = new File("data/" + getFileName());
        
        if (!arquivo.exists()) {
            return lista;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(convertFromString(linha));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
        return lista;
    }
    
    public void salvarTodos(List<T> lista) {
        File diretorio = new File("data");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/" + getFileName()))) {
            for (T item : lista) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
    
    public int getProximoId() {
        List<T> lista = carregarTodos();
        if (lista.isEmpty()) {
            return 1;
        }
        // Assumindo que a entidade tem um método getId()
        return lista.size() + 1;
    }
}