package com.evotek.film.services;

import com.evotek.film.modules.Country;
import com.evotek.film.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country getCountryById(long countryId) {
        return countryRepository.findById(countryId).orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));
    }
}