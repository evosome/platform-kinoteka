package com.evotek.aboutfilms.controllers;

import com.evotek.aboutfilms.modules.HallLayout;
import com.evotek.aboutfilms.services.HallLayoutService;
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
@Tag(name = "HallLayouts", description = "The Hall Layouts API")
@RestController
@RequestMapping("/api1/v1")
public class HallLayoutController {
    public static HallLayoutService hallLayoutService;
    @Autowired
    public HallLayoutController(HallLayoutService hallLayoutService) {
        HallLayoutController.hallLayoutService = hallLayoutService;
    }
    @Operation(summary = "Gets all hall layouts", tags = "hallLayouts")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the hall layouts",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = HallLayout.class)))
                    })
    })
    @GetMapping("/lay")
    public List<HallLayout> getHalls(){
        return hallLayoutService.getAllHallLayout();
    }
    @Operation(summary = "Create new hall layout", tags = "hallLayouts")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Add new hall layout",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HallLayout.class))
                    })
    })
    @PostMapping("/lay")
    public HallLayout createHalls(@RequestBody HallLayout lay){
        hallLayoutService.createHallLayout(lay);
        return lay;
    }
    @Operation(summary = "Get hall layout by id", tags = "hallLayouts")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found hall layout with id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HallLayout.class))
                    })
    })
    @GetMapping("/lay/{id}")
    public HallLayout getHallsById(@PathVariable Long id){
        return hallLayoutService.getHallLayoutById(id);
    }
}
