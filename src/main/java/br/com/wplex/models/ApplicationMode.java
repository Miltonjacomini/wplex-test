package br.com.wplex.models;

import br.com.wplex.services.impl.CSVNativeServiceImpl;
import br.com.wplex.services.impl.CSVReaderServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public enum ApplicationMode {

    NATIVE {

        @Override
        public void execute(double latitude, double longitude) throws IOException {

            System.out.println(" EXECUTING NATIVE LIBS ::: ");
            long start = begin();

            List<Evento> eventos = new CSVNativeServiceImpl().getEventosByLatLong(new File(".").getCanonicalPath(), latitude, longitude);

            eventos.stream().forEach(evento -> System.out.println(evento.getFormatedResult(latitude, longitude)));

            end(start);

        }
    },
    OPEN_CSV {

        @Override
        public void execute(double latitude, double longitude) throws IOException {

            System.out.println(" EXECUTING OPEN_CSV LIB ::: ");
            long start = begin();

            List<Evento> eventos = new CSVReaderServiceImpl().getEventosByLatLong(new File(".").getCanonicalPath(), latitude, longitude);

            eventos.stream().forEach(evento -> System.out.println(evento.getFormatedResult(latitude, longitude)));

            end(start);

        }
    };


    private static long begin() {
        long start = System.currentTimeMillis();
        System.out.println(" ::::::::::::::::::::::::::::::::::: ");
        System.out.println(" ::: [ INICIO EVENTOS LISTADOS ] ::: ");
        return start;
    }

    private static void end(long start) {
        System.out.println(" ::: [ FIM EVENTOS LISTADOS ] ::: ");
        System.out.println(" ::::::::::::::::::::::::::::::::::: ");
        long timing = System.currentTimeMillis() - start;
        System.out.println(" ---- Time of execution program: " + TimeUnit.MILLISECONDS.toMinutes(timing) + "." + TimeUnit.MILLISECONDS.toSeconds(timing) + " sec");
        System.out.println(" ::::::::::::::::::::::::::::::::::: ");
    }

    public abstract void execute(double latitude, double longitude) throws IOException;
}
