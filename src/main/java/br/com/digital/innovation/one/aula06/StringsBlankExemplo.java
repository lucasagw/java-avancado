package br.com.digital.innovation.one.aula06;

public class StringsBlankExemplo {
    public static void main(String[] args) {
        String espaco = " ";
        System.out.println(espaco.isBlank());
        //System.out.println(espaco == null || espaco.length() == 0 || espaco.chars().allMatch(c -> c == ' ')); //old
    }
}