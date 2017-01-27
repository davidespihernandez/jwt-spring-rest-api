package com.urbieta.domains


import com.urbieta.JwtDemoApplication
import com.urbieta.repository.SecUserSecRoleRepository
import groovy.transform.EqualsAndHashCode

import javax.persistence.*

@Entity //@IdClass(SecUserSecRolePK.class)
@Table(name = "sec_user_sec_role",
    uniqueConstraints=@UniqueConstraint(columnNames=["sec_user_id", "sec_role_id"])
)
class SecUserSecRole implements Serializable{

	@EmbeddedId
	private SecUserSecRolePK id = new SecUserSecRolePK();

	public getSecUser(){ 
		id.secUser
	}
	
	public getSecRole(){ 
		id.secRole
	}
	
	public setSecUser(SecUser secUser){
		id.secUser = secUser
	}
	
	public setSecRole(SecRole secRole){
		id.secRole = secRole
	}

//	static mapping = {
//		id composite: ['secRole', 'secUser']
//		version false
//		secUser lazy: false
//		secRole lazy: false
//	}
	
	static List<SecUserSecRole> findAllBySecUser(SecUser secUser){
		SecUserSecRoleRepository secUserSecRoleRepository = JwtDemoApplication.ctx.getBean('secUserSecRoleRepository')
		return(secUserSecRoleRepository.findByIdSecUser(secUser))
	}

	SecUserSecRole save(){
		SecUserSecRoleRepository secUserSecRoleRepository = JwtDemoApplication.ctx.getBean('secUserSecRoleRepository')
		secUserSecRoleRepository.save(this)
	}
	
	def delete(){
		SecUserSecRoleRepository secUserSecRoleRepository = JwtDemoApplication.ctx.getBean('secUserSecRoleRepository')
		secUserSecRoleRepository.deleteByIdSecUserAndIdSecRole(id.secUser, id.secRole)
	}
	
	static List<SecUserSecRole> findAllBySecRole(SecRole secRole){
		SecUserSecRoleRepository secUserSecRoleRepository = JwtDemoApplication.ctx.getBean('secUserSecRoleRepository')
		return(secUserSecRoleRepository.findByIdSecRole(secRole))
	}
	
	static List<SecUserSecRole> findAll(){
		SecUserSecRoleRepository secUserSecRoleRepository = JwtDemoApplication.ctx.getBean('secUserSecRoleRepository')
		secUserSecRoleRepository.findAll()
	}
	
	static SecUserSecRole findBySecUserAndSecRole(SecUser secUser, SecRole secRole){
		SecUserSecRoleRepository secUserSecRoleRepository = JwtDemoApplication.ctx.getBean('secUserSecRoleRepository')
		secUserSecRoleRepository.findOneByIdSecUserAndIdSecRole(secUser, secRole)
	}

	static void deleteAll(List<SecUserSecRole> elements){
		elements.each{ element ->
			element.delete()
		}
	}
}

@Embeddable
@EqualsAndHashCode
public class SecUserSecRolePK implements Serializable {
	@ManyToOne
	@JoinColumn(name = "sec_user_id", referencedColumnName = "id", nullable = false)
	SecUser secUser

	@ManyToOne
	@JoinColumn(name = "sec_role_id", referencedColumnName = "id", nullable = false)
	SecRole secRole
}

