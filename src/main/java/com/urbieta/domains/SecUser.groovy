package com.urbieta.domains


import com.urbieta.JwtDemoApplication
import com.urbieta.repository.SecUserRepository
import com.urbieta.services.SecurityService
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Null

@Entity
@Table(name = "sec_user")
class SecUser implements Authentication, Serializable{
	@Id
    @GeneratedValue
    Long id;

	@Version
	private Long version = 0L;

	@Column(name = "username", nullable = false, unique=true)
	String username

	@Column(name = "`password`", nullable = false)
	String password

	@Column(name = "enabled", nullable = false)
	boolean enabled

	@Column(name = "account_expired", nullable = false)
	boolean accountExpired

	@Column(name = "account_locked", nullable = false)
	boolean accountLocked

	@Column(name = "credentials_expired", nullable = false)
	boolean credentialsExpired

	@Column(name = "password_expired", nullable = false)
	boolean passwordExpired

	@Column(name = "authenticated", nullable = false)
	Boolean authenticated=false

	@Column(name = "user_email", nullable = false)
	String userEmail

	@Column(name = "first_name", nullable = false)
	String firstName

	@Column(name = "last_name", nullable = true)
	String lastName

	@Column(name = "phone_number", nullable = true)
	String phoneNumber

	@Column(name = "last_password_reset_date")
	@Temporal(TemporalType.TIMESTAMP)
	@Null
	Date lastPasswordResetDate


	List<SecRole> getAuthorities() {
		return(SecUserSecRole.findAllBySecUser(this).collect { it.secRole } as Collection)
	}
	
	//methods from Authentication interface, needed for Acl security
	//getAuthorities already existed
	@Override
	public String getName() {
		return username;
	}

	@Override
	public Object getCredentials() {
		return password;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return username;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
	}

	static SecUser get(Long id){
		SecUserRepository secUserRepository = JwtDemoApplication.ctx.getBean('secUserRepository')
		secUserRepository.findOneById(id)
	}
	
	SecUser save(){
		SecUserRepository secUserRepository = JwtDemoApplication.ctx.getBean('secUserRepository')
		secUserRepository.save(this)
	}
	
	def delete(){
		SecUserRepository secUserRepository = JwtDemoApplication.ctx.getBean('secUserRepository')
		secUserRepository.delete(this.getId())
	}

	static List<SecUser> findAll(){
		SecUserRepository secUserRepository = JwtDemoApplication.ctx.getBean('secUserRepository')
		Sort userSort = new Sort(Sort.Direction.ASC, "username");
		secUserRepository.findAll(userSort);
	}
	
	static SecUser findByUsername(String username){
		SecUserRepository secUserRepository = JwtDemoApplication.ctx.getBean('secUserRepository')
		secUserRepository.findOneByUsername(username)
	}

	String getFullName(){
		String fName = firstName
		if(lastName != null){
			fName = fName + " " + lastName
		}
		return(fName)
	}

	String getRolesString() {
		return(SecUserSecRole.findAllBySecUser(this).findAll{ it.secRole.authority!=SecurityService.ROLE_ACCESS }.collect { it.secRole.description }.join(', '))
	}

}
