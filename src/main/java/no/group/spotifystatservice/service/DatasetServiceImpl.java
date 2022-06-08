package no.group.spotifystatservice.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import no.group.spotifystatservice.domain.Song;
import no.group.spotifystatservice.exception.InvalidColumnNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DatasetServiceImpl implements DatasetService {

	private final List<Song> dataset;
	
	DatasetServiceImpl()
	{
		dataset = initDatasets();
	}

	private static List<Song> initDatasets() {
		List<Song> dataset = null;
		try(BufferedReader reader = new BufferedReader(
				new FileReader(
						new ClassPathResource("csv/songs_normalize.csv").getFile()))) {
			ColumnPositionMappingStrategy<Song> strategy = getMappingStrategy();
			CsvToBean<Song> csv = new CsvToBeanBuilder<Song>(reader)
					.withType(Song.class)
					.withMappingStrategy(strategy)
					.withSkipLines(1)
					.build();
			dataset = csv.parse();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return dataset;
	}

	private static ColumnPositionMappingStrategy<Song> getMappingStrategy() {
		ColumnPositionMappingStrategy<Song> strategy = new ColumnPositionMappingStrategy<>();
		strategy.setType(Song.class);
		strategy.setColumnMapping("artist","song","duration_ms","explicit","year","popularity","danceability",
				"energy","key","loudness","mode","speechiness","acousticness","instrumentalness","liveness",
				"valence","tempo","genre");
		return strategy;
	}

	@Override
	public List<Song> getDataset(String colname) {
//		switch (colname){
//			case "duration_ms": arrayIndex = 0; break;
//			case "year": arrayIndex = 1; break;
//			case "popularity": arrayIndex = 2; break;
//			case "danceability": arrayIndex = 3; break;
//			case "energy": arrayIndex = 4; break;
//			case "key": arrayIndex = 5; break;
//			default: throw new InvalidColumnNameException("Invalid column name: "+colname+
//					"Valid colnames are: duration_ms, year, popularity, danceability, energy, key");
//		}
		return dataset;
	}

}