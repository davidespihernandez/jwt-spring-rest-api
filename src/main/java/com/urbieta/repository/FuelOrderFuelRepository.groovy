package com.urbieta.repository

import com.urbieta.domains.FuelOrder
import com.urbieta.domains.FuelOrderFuel
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface FuelOrderFuelRepository extends JpaRepository<FuelOrderFuel, Long>{
	FuelOrderFuel findOneById(Long id);
	List<FuelOrderFuel> findByFuelOrderOrderById(FuelOrder fuelOrder);
}
