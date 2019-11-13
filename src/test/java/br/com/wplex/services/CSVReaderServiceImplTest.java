package br.com.wplex.services;


import br.com.wplex.models.Evento;
import br.com.wplex.services.impl.CSVNativeServiceImpl;
import br.com.wplex.services.impl.CSVReaderServiceImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class CSVReaderServiceImplTest {

    @Test
    public void shouldFilterAll50Meters() {

        CSVService csvService = new CSVReaderServiceImpl();

        double latitude = -23.70041;
        double longitude = -46.53713;

        List<Evento> eventosByLatLong =
                csvService.getEventosByLatLong(getClass().getResource("/csv/input").getPath(),
                        latitude, longitude);

        assertFalse(eventosByLatLong.isEmpty());

    }

    @Test
    public void shouldFilterAll50MetersNative() {

        CSVService csvService = new CSVNativeServiceImpl();

        double latitude = -23.70041;
        double longitude = -46.53713;

        List<Evento> eventosByLatLong =
                csvService.getEventosByLatLong(getClass().getResource("/csv/input").getPath(),
                        latitude, longitude);

        assertFalse(eventosByLatLong.isEmpty());

    }

}
