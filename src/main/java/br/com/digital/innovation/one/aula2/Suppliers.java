package br.com.digital.innovation.one.aula2;

import java.util.function.Supplier;

public class Suppliers { //suplidores

    public static void main(String[] args) {
        Supplier<Pessoa> instanciaPessoa = () -> new Pessoa();
        System.out.println(instanciaPessoa.get());

        Supplier<Pessoa> instanciaPessoa2 = Pessoa::new; //method reference
        System.out.println(instanciaPessoa2.get());
    }
}

class Pessoa {
    private String nome;
    private Integer idade;

    public Pessoa() {
        nome = "Lucas";
        idade = 27;
    }

    @Override
    public String toString() {
        return String.format("nome : %s, idade: %d", nome, idade);
    }
}
