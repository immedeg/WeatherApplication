package com.example.weatherapplication;

public class CityItem {
    private String name;
    private String slug;
    private int id;

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