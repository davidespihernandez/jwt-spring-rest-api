package com.urbieta.repository

import com.urbieta.domains.FuelOrder
import com.urbieta.domains.FuelOrderHistory
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface FuelOrderHistoryRepository extends JpaRepository<FuelOrderHistory, Long>{
	FuelOrderHistory findOneById(Long id);
	List<FuelOrderHistory> findByFuelOrderOrderById(FuelOrder fuelOrder);
}
