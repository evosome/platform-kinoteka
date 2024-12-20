package org.example.services;

import org.example.modules.Role;
import org.example.repositories.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Page<Role> getAllRoles(int page, int size) {
        return roleRepository.findAll(PageRequest.of(page, size));
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRoleById(long id) {
        roleRepository.deleteById(id);
    }

    public Role getRoleById(long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
    }
}