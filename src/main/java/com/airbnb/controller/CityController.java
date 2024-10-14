package com.airbnb.controller;

import com.airbnb.dto.CityDto;
import com.airbnb.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private CityService cityService;
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/add")
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto) {
        CityDto city = cityService.createCity(cityDto);
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }

}
