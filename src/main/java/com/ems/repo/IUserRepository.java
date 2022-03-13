package com.ems.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ems.entity.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
	
	Iterable<User> findByUserId(String name);


}
