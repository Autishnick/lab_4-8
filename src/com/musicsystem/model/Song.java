package com.musicsystem.model;

public class Song extends MusicComposition {
    private String lyrics;
    private boolean hasFeaturing;

    public Song(String title, String artist, MusicStyle style, int durationSeconds, int year) {
        super(title, artist, style, durationSeconds, year);
        this.lyrics = "";
        this.hasFeaturing = false;
    }

    public Song(String title, String artist, MusicStyle style, int durationSeconds, int year,
                String lyrics, boolean hasFeaturing) {
        super(title, artist, style, durationSeconds, year);
        this.lyrics = lyrics;
        this.hasFeaturing = hasFeaturing;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public boolean isHasFeaturing() {
        return hasFeaturing;
    }

    public void setHasFeaturing(boolean hasFeaturing) {
        this.hasFeaturing = hasFeaturing;
    }

    @Override
    public String getType() {
        return "SONG";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + lyrics + "|" + hasFeaturing;
    }
}