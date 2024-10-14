package com.airbnb.service;

import com.airbnb.dto.PropertyDto;
import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import com.airbnb.entity.Property;
import com.airbnb.repository.CityRepository;
import com.airbnb.repository.CountryRepository;
import com.airbnb.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService{

    private PropertyRepository propertyRepository;
    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    public PropertyServiceImpl(PropertyRepository propertyRepository, CountryRepository countryRepository, CityRepository cityRepository){
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public PropertyDto createProperty(long cityId, long countryId, PropertyDto dto) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new RuntimeException("City Not Found"));
        Country country = countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country Not Found"));

        Property save = mapToEntity(dto);
        save.setCity(city);
        save.setCountry(country);
        Property saved = propertyRepository.save(save);

        return mapToDto(saved);
    }

    @Override
    public List<PropertyDto> searchProperty(String cityName) {
        List<Property> properties = propertyRepository.searchProperty(cityName);
        return properties.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }


    public Property mapToEntity(PropertyDto dto) {
        Property property = new Property();
        property.setName(dto.getName());
        property.setNoOfBedroom(dto.getNoOfBedroom());
        property.setNoOfBathrooms(dto.getNoOfBathrooms());
        property.setNoOfGuests(dto.getNoOfGuests());
        property.setNoOfBeds(dto.getNoOfBeds());
        return property;
    }

    public PropertyDto mapToDto(Property property){
        PropertyDto dto = new PropertyDto();
        dto.setId(property.getId());
        dto.setName(property.getName());
        dto.setNoOfGuests(property.getNoOfGuests());
        dto.setNoOfBedroom(property.getNoOfBedroom());
        dto.setNoOfBeds(property.getNoOfBeds());
        dto.setNoOfBathrooms(property.getNoOfBathrooms());

        dto.setCity(property.getCity().getId());
        dto.setCountry(property.getCountry().getId());
        return dto;
    }


}
