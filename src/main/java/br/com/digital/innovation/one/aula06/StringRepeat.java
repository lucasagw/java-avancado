package br.com.digital.innovation.one.aula06;

public class StringRepeat {

    public static void main(String[] args) {
        String nome = "Lucas";
        String aux = "";
        for (int i = 0; i < 10; i++) {
            aux += nome;
        }
        System.out.println(aux);

        System.out.println("Lucas".repeat(10));
    }
}
