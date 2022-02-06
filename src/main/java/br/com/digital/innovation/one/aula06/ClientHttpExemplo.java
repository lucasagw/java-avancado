package br.com.digital.innovation.one.aula06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ClientHttpExemplo {

    static ExecutorService executor = Executors.newFixedThreadPool(6, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            System.out.println("Nova Thread criada " + (thread.isDaemon() ? "daemon" : "")
                    + " Thread Group :: " + thread.getThreadGroup());
            return thread;
        }
    });

    public static void main(String[] args) throws Exception {
        //  connectAndPrintURLJavaOracle();
        // connectAndPrintURLJavaOracleJdk11();
        connectAkammiHttp11Client();
    }

    private static void connectAkammiHttp11Client() throws Exception { //or 2
        System.out.println("Running HTTP/2 example ..."); // 1.1
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2) //HTTP_1_1
                    .proxy(ProxySelector.getDefault())
                    .build();

            long start = System.currentTimeMillis();

            HttpRequest request = HttpRequest.newBuilder()
                    .GET().uri(URI.create("https://http2.akamai.com/demo/h2_demo_frame.html"))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status code :: " + response.statusCode());
            System.out.println("Headers response :: " + response.headers());
            String responseBody = response.body();
            System.out.println(responseBody);

            List<Future<?>> future = new ArrayList<>();

            responseBody
                    .lines()
                    .filter(line -> line.trim().startsWith("<img height"))
                    .map(line -> line.substring(line.indexOf("src='") + 5, line.indexOf("'/>")))
                    .forEach(image -> {
                        Future<?> imgFuture = executor.submit(() -> {
                            HttpRequest imgRequest = HttpRequest.newBuilder()
                                    .GET().uri(URI.create("https://http2.akamai.com" + image))
                                    .build();
                            try {
                                HttpResponse<String> imagesResponse = httpClient.send(imgRequest, HttpResponse.BodyHandlers.ofString());
                                System.out.println("Imagem Carregada :: " + image + ", Status code :: " + imagesResponse.statusCode());
                            } catch (IOException | InterruptedException e) {
                                System.out.println("Mensagem de error durante requesição para recuperar a imagem" + image);
                            }
                        });
                        future.add(imgFuture);
                        System.out.println("Submetido um futuro para imagem ::  " + image);
                    });
            future.forEach(f -> {
                try {
                    f.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error ao esperar carregar imagem do futuro");
                }
            });

            long end = System.currentTimeMillis();
            System.out.println("Tempo de carregamento total :: " + (end - start) + " ms");
        } finally {
            executor.shutdown();
        }
    }

    private static void connectAndPrintURLJavaOracle() {
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

    private static void connectAndPrintURLJavaOracleJdk11() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET().uri(URI.create("https://docs.oracle.com/javase/10/language/"))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status code :: " + response.statusCode());
        System.out.println("Headers response :: " + response.headers());
        System.out.println(response.body());
    }
}
