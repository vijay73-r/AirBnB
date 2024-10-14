package com.airbnb.service;

import com.airbnb.dto.PropertyDto;

import java.util.List;

public interface PropertyService {
    public PropertyDto createProperty(long cityId, long countryId, PropertyDto dto);

    public List<PropertyDto> searchProperty(String cityName);
}
