package com.github.huangp.inventory.dto;

public class ManufacturerDto {
    private final String name;
    private final String homePage;
    private final String phone;

    public ManufacturerDto(String name, String homePage, String phone) {
        this.name = name;
        this.homePage = homePage;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getPhone() {
        return phone;
    }
}
