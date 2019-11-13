package br.com.wplex;

import br.com.wplex.models.ApplicationMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> arguments = Arrays.asList(args);
        if (arguments.contains("--location")) {

            List<String> latLong = getLatLong(arguments);

            startProgram(arguments, latLong);

        }
    }

    private static List<String> getLatLong(List<String> arguments) {
        return arguments.subList(arguments.indexOf("--location") + 1, arguments.size());
    }

    public static void startProgram(List<String> arguments, List<String> latLong) throws IOException {

        Double latitude = Double.valueOf(latLong.get(0));
        Double longitude = Double.valueOf(latLong.get(1));

        if (arguments.contains("--native")) {
            appMode(ApplicationMode.NATIVE, latitude, longitude);
        } else if (arguments.contains("--opencsv")) {
            appMode(ApplicationMode.OPEN_CSV, latitude, longitude);
        }

    }

    private static void appMode(ApplicationMode strategy, double latitude, double longitude) throws IOException {
        strategy.execute(latitude, longitude);
    }

}
