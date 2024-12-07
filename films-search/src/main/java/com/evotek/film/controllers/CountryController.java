package com.evotek.film.controllers;

import com.evotek.film.modules.Country;
import com.evotek.film.services.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Countries", description = "The Countries API")
@RestController
@RequestMapping("/api1/v1")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    @Operation(summary = "Gets all countries", tags = "countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the countries",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Country.class))))
    })
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }
    @Operation(summary = "Create new country", tags = "countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created country", // 201 Created - более подходящий код ответа
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Country.class)))
    })
    @PostMapping("/countries")
    public Country createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    @Operation(summary = "Get country by id", tags = "countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found country with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Country.class)))
    })
    @GetMapping("/countries/{id}")
    public Country getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }
}