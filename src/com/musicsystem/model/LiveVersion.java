package com.musicsystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LiveVersion extends MusicComposition {
    private String venue;
    private LocalDate liveDate;

    public LiveVersion(String title, String artist, MusicStyle style, int durationSeconds, int year) {
        super(title, artist, style, durationSeconds, year);
        this.venue = "";
        this.liveDate = LocalDate.now();
    }

    public LiveVersion(String title, String artist, MusicStyle style, int durationSeconds, int year,
                       String venue, LocalDate liveDate) {
        super(title, artist, style, durationSeconds, year);
        this.venue = venue;
        this.liveDate = liveDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDate getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(LocalDate liveDate) {
        this.liveDate = liveDate;
    }

    @Override
    public String getType() {
        return "LIVE";
    }

    @Override
    public String getInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return String.format("[%s] %s - %s (Live at %s, %s) (%s) [%s] %d",
                getType(), getArtist(), getTitle(), venue, liveDate.format(formatter),
                getStyle(), getFormattedDuration(), getYear());
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + venue + "|" + liveDate.toString();
    }
}