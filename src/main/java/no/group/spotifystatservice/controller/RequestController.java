package no.group.spotifystatservice.controller;

import no.group.spotifystatservice.service.DatasetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class RequestController {

	private final DatasetService datasetService;
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/")
	public String getStatistics(@RequestParam String colname, @RequestParam(required = false) Integer year) {
		if (year == null) year = 1;
		
		LOG.info("colname = {}, year = {}", colname, year);
		LOG.info("{}", Arrays.toString(datasetService.getDataset(colname)));
		LOG.info("length = {}", datasetService.getDataset(colname).length);

		return "[\n"
				+ "    {\"min\" : 0, \"max\" : 100, \"count\" : 10},\n"
				+ "    {\"min\" : 101, \"max\" : 200, \"count\" : 20},\n"
				+ "    {\"min\" : 201, \"max\" : 999, \"count\" : 30}]";
	}
}
