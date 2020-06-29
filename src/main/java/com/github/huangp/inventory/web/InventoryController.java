package com.github.huangp.inventory.web;

import com.github.huangp.inventory.dto.InventoryItemDto;
import com.github.huangp.inventory.dto.ManufacturerDto;
import com.github.huangp.inventory.model.InventoryItem;
import com.github.huangp.inventory.model.Manufacturer;
import com.github.huangp.inventory.repository.InventoryRepository;
import com.github.huangp.inventory.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
public class InventoryController {
    private InventoryRepository inventoryRepository;
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public InventoryController(InventoryRepository inventoryRepository, ManufacturerRepository manufacturerRepository) {
        this.inventoryRepository = inventoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @PostMapping(path = "/inventory")
    ResponseEntity<InventoryItemDto> addInventory(@RequestBody InventoryItemDto body) {
        Optional<InventoryItem> inventoryItem = inventoryRepository.findByName(body.getName());
        if (inventoryItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        String id = UUID.randomUUID().toString();
        Optional<Manufacturer> manufacturerOptional = manufacturerRepository.findById(body.getManufacturer().getName());

        Manufacturer manufacturer = manufacturerOptional.orElseGet(() -> {
            // need to create the manufacture first
            Manufacturer newManufacturer = new Manufacturer();
            newManufacturer.setName(body.getManufacturer().getName());
            newManufacturer.setHomePage(body.getManufacturer().getHomePage());
            newManufacturer.setPhone(body.getManufacturer().getPhone());
            return manufacturerRepository.save(newManufacturer);
        });

        InventoryItem toCreate = new InventoryItem();

        toCreate.setId(id);
        toCreate.setName(body.getName());
        toCreate.setManufacturer(manufacturer);
        // TODO release date set to creation date?
        toCreate.setReleaseDate(new Date());
        inventoryRepository.save(toCreate);

        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + id).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping(path = "/inventory/{id}")
    ResponseEntity<InventoryItemDto> getOne(@PathVariable String id) {
        Optional<InventoryItem> item = inventoryRepository.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(inventoryItemEntityToDto(item.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/inventory")
    ResponseEntity<List<InventoryItemDto>> getAll() {
        List<InventoryItemDto> result = StreamSupport.stream(inventoryRepository.findAll().spliterator(), false)
                .map(InventoryController::inventoryItemEntityToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    private static InventoryItemDto inventoryItemEntityToDto(InventoryItem inventoryItem) {
        Manufacturer manufacturer = inventoryItem.getManufacturer();
        ManufacturerDto manufacturerDto = new ManufacturerDto(manufacturer.getName(), manufacturer.getHomePage(), manufacturer.getPhone());
        return new InventoryItemDto(inventoryItem.getId(), inventoryItem.getName(), inventoryItem.getReleaseDate(), manufacturerDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
