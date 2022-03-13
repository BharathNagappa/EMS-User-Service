package com.ems.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ems.entity.UserRoles;

public interface IRolesServiceRepository extends CrudRepository<UserRoles, Long>{

	Optional<UserRoles> findByRoleName(String roleName);

}
