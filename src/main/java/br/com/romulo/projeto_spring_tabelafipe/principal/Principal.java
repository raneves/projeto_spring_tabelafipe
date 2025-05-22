package br.com.romulo.projeto_spring_tabelafipe.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.romulo.projeto_spring_tabelafipe.model.Dados;
import br.com.romulo.projeto_spring_tabelafipe.model.Modelos;
import br.com.romulo.projeto_spring_tabelafipe.model.Veiculo;
import br.com.romulo.projeto_spring_tabelafipe.service.ConsumoApi;
import br.com.romulo.projeto_spring_tabelafipe.service.ConverteDados;

public class Principal {
    private static final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        String tipoVeiculo = solicitarTipoVeiculo();
        String urlMarcas = construirUrlMarcas(tipoVeiculo);

        List<Dados> marcas = obterMarcas(urlMarcas);
        exibirMarcas(marcas);

        String codigoMarca = solicitarCodigo("marca");
        String urlModelos = urlMarcas + "/" + codigoMarca + "/modelos";

        Modelos modeloLista = obterModelos(urlModelos);
        exibirModelos(modeloLista);

        String nomeVeiculo = solicitarNomeVeiculo();
        List<Dados> modelosFiltrados = filtrarModelos(modeloLista, nomeVeiculo);
        exibirModelosFiltrados(modelosFiltrados);

        String codigoModelo = solicitarCodigo("modelo");
        String urlAnos = urlModelos + "/" + codigoModelo + "/anos";

        List<Dados> anos = obterAnos(urlAnos);
        List<Veiculo> veiculos = obterVeiculosComAvaliacoes(urlAnos, anos);

        exibirVeiculos(veiculos);
    }

    private String solicitarTipoVeiculo() {
        String menu = """
               *** OPÇÕES ***
               Carro
               Moto
               Caminhão

               Digite uma das opções para consulta:
               """;
        System.out.println(menu);
        return leitura.nextLine().toLowerCase();
    }

    private String construirUrlMarcas(String tipoVeiculo) {
        if (tipoVeiculo.contains("carr")) {
            return URL_BASE + "carros/marcas";
        } else if (tipoVeiculo.contains("mot")) {
            return URL_BASE + "motos/marcas";
        } else {
            return URL_BASE + "caminhoes/marcas";
        }
    }

    private List<Dados> obterMarcas(String url) {
        String json = consumo.obterDados(url);
        return conversor.obterLista(json, Dados.class);
    }

    private void exibirMarcas(List<Dados> marcas) {
        marcas.stream()                     // Cria fluxo sequencial de elementos da lista
              .sorted(Comparator.comparing(Dados::codigo)) // Ordena pelo código em ordem crescente
              .forEach(System.out::println); // Imprime cada elemento no console
    }

    private String solicitarCodigo(String tipo) {
        System.out.printf("Informe o código da %s para consulta: %n", tipo);
        return leitura.nextLine();
    }

    private Modelos obterModelos(String url) {
        String json = consumo.obterDados(url);
        return conversor.obterDados(json, Modelos.class);
    }

//    private void exibirModelos(Modelos modeloLista) {
//        System.out.println("\nModelos dessa marca:");
//        modeloLista.modelos().stream()    // Cria fluxo sequencial dos modelos
//                   .sorted(Comparator.comparing(Dados::codigo)) // Ordena modelos pelo código crescente
//                   .forEach(System.out::println); // Imprime cada modelo no console
//    }
    
    private void exibirModelos(Modelos modeloLista) {
        System.out.println("\nModelos dessa marca:");
        modeloLista.modelos().stream()    // Cria fluxo sequencial dos modelos
        .sorted(Comparator.comparing(d -> d.nome().toLowerCase())) // Ordena modelos ordem alfabetica
        .forEach(System.out::println);

    }


    private String solicitarNomeVeiculo() {
        System.out.println("\nDigite um trecho do nome do veículo a ser buscado:");
        return leitura.nextLine(); // Lê texto digitado pelo usuário
    }

    private List<Dados> filtrarModelos(Modelos modeloLista, String nomeVeiculo) {
        return modeloLista.modelos().stream()  // Cria fluxo sequencial dos modelos
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase())) // Filtra por nome veículo ignorando maiúsculas
                .collect(Collectors.toList());  // Coleta resultados em nova lista
    }

    private void exibirModelosFiltrados(List<Dados> modelosFiltrados) {
        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println); // Imprime todos modelos filtrados
    }

    private List<Dados> obterAnos(String url) {
        String json = consumo.obterDados(url); // Busca dados JSON da URL via API
        return conversor.obterLista(json, Dados.class);
    }

    private List<Veiculo> obterVeiculosComAvaliacoes(String urlBase, List<Dados> anos) {
        List<Veiculo> veiculos = new ArrayList<>();
        for (Dados ano : anos) {
            String urlAno = urlBase + "/" + ano.codigo();
            String json = consumo.obterDados(urlAno);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        return veiculos;
    }

    private void exibirVeiculos(List<Veiculo> veiculos) {
        System.out.println("\nTodos os veículos filtrados com avaliações por ano:");
        veiculos.forEach(System.out::println); // Imprime cada veículo com avaliação
    }
}