package br.com.digital.innovation.one.aula3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class PararelStreamExemplo {
    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        IntStream.range(1, 100000).forEach(num -> fatorial(num)); //serial
        long fim = System.currentTimeMillis();
        System.out.println("Tempo de execução Serial :: " + (fim - inicio));

        inicio = System.currentTimeMillis();
        IntStream.range(1, 100000).parallel().forEach(num -> fatorial(num)); //Pararell
        fim = System.currentTimeMillis();
        System.out.println("Tempo de execução Pararell :: " + (fim - inicio));

        List<String> nomes = Arrays.asList("Joao", "Paulo", "Oliveira", "Santos");
        nomes.parallelStream().forEach(System.out::println);
        //  nomes.stream().parallel();
    }

    public static long fatorial(long num) {
        int fat = 1;
        for (int i = 2; i <= num; i++) {
            fat *= i;
        }
        return fat;
    }
}
