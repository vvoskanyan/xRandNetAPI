package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByRole(String role);
}