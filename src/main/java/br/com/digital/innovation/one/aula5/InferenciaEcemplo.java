package br.com.digital.innovation.one.aula5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class InferenciaEcemplo {
    public static void main(String[] args) throws IOException {
        testar("jaoo", "santos");
        printarSoma(10, 5);
        printarSoma(5, 5, 5);

        connectAndPrintURLJavaOracle();
    }

    private static void connectAndPrintURLJavaOracle() {
         /* URL url = new URL("https://docs.oracle.com/javase/10/language/");
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));*/

        try {
            var url = new URL("https://docs.oracle.com/javase/10/language/");
            var urlConnection = url.openConnection();
            try (var bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                System.out.println(bufferedReader.lines().collect(Collectors.joining()).replaceAll(">", ">\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testar(String nome, String sobrenome) {
        var nomeCompleto = String.format("%s %s", nome, sobrenome);
        System.out.println(nomeCompleto);
    }

    public static void printarSoma(int a, int b) {
        var soma = a + b;
        System.out.println(soma);
    }

    public static void printarSoma(int... numeros) {
        var soma = 0;
        if (numeros.length > 0) {
            for (var numero : numeros) {
                soma += numero;
            }
            System.out.println("A soma é: " + soma);
        }
    }

    //**Consegue
    //variavel local inicializadas
    //variavel suporte no for
    //variavel try with resource


    //**Nao consegue
    //var nao pode ser utilizado em nível de classe
    //var nao pode ser utilizado como parametro
    //var nao pode ser utilizada em variaveis locais nao inicializadas
}
