package com.github.huangp.inventory.dto;

import java.util.Date;

public class InventoryItemDto {
    private final String id;
    private final String name;
    private final Date releaseDate;
    private final ManufacturerDto manufacturer;

    public InventoryItemDto(String id, String name, Date releaseDate, ManufacturerDto manufacturer) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.manufacturer = manufacturer;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public ManufacturerDto getManufacturer() {
        return manufacturer;
    }
}
