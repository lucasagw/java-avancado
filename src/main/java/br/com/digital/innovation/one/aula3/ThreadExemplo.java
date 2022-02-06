package br.com.digital.innovation.one.aula3;

import static java.lang.Thread.*;

public class ThreadExemplo {

    public static void main(String[] args) {
//        Thread thread = new Thread(new BarraDeCarregamento2());
//        Thread thread1 = new Thread(new BarraDeCarregamento3());
//        thread.start();
//        thread1.start();
//        System.out.println(thread.getName());
//        System.out.println(thread1.getName());
        GeradorPDF iniciarGeradorPDF = new GeradorPDF();
        BarraDeCarregamento iniciarBarraDeCarregamento = new BarraDeCarregamento(iniciarGeradorPDF);
        iniciarGeradorPDF.start();
        iniciarBarraDeCarregamento.start();
    }
}

class GeradorPDF extends Thread {

    @Override
    public void run() {
        try {
            System.out.println("Iniciar geração de PDF");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PDF Gerado");
    }
}

class BarraDeCarregamento extends Thread {

    private Thread iniciarGeradorPDF;

    public BarraDeCarregamento(Thread iniciarGeradorPDF) {
        this.iniciarGeradorPDF = iniciarGeradorPDF;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
                if (!iniciarGeradorPDF.isAlive()) {
                    break;
                }
                System.out.println("Loading ....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class BarraDeCarregamento2 implements Runnable {

    @Override
    public void run() {

        try {
            Thread.sleep(3000);
            System.out.println("rodei BarraDeCarregamento2: ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

class BarraDeCarregamento3 implements Runnable {

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
            System.out.println("rodei BarraDeCarregamento3: ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}