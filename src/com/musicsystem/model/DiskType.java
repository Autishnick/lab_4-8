package com.musicsystem.model;

public enum DiskType {
    CD(700),
    DVD(4700),
    BLURAY(25000);

    private final int capacityMB;

    DiskType(int capacityMB) {
        this.capacityMB = capacityMB;
    }

    public int getCapacityMB() {
        return capacityMB;
    }

    public static DiskType fromString(String text) {
        for (DiskType type : DiskType.values()) {
            if (type.name().equalsIgnoreCase(text)) {
                return type;
            }
        }
        return CD;
    }

    @Override
    public String toString() {
        return name() + " (" + capacityMB + " MB)";
    }
}