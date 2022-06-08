package no.group.spotifystatservice.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import no.group.spotifystatservice.domain.Song;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
	public List<Song> getDataset() {
		return dataset;
	}

}