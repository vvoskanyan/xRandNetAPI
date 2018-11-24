package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Role;
import com.ysu.xrandnet.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}