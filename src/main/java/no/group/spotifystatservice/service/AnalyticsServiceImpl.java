package no.group.spotifystatservice.service;

import lombok.RequiredArgsConstructor;
import no.group.spotifystatservice.domain.Decile;
import no.group.spotifystatservice.domain.Song;
import no.group.spotifystatservice.exception.InvalidColumnNameException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService{

    private final DatasetService datasetService;

    @Override
    public Decile[] getAnalytics(String colname, Integer year) {
        Decile[] deciles = new Decile[10];
        for(int i=0;i<10;i++){
            deciles[i]=new Decile();
        }
        List<Song> dataset = datasetService.getDataset();
        if(year != null) {
            dataset = dataset.stream().filter(song -> song.getYear() == year).collect(Collectors.toList());
            if (dataset.size() == 0) {
                return deciles;
            }
        }
        double[] numericDataset = getNumericDataset(dataset, colname);
        double min = Arrays.stream(numericDataset).min().getAsDouble();
        double max = Arrays.stream(numericDataset).max().getAsDouble();
        generateRanges(deciles,min,max);
        countStats(numericDataset,deciles);
        return deciles;
    }

    private void countStats(double[] numericDataset, Decile[] deciles) {
        Arrays.stream(numericDataset).forEach(value -> {
            Optional<Decile> targetOptional = Arrays.stream(deciles).filter(decile -> {
                return BigDecimal.valueOf(value).compareTo(decile.getMin()) >= 0 &&
                        BigDecimal.valueOf(value).compareTo(decile.getMax()) < 0;
            }).findFirst();
            if(targetOptional.isPresent()){
                Decile target = targetOptional.get();
                target.setCount(target.getCount()+1);
            } else {
                //Uppermost border of data range has to be processed separately
                deciles[9].setCount(deciles[9].getCount()+1);
            }
        });
    }

    private void generateRanges(Decile[] deciles, double min, double max) {
        BigDecimal increment = BigDecimal.valueOf(max).subtract(BigDecimal.valueOf(min)).divide(BigDecimal.TEN);
        BigDecimal border = BigDecimal.valueOf(min);
        for(Decile decile : deciles){
            decile.setMin(border);
            border = border.add(increment);
            decile.setMax(border);
        }
    }

    private double[] getNumericDataset(List<Song> dataset, String colname) {
        double[] numericDataset = dataset.stream().mapToDouble(song -> {
            switch (colname){
                case "duration_ms": return song.getDuration_ms();
                case "year": return song.getYear();
                case "popularity": return song.getPopularity();
                case "danceability": return song.getDanceability();
                case "energy": return song.getEnergy();
                case "key": return song.getKey();
                default: throw new InvalidColumnNameException("Invalid column name: "+colname+
                        "Valid colnames are: duration_ms, year, popularity, danceability, energy, key");
            }
        }).toArray();
        return numericDataset;
    }
}
