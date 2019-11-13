package br.com.wplex.services.impl;

import br.com.wplex.models.Evento;
import br.com.wplex.services.CSVService;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CSVReaderServiceImpl implements CSVService {

    @Override
    public List<Evento> getEventosByLatLong(String path, double latitude, double longitude) {

        try {

            Reader reader = Files.newBufferedReader(Paths.get(path.concat("/eventlog.csv")));

            CSVReader csvReader = new CSVReader(reader);

            List<Evento> result = csvReader.readAll()
                    .stream()
                    .skip(1)
                    .filter(arg -> !arg[0].isEmpty())
                    .map(Evento::new)
                    .filter(evento -> evento.getDistance(latitude, longitude) < 50.0)
                    .collect(toList());

            Comparator<Evento> compareCodigo = Comparator.comparing(Evento::getCodigoRastreadorVeicular);
            Comparator<Evento> compareByDataHora = Comparator.comparing(Evento::getDataHora);

            return result.stream().sorted(compareCodigo.thenComparing(compareByDataHora)).collect(toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
