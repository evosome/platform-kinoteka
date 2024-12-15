package org.example.services;

import org.example.modules.Cinemas;
import org.example.modules.Country;
import org.example.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Page<Country> getAllCountries(int page, int size){
        return countryRepository.findAll(PageRequest.of(page, size));
    }
    //public List<Country> getAllCountries() {
    //    return countryRepository.findAll();
    //}

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country getCountryById(long countryId) {
        return countryRepository.findById(countryId).orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));
    }
}