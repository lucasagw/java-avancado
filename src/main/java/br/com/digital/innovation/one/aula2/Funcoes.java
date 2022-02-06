package br.com.digital.innovation.one.aula2;

import java.util.function.Function;

public class Funcoes {

    public static void main(String[] args) {
        Function<String, String> retornarNomeAoContrario = texto -> new StringBuilder(texto).reverse().toString();
        System.out.println(retornarNomeAoContrario.apply("Joao"));

        Function<String, Integer> converterStringParaInteiroECalcularODobro = string -> Integer.valueOf(string) * 2;
        System.out.println(converterStringParaInteiroECalcularODobro.apply("20"));

        Function<String, Integer> converterStringParaInteiro = Integer::valueOf; //method reference
        System.out.println(converterStringParaInteiro.apply("20"));
    }
}
