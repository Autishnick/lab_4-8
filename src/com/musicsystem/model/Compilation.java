package com.musicsystem.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Compilation {
    private static int idCounter = 1;

    private int id;
    private String name;
    private String description;
    private List<MusicComposition> tracks;

    public Compilation(String name, String description) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.tracks = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MusicComposition> getTracks() {
        return new ArrayList<>(tracks);
    }

    public void setTracks(List<MusicComposition> tracks) {
        this.tracks = tracks != null ? new ArrayList<>(tracks) : new ArrayList<>();
    }

    public void addTrack(MusicComposition track) {
        if (track != null && !tracks.contains(track)) {
            tracks.add(track);
        }
    }

    public boolean removeTrack(MusicComposition track) {
        return tracks.remove(track);
    }

    public boolean removeTrack(int trackId) {
        return tracks.removeIf(track -> track.getId() == trackId);
    }

    public int getTrackCount() {
        return tracks.size();
    }

    public int calculateDuration() {
        return tracks.stream()
                .mapToInt(MusicComposition::getDurationSeconds)
                .sum();
    }

    public String getFormattedDuration() {
        int totalSeconds = calculateDuration();
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public void sortByStyle() {
        tracks.sort(Comparator
                .comparing((MusicComposition c) -> c.getStyle().toString())
                .thenComparing(MusicComposition::getTitle));
    }

    public int estimateSizeMB() {
        int totalMinutes = calculateDuration() / 60;
        return totalMinutes * 5;
    }

    public String getInfo() {
        return String.format("Збірка #%d: %s\nОпис: %s\nКількість треків: %d\nЗагальна тривалість: %s\nОрієнтовний розмір: %d MB",
                id, name, description, getTrackCount(), getFormattedDuration(), estimateSizeMB());
    }

    @Override
    public String toString() {
        return String.format("#%d %s (%d треків, %s)", id, name, getTrackCount(), getFormattedDuration());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Compilation that = (Compilation) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}