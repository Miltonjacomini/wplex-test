package br.com.wplex.services.impl;

import br.com.wplex.models.Evento;
import br.com.wplex.services.CSVService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CSVNativeServiceImpl implements CSVService {

    private List<String[]> getAllEventosByPath(String path) {

        List<String[]> rowList = new ArrayList();
        String cvsSplitBy = ",";
        String encoding = "UTF-8";
        BufferedReader br2 = null;
        String line;

        try {

            br2 =  new BufferedReader(new InputStreamReader(new FileInputStream(path.concat(CSV_FILE_NAME)), encoding));

            while ((line = br2.readLine()) != null) {

                if (line.contains("device") || line.length() <= 0) continue;

                rowList.add(convertLineInArrayOfObjects(cvsSplitBy, line));

            }

            return rowList;

        } catch(Exception ex){
            System.out.println(ex);
            System.out.println("fix"+ex);
        }

        return Collections.emptyList();
    }

    private String[] convertLineInArrayOfObjects(String cvsSplitBy, String line) {

        String payload = line.substring(line.indexOf("\"")+2, line.lastIndexOf("\"")-2);
        String lineWithoutPayload = line.replace(line.substring(line.indexOf("\""), line.lastIndexOf("\"")+2), "");
        String[] arraySplited = lineWithoutPayload.split(cvsSplitBy);
        String company = arraySplited[3];

        List<String> result = Arrays.asList(arraySplited[0], arraySplited[1], arraySplited[2], payload, company);

        return result.toArray(new String[result.size()]);
    }

    @Override
    public List<Evento> getEventosByLatLong(String path, double latitude, double longitude) {

        List<Evento> allEventos = this.getAllEventosByPath(path).stream().map(Evento::new).collect(toList());

        List<Evento> result = allEventos.stream().filter(evento -> evento.getDistance(latitude, longitude) < 50.0)
                .collect(toList());

        Comparator<Evento> compareCodigo = Comparator.comparing(Evento::getCodigoRastreadorVeicular);
        Comparator<Evento> compareByDataHora = Comparator.comparing(Evento::getDataHora);

        return result.stream().sorted(compareCodigo.thenComparing(compareByDataHora)).collect(toList());
    }



}
