package com.ems.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ems.entity.Employee;


@Repository
public interface IEmployeeRepository extends CrudRepository<Employee, Long> {

}
