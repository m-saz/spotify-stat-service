package no.group.spotifystatservice.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Song {
    @CsvBindByPosition(position = 1)
    private String song;
    @CsvBindByPosition(position = 2)
    private int durationMs;
    @CsvBindByPosition(position = 4)
    private int year;
    @CsvBindByPosition(position = 5)
    private int popularity;
    @CsvBindByPosition(position = 6)
    private double danceability;
    @CsvBindByPosition(position = 7)
    private double energy;
    @CsvBindByPosition(position = 8)
    private int key;
}
