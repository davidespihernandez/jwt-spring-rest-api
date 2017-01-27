package com.urbieta.repository

import com.urbieta.domains.SecRole
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface SecRoleRepository extends JpaRepository<SecRole, Long>{
	SecRole findOneById(Long id);
	SecRole findOneByAuthority(String authority);
}
