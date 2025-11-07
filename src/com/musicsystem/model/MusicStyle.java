package com.musicsystem.model;

public enum MusicStyle {
    ROCK("Rock"),
    POP("Pop"),
    JAZZ("Jazz"),
    CLASSICAL("Classical"),
    ELECTRONIC("Electronic"),
    HIP_HOP("Hip-Hop"),
    METAL("Metal"),
    BLUES("Blues"),
    COUNTRY("Country"),
    REGGAE("Reggae"),
    OTHER("Other");

    private final String displayName;

    MusicStyle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MusicStyle fromString(String text) {
        for (MusicStyle style : MusicStyle.values()) {
            if (style.displayName.equalsIgnoreCase(text) || style.name().equalsIgnoreCase(text)) {
                return style;
            }
        }
        return OTHER;
    }

    @Override
    public String toString() {
        return displayName;
    }
}