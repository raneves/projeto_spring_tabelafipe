package br.com.romulo.projeto_spring_tabelafipe.service;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper(); // Converte JSON em objetos Java automaticamente

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe); // Converte JSON em objeto da classe especificada
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        CollectionType lista = mapper.getTypeFactory() // Cria fábrica para tipos genéricos
                                     .constructCollectionType(List.class, classe); // Define tipo List contendo objetos da classe
        try {
            return mapper.readValue(json, lista); // Converte JSON em lista de objetos Java
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
