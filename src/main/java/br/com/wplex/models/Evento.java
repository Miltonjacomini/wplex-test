package br.com.wplex.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import services.Haversine;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    private long codigoRastreadorVeicular;
    private String idTipoEvento;
    private String dataHoraISO8601;
    private String payload;
    private String company;

    public Evento(String[] csvContent) {
        if (!csvContent[0].isEmpty() && csvContent[0].length() > 0) {
            this.codigoRastreadorVeicular = Long.valueOf(csvContent[0]);
            this.idTipoEvento = csvContent[1];
            this.dataHoraISO8601 = csvContent[2];
            this.payload = csvContent[3];
            this.company = csvContent[4];
        }
    }

    public double getDistance(double latitude, double longitude) {
        return Haversine.distance(latitude, longitude, getLatitude(), getLongitude());
    }

    public double getLatitude() {

        String[] payload = this.getPayload().split(",");
        String latitude = payload[2];

        if (latitude.isEmpty())
            return 0L;

        return Double.valueOf(latitude);
    }

    public double getLongitude() {

        String[] payload = this.getPayload().split(",");
        String longitude = payload[3];

        if (longitude.isEmpty())
            return 0L;

        longitude = longitude.replace("<\"", "").replace("<", "");

        return Double.valueOf(longitude);
    }

    public Date getDataHora() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd()
                .optionalStart().appendOffset("+HHMM", "+0000").optionalEnd()
                .optionalStart().appendOffset("+HH", "Z").optionalEnd()
                .toFormatter();

        return Date.from(OffsetDateTime.parse(this.dataHoraISO8601, formatter).toInstant());
    }

    public String getFormatedResult(double latitude, double longitude) {
        return this.getCodigoRastreadorVeicular() + ";"+
               this.getDistance(latitude, longitude) + ";" +
               this.getDataHoraISO8601() + ";" +
               this.getPayload();
    }
}
