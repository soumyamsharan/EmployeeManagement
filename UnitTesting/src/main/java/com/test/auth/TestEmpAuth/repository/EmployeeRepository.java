package com.test.auth.TestEmpAuth.repository;

import com.test.auth.TestEmpAuth.model.TestEmployee;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CassandraRepository<TestEmployee, Long> {
}
