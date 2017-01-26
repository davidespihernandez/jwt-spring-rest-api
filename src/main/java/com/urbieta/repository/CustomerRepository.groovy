package com.urbieta.repository

import com.urbieta.domains.*
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface CustomerRepository extends JpaRepository<Customer, Long>{
	com.urbieta.domains.Customer findOneById(Long id);
	com.urbieta.domains.Customer findOneByName(String name);
	@Query("select c from Customer c where c.name like %?1% or c.contactName like %?1% or c.city like %?1% ORDER BY c.name ASC")
	List<com.urbieta.domains.Customer> filterCustomers(String filter);
}
