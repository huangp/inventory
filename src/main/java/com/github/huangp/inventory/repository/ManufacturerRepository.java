package com.github.huangp.inventory.repository;

import com.github.huangp.inventory.model.Manufacturer;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, String> {
}
