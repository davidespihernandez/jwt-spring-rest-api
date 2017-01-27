package com.urbieta.repository

import com.urbieta.domains.OtherProduct
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface OtherProductRepository extends JpaRepository<OtherProduct, Long>{
	OtherProduct findOneById(Long id);
	OtherProduct findOneByName(String name);
}
