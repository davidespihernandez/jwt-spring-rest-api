package com.urbieta.repository

import com.urbieta.domains.FuelOrderStatus
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface FuelOrderStatusRepository extends JpaRepository<FuelOrderStatus, Long>{
	FuelOrderStatus findOneById(Long id);
	FuelOrderStatus findOneByName(String name);
}
