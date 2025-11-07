package com.musicsystem.model;

public class Remix extends MusicComposition {
    private String originalArtist;
    private String remixer;

    public Remix(String title, String artist, MusicStyle style, int durationSeconds, int year) {
        super(title, artist, style, durationSeconds, year);
        this.originalArtist = artist;
        this.remixer = "";
    }

    public Remix(String title, String artist, MusicStyle style, int durationSeconds, int year,
                 String originalArtist, String remixer) {
        super(title, artist, style, durationSeconds, year);
        this.originalArtist = originalArtist;
        this.remixer = remixer;
    }

    public String getOriginalArtist() {
        return originalArtist;
    }

    public void setOriginalArtist(String originalArtist) {
        this.originalArtist = originalArtist;
    }

    public String getRemixer() {
        return remixer;
    }

    public void setRemixer(String remixer) {
        this.remixer = remixer;
    }

    @Override
    public String getType() {
        return "REMIX";
    }

    @Override
    public String getInfo() {
        return String.format("[%s] %s - %s (Remix by %s) (%s) [%s] %d",
                getType(), originalArtist, getTitle(), remixer, getStyle(),
                getFormattedDuration(), getYear());
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + originalArtist + "|" + remixer;
    }
}