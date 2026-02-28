package com.example.weatherapplication;

public class CityItem {
    private final String name;
    private final String slug;
    private final int id;

    public CityItem(String name, String slug, int id) {
        this.name = name;
        this.slug = slug;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public int getId() {
        return id;
    }
}
