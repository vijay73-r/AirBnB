package com.airbnb.service;

import com.airbnb.dto.CountryDto;
import com.airbnb.entity.Country;
import com.airbnb.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService{
    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDto createCountry(CountryDto dto) {
        Country country = mapToEntity(dto);
        Country saved = countryRepository.save(country);

        return  mapToDto(saved);
    }

    public Country mapToEntity(CountryDto dto) {
        Country country = new Country();
        country.setName(dto.getName());
        return country;
    }

    public CountryDto mapToDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());
        return countryDto;
    }
}
