package no.group.spotifystatservice.controller;

import lombok.RequiredArgsConstructor;
import no.group.spotifystatservice.domain.Decile;
import no.group.spotifystatservice.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestController {

	private final AnalyticsService analyticsService;
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/")
	public Decile[] getStatistics(@RequestParam String colname, @RequestParam(required = false) Integer year) {
		return analyticsService.getAnalytics(colname,year);
	}
}
