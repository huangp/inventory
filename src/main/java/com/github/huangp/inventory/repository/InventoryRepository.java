package com.github.huangp.inventory.repository;

import com.github.huangp.inventory.model.InventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryRepository extends CrudRepository<InventoryItem, String> {
    Optional<InventoryItem> findByName(String name);
}
