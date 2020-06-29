package com.github.huangp.inventory.repository;

import com.github.huangp.inventory.model.InventoryItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends CrudRepository<InventoryItem, String> {
    Optional<InventoryItem> findByName(String name);

    List<InventoryItem> findAll(Pageable pageable);
}
