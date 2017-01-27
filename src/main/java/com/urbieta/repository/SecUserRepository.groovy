package com.urbieta.repository

import com.urbieta.domains.SecUser
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface SecUserRepository extends JpaRepository<SecUser, Long>{
	SecUser findOneById(Long id);
	SecUser findOneByUsername(String username);
}
