package com.musicsystem.model;

public abstract class MusicComposition {
    private static int idCounter = 1;

    private int id;
    private String title;
    private String artist;
    private MusicStyle style;
    private int durationSeconds;
    private int year;

    public MusicComposition(String title, String artist, MusicStyle style, int durationSeconds, int year) {
        this.id = idCounter++;
        this.title = title;
        this.artist = artist;
        this.style = style;
        this.durationSeconds = durationSeconds;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public MusicStyle getStyle() {
        return style;
    }

    public void setStyle(MusicStyle style) {
        this.style = style;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFormattedDuration() {
        int hours = durationSeconds / 3600;
        int minutes = (durationSeconds % 3600) / 60;
        int seconds = durationSeconds % 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public boolean validate() {
        return title != null && !title.trim().isEmpty() &&
                artist != null && !artist.trim().isEmpty() &&
                style != null &&
                durationSeconds > 0 &&
                year >= 1900 && year <= 2100;
    }

    public abstract String getType();

    public String getInfo() {
        return String.format("[%s] %s - %s (%s) [%s] %d",
                getType(), artist, title, style, getFormattedDuration(), year);
    }

    public String toFileString() {
        return String.format("%d|%s|%s|%s|%s|%d|%d",
                id, getType(), title, artist, style.name(), durationSeconds, year);
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MusicComposition that = (MusicComposition) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}