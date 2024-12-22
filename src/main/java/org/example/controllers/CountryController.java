package org.example.controllers;

import org.example.modules.Country;
import org.example.services.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Page<Country> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return countryService.getAllCountries(page, size);
    }
    @Operation(summary = "Create new country", tags = "countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created country", // 201 Created - более подходящий код ответа
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Country.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
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
    @Operation(summary = "Update country", tags = "countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated country",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Country.class))),
            @ApiResponse(responseCode = "404", description = "Country not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country countryDetails) {
        Country existingCountry = countryService.getCountryById(id);
        if (existingCountry == null) {
            return ResponseEntity.notFound().build();
        }
        existingCountry.setCountryName(countryDetails.getCountryName());
        existingCountry.setCountryCode(countryDetails.getCountryCode());
        existingCountry.setLinkPhoto(countryDetails.getLinkPhoto());
        if (countryDetails.getCountryMovies() != null) {
            existingCountry.getCountryMovies().clear();
            existingCountry.getCountryMovies().addAll(countryDetails.getCountryMovies());
        }
        Country updatedCountry = countryService.createCountry(existingCountry);
        return ResponseEntity.ok(updatedCountry);
    }
}