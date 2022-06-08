package no.group.spotifystatservice.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import no.group.spotifystatservice.exception.InvalidColumnNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Service
public class DatasetServiceImpl implements DatasetService {

	private final double[][] datasets;
	
	DatasetServiceImpl()
	{
		datasets = initDatasets();
	}

	private static double[][] initDatasets() {
		double datasets[][] = new double[6][2000];
		try(BufferedReader reader = new BufferedReader(
				new FileReader(
						new ClassPathResource("csv/songs_normalize.csv").getFile()))) {
			CSVParser parser = new CSVParserBuilder()
					.withSeparator(',')
					.withQuoteChar('\"')
					.build();
			CSVReader csvReader = new CSVReaderBuilder(reader)
					.withSkipLines(1)
					.withCSVParser(parser)
					.build();
			int counter = 0;
			String[] values;
			while ((values = csvReader.readNext()) != null){
				insertData(datasets, values, counter);
				counter++;
			}
		}
		catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		return datasets;
	}

	private static void insertData(double[][] datasets, String[] values, int counter) {
		datasets[0][counter] = Double.parseDouble(values[2]); //duration_ms
		datasets[1][counter] = Double.parseDouble(values[4]); //year
		datasets[2][counter] = Double.parseDouble(values[5]); //popularity
		datasets[3][counter] = Double.parseDouble(values[6]); //danceability
		datasets[4][counter] = Double.parseDouble(values[7]); //energy
		datasets[5][counter] = Double.parseDouble(values[8]); //key
	}

	@Override
	public double[] getDataset(String colname) {
		int arrayIndex;
		switch (colname){
			case "duration_ms": arrayIndex = 0; break;
			case "year": arrayIndex = 1; break;
			case "popularity": arrayIndex = 2; break;
			case "danceability": arrayIndex = 3; break;
			case "energy": arrayIndex = 4; break;
			case "key": arrayIndex = 5; break;
			default: throw new InvalidColumnNameException("Invalid column name: "+colname+
					"Valid colnames are: duration_ms, year, popularity, danceability, energy, key");
		}
		return datasets[arrayIndex];
	}

}