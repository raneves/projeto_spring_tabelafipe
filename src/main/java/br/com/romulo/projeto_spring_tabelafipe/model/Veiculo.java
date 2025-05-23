package br.com.romulo.projeto_spring_tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos não mapeados do JSON
public record Veiculo(
        @JsonAlias("Valor") String valor,           // Mapeia campo "Valor" do JSON para valor
        @JsonAlias("Marca") String marca,           // Mapeia campo "Marca" do JSON para marca
        @JsonAlias("Modelo") String modelo,         // Mapeia campo "Modelo" do JSON para modelo
        @JsonAlias("AnoModelo") Integer ano,        // Mapeia campo "AnoModelo" para ano
        @JsonAlias("Combustivel") String tipoCombustivel // Mapeia campo "Combustivel"
) {

    @Override
    public String toString() { // Retorna veículo formatado como texto
        return String.format("%s %s  ano: %s valor: %s combustível: %s", // Monta string com detalhes do veículo
                marca, modelo, ano, valor, tipoCombustivel); // Insere os dados na string
    }
}
