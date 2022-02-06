package br.com.digital.innovation.one.aula3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class FutureExemplo {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        Casa casa = new Casa(new Quarto());
        // casa.obterAfazresDaCasa().forEach(atividade -> threadPool.execute(() -> {
        List<? extends Future<String>> futuros =
                new CopyOnWriteArrayList<>(casa.obterAfazresDaCasa().stream()
                        .map(atividade -> threadPool.submit(() -> {
                            try {
                                return atividade.realizar();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })).collect(Collectors.toList()));
        while (true) {
            int numeroAtividadesNaoFinalizadas = 0;
            for (Future<?> futuro : futuros) {
                if (futuro.isDone()) {
                    try {
                        System.out.println("Parabéns voce terminou de " + futuro.get());
                        futuros.remove(futuro);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    numeroAtividadesNaoFinalizadas++;
                }
            }
            if (futuros.stream().allMatch(Future::isDone)) {
                break;
            }
            System.out.println("Numero de atividades nao finalizadas :: " + numeroAtividadesNaoFinalizadas);
            Thread.sleep(500);
        }
        threadPool.shutdown();
    }
}

class Casa {
    private List<Comodo> comodos;

    Casa(Comodo... comodos) {
        this.comodos = Arrays.asList(comodos);
    }

    List<Atividade> obterAfazresDaCasa() {
        return this.comodos.stream().map(Comodo::obterAfazresDoComodo)
                .reduce(new ArrayList<Atividade>(), (pivo, atividades) -> {
                    pivo.addAll(atividades);
                    return pivo;
                });
    }
}

interface Atividade {
    String realizar() throws InterruptedException;
}

abstract class Comodo {

    abstract List<Atividade> obterAfazresDoComodo();
}

class Quarto extends Comodo {

    @Override
    List<Atividade> obterAfazresDoComodo() {
        return Arrays.asList(
                this::arrumarACama,
                this::varrerQuarto,
                this::arrumarGuardaRoupa
        );
    }

    private String arrumarGuardaRoupa() throws InterruptedException {
        Thread.sleep(5000);
        String um = "arrumar o guarda roupa";
        System.out.println(um);
        return um;
    }

    private String varrerQuarto() throws InterruptedException {
        Thread.sleep(7000);
        String dois = "varrer quarto";
        System.out.println(dois);
        return dois;
    }

    private String arrumarACama() throws InterruptedException {
        Thread.sleep(10000);
        String tres = "arrumar a cama";
        System.out.println(tres);
        return tres;
    }
}