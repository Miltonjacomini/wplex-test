package br.com.wplex.services;

import br.com.wplex.models.Evento;

import java.util.List;

public interface CSVService {

    String CSV_FILE_NAME = "/eventlog.csv";

    List<Evento> getEventosByLatLong(String path, double latitude, double longitude);
}
