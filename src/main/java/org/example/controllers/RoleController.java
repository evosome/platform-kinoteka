package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.modules.Role;
import org.example.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api1/v1")
@CrossOrigin
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Gets all roles", tags = "roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the roles",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Role.class))))
    })
    @GetMapping("/roles")
    public Page<Role> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return roleService.getAllRoles(page, size);
    }

    @Operation(summary = "Create new role", tags = "roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created role",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Role.class)))
    })
    @PostMapping("/roles")
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @Operation(summary = "Get role by id", tags = "roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found role with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @Operation(summary = "Delete role by id", tags = "roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted role"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRoleById(id);
    }
}
