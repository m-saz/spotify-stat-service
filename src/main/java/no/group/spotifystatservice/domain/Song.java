package no.group.spotifystatservice.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Song {
    @CsvBindByName
    private String song;
    @CsvBindByName
    private int duration_ms;
    @CsvBindByName
    private int year;
    @CsvBindByName
    private int popularity;
    @CsvBindByName
    private double danceability;
    @CsvBindByName
    private double energy;
    @CsvBindByName
    private int key;
}
