package com.github.huangp.inventory.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class InventoryItemDto {
    private final String id;
    @NotBlank
    private final String name;
    private final Date releaseDate;
    @Valid
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
