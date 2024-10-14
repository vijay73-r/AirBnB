package com.airbnb.controller;

import com.airbnb.dto.PropertyDto;
import com.airbnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @PostMapping("/add")
    public ResponseEntity<PropertyDto> addProperty(@RequestParam long cityId, @RequestParam long countryId, @RequestBody PropertyDto propertyDto){
        PropertyDto property = propertyService.createProperty(cityId, countryId, propertyDto);
        return  new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping("/propertyresult")
    public List<PropertyDto> searchProperty(@RequestParam("city") String city){
        List<PropertyDto> dto = propertyService.searchProperty(city);
        return dto;
    }

}
