package br.com.digital.innovation.one.aula2;

import java.util.function.Consumer;

public class Consumidores {

    public static void main(String[] args) {

        Consumer<String> imprimirUmaFrase = System.out::println; //method reference
        imprimirUmaFrase.accept("Hello World");

        Consumer<String> imprimirUmaFrase2 = frase -> System.out.println(frase);
        imprimirUmaFrase2.accept("Hello World");

    }
}
