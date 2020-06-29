package com.github.huangp.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class InventoryItem {
    @Id
    private String id;

    @Version
    private Long version;

    @Column
    @NotBlank
    private String name;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Manufacturer manufacturer;

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
