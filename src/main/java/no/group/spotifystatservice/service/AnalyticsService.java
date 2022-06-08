package no.group.spotifystatservice.service;

import no.group.spotifystatservice.domain.Decile;

public interface AnalyticsService {
    Decile[] getAnalytics(String colname, Integer year);
}
