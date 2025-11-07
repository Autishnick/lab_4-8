package com.musicsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Instrumental extends MusicComposition {
    private List<String> instruments;

    public Instrumental(String title, String artist, MusicStyle style, int durationSeconds, int year) {
        super(title, artist, style, durationSeconds, year);
        this.instruments = new ArrayList<>();
    }

    public Instrumental(String title, String artist, MusicStyle style, int durationSeconds, int year,
                        List<String> instruments) {
        super(title, artist, style, durationSeconds, year);
        this.instruments = instruments != null ? new ArrayList<>(instruments) : new ArrayList<>();
    }

    public List<String> getInstruments() {
        return new ArrayList<>(instruments);
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments != null ? new ArrayList<>(instruments) : new ArrayList<>();
    }

    public void addInstrument(String instrument) {
        if (instrument != null && !instrument.trim().isEmpty()) {
            this.instruments.add(instrument);
        }
    }

    @Override
    public String getType() {
        return "INSTRUMENTAL";
    }

    @Override
    public String toFileString() {
        String instrumentsStr = String.join(";", instruments);
        return super.toFileString() + "|" + instrumentsStr;
    }
}