package br.com.digital.innovation.one.aula2;

import java.util.function.Predicate;

public class Predicados {

    public static void main(String[] args) {

        Predicate<String> estaVazio = valor -> valor.isEmpty();
        System.out.println(estaVazio.test(""));
        System.out.println(estaVazio.test("Joao"));

        Predicate<String> estaVazio2 = String::isEmpty; //method reference
        System.out.println(estaVazio2.test(""));
        System.out.println(estaVazio2.test("Joao"));

    }
}
