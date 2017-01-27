package com.urbieta.domains


import com.urbieta.JwtDemoApplication
import com.urbieta.repository.SecRoleRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

import javax.persistence.*

@Entity
@Table(name = "sec_role")
class SecRole implements GrantedAuthority, Authentication, Serializable{
	@Id
    @GeneratedValue
    Long id;

	@Version
	private Long version = 0L;

	@Column(name = "authority", nullable = false, unique = true)
	String authority 

	@Column(name = "description", nullable = true)
	String description

	@Column(name = "authenticated", nullable = false)
	Boolean authenticated=false

	boolean equals(other) {
		if (!(other instanceof SecRole)) {
			return false
		}
		other.authority == authority
	}
	
	//interface method
	String getAuthority(){
		return(authority)
	}

	@Override
	public String getName() {
		return authority;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return [this];
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return authority;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
	}
	
	String toString(){
		return(authority)
	}

	static SecRole get(Long id){
		SecRoleRepository secRoleRepository = JwtDemoApplication.ctx.getBean('secRoleRepository')
		secRoleRepository.findOneById(id)
	}

	SecRole save(){
		SecRoleRepository secRoleRepository = JwtDemoApplication.ctx.getBean('secRoleRepository')
		secRoleRepository.save(this)
	}

	def delete(){
		SecRoleRepository secRoleRepository = JwtDemoApplication.ctx.getBean('secRoleRepository')
		secRoleRepository.delete(this.getId())
	}

	static List<SecRole> findAll(){
		SecRoleRepository secRoleRepository = JwtDemoApplication.ctx.getBean('secRoleRepository')
		secRoleRepository.findAll()
	}

	static SecRole findByAuthority(String authority){
		SecRoleRepository secRoleRepository = JwtDemoApplication.ctx.getBean('secRoleRepository')
		secRoleRepository.findOneByAuthority(authority)
	}
}
