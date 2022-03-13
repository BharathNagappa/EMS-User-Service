package com.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.User;
import com.ems.repo.IUserRepository;

@Service
public class UserService implements IEntityService<User> {

	@Autowired
	IUserRepository repo;
	
	@Override
	public Iterable<User> getAllEntities() {
		return repo.findAll();
	}

	@Override
	public User saveEntity(User entity) {
		// TODO Auto-generated method stub
		return repo.save(entity);
	}

	public Iterable<User> getUserByName(String name) {
		return repo.findByUserId(name);
	}

}
