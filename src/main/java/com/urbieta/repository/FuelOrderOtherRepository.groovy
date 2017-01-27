package com.urbieta.repository

import com.urbieta.domains.FuelOrder
import com.urbieta.domains.FuelOrderOther
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface FuelOrderOtherRepository extends JpaRepository<FuelOrderOther, Long>{
	FuelOrderOther findOneById(Long id);
	List<FuelOrderOther> findByFuelOrderOrderById(FuelOrder fuelOrder);
}
