package com.urbieta.repository

import com.urbieta.domains.FuelProduct
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface FuelProductRepository extends JpaRepository<FuelProduct, Long>{
	FuelProduct findOneById(Long id);
	FuelProduct findOneByName(String name);
	FuelProduct findOneByCode(String code);
}
