package com.urbieta.repository

import com.urbieta.domains.SecRole
import com.urbieta.domains.SecUser
import com.urbieta.domains.SecUserSecRole
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface SecUserSecRoleRepository extends JpaRepository<SecUserSecRole, Long>{
	
	List<SecUserSecRole> findByIdSecUser(SecUser secUser);
	List<SecUserSecRole> findByIdSecRole(SecRole secRole);
	SecUserSecRole findOneByIdSecUserAndIdSecRole(SecUser secUser, SecRole secRole);
	Long deleteByIdSecUserAndIdSecRole(SecUser secUser, SecRole secRole);
}
