package com.github.huangp.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Access(AccessType.FIELD)
public class Manufacturer {
    @Id
    @NotBlank
    private String name;

    @Version
    private Long version;

    @Column
    private String homePage;

    @Column
    private String phone;

    public String getName() {
        return name;
    }

    public Long getVersion() {
        return version;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
