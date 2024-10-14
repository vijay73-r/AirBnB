package com.airbnb.service;

import com.airbnb.dto.CityDto;
import com.airbnb.entity.City;
import com.airbnb.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService{

    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDto createCity(CityDto dto) {
        City en = mapToEntity(dto);
        City saved = cityRepository.save(en);
        return mapToDto(saved);
    }

    public City mapToEntity(CityDto dto) {
        City city = new City();
        city.setName(dto.getName());
        return city;
    }

    public CityDto mapToDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        return cityDto;
    }
}
